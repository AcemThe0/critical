package acme.critical.module.visual;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.FriendsUtils;
import acme.critical.utils.MiscUtils;
import acme.critical.utils.Render3DUtils;

import org.lwjgl.opengl.GL11;

public class Nametags extends Mod {
    private ArrayList<Entity> ents = new ArrayList<>();
    private MinecraftClient mc = MinecraftClient.getInstance();

    private NumberSetting size = new NumberSetting("Size", 0, 7, 2.4, 0.1);
    private BooleanSetting self = new BooleanSetting("Self", false);
    public BooleanSetting players = new BooleanSetting("Players", true);
    public BooleanSetting offensive = new BooleanSetting("Offensive", false);
    public BooleanSetting passive = new BooleanSetting("Passive", false);
    public BooleanSetting items = new BooleanSetting("Items", true);

    public Nametags() {
        super(
            "Nametags", "View additional info about entities.", Category.VISUAL
        );
        addSettings(
            size, self, players, offensive, passive, items,
            new KeybindSetting("Key", 0)
        );
    }

    @Override
    public void onTick() {
        ents.clear();
        mc.world.getEntities().forEach(ents::add);
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }

    @CriticalSubscribe
    public void onRenderWorld(EventWorldRender.Post event) {
        var matrices = event.getMatrices();
        float tickDelta = event.getDelta();

        matrices.push();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        var region = Render3DUtils.getCameraRegion();
        Render3DUtils.applyRegionOffset(matrices, region);

        for (var ent : ents) {
            if (!shouldDraw(ent))
                continue;
            matrices.push();
            matrices.translate(-region.x, 0, -region.y);
            var lerp = new Vec3d(
                MathHelper.lerp(tickDelta, ent.prevX, ent.getX()),
                MathHelper.lerp(tickDelta, ent.prevY, ent.getY()),
                MathHelper.lerp(tickDelta, ent.prevZ, ent.getZ())
            );
            matrices.translate(lerp.x, lerp.y + ent.getHeight() + 0.5, lerp.z);

            var dir = lerp.subtract(Render3DUtils.getCameraPos()).normalize();
            //var quat = new Quaternion();
            //quat = quat.rotateY((float)-MathHelper.atan2(dir.z, dir.x));
            //quat = quat.rotateY(MathHelper.PI / 2.0f);
            var quat = Quaternion.fromEulerXyz(
                0.0f,
                (float)-MathHelper.atan2(dir.z, dir.x) + MathHelper.PI / 2.0f,
                0.0f
            );
            matrices.multiply(quat);

            float scale = size.getValueFloat() * 0.01f;
            float scale_dist = mc.player.distanceTo(ent) / 10;
            if (scale_dist > 1.0f)
                scale *= scale_dist;
            matrices.scale(-scale, -scale, -scale);

            String text = ent.getDisplayName().getString();
            if (ent instanceof LivingEntity) {
                int hp = (int)((LivingEntity)ent).getHealth();
                text += " \u00a7a" + hp;
            }
            if (ent instanceof ItemEntity) {
                int count = ((ItemEntity)ent).getStack().getCount();
                text += " \u00a76x" + count;
            }

            Render3DUtils.simpleTextCentered(
                matrices, text, 0.0f, 0.0f,
                FriendsUtils.isFriend(ent) ? ColorUtils.friendColor
                                           : 0xffffffff,
                true
            );
            matrices.pop();
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        matrices.pop();
    }

    public boolean shouldDraw(Entity ent) {
        if (!self.isEnabled() &&
            ent.equals(MinecraftClient.getInstance().player))
            return false;
        if (items.isEnabled() && ent.getType() == EntityType.ITEM)
            return true;
        if (players.isEnabled() && ent.isPlayer())
            return true;
        if (offensive.isEnabled() && MiscUtils.isEntHostile(ent))
            return true;
        else if (passive.isEnabled() && ent.getType() != EntityType.ITEM)
            return true;
        return false;
    }
}
