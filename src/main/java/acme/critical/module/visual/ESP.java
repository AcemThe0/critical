package acme.critical.module.visual;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.ModeSetting;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.FriendsUtils;
import acme.critical.utils.MiscUtils;
import acme.critical.utils.Render3DUtils;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

public class ESP extends Mod {
    private List<Entity> ents = new ArrayList<>();
    
    private ModeSetting mode = new ModeSetting("Mode", "Box", "Box", "Walls");
    private BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    private BooleanSetting self = new BooleanSetting("Self", false);
    private BooleanSetting players = new BooleanSetting("Players", true);
    private BooleanSetting offensive = new BooleanSetting("Offensive", false);
    private BooleanSetting passive = new BooleanSetting("Passive", false);

    public ESP() {
        super("ESP", "Extrasensory perception!", Category.VISUAL);
        addSettings(
            mode, rainbow, self, players, offensive, passive,
            new KeybindSetting("Key", 0)
        );
    }

    @Override
    public void onTick() {
        ents.clear();
        MinecraftClient.getInstance().world.getEntities().forEach(ents::add);
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
        if (mode.getMode() != "Box")
            return;
        var matrices = event.getMatrices();
        float tickDelta = event.getDelta();

        matrices.push();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Vector2i region = Render3DUtils.getCameraRegion();
        Render3DUtils.applyRegionOffset(matrices, region);

        matrices.translate(-region.x, 0, -region.y);
        
        for (var ent : ents) {
            if (!shouldDraw(ent))
                continue;
            matrices.push();
            matrices.translate(
                MathHelper.lerp(tickDelta, ent.prevX, ent.getX()),
                MathHelper.lerp(tickDelta, ent.prevY, ent.getY()),
                MathHelper.lerp(tickDelta, ent.prevZ, ent.getZ())
            );

            Render3DUtils.setGlColor(getColor(ent) & 0xa0ffffff);
            Render3DUtils.boxAABB(
                matrices, ent.getBoundingBox().offset(ent.getPos().negate())
            );
            Render3DUtils.setGlColor(getColor(ent));
            Render3DUtils.boxAABBOutline(
                matrices, ent.getBoundingBox().offset(ent.getPos().negate())
            );
            matrices.pop();
        }

        Render3DUtils.setGlColor(0xffffffff);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        matrices.pop();
    }

    public int getColor(Entity entity) {
        if (!entity.isPlayer())
            return ColorUtils.GetEntColor(entity);
        if (FriendsUtils.isFriend(entity))
            return ColorUtils.friendColor;
        if (rainbow.isEnabled())
            return ColorUtils.Rainbow();
        return 0xffffffff;
    }

    private boolean shouldDraw(Entity ent) {
        if (!self.isEnabled() &&
            ent.equals(MinecraftClient.getInstance().player))
            return false;
        if (players.isEnabled() && ent.isPlayer())
            return true;
        if (offensive.isEnabled() && MiscUtils.isEntHostile(ent))
            return true;
        else if (passive.isEnabled())
            return true;
        return false;
    }

    public boolean walls_shouldRenderEntity(Entity entity) {
        if (!isEnabled() || mode.getMode() != "Walls")
            return false;
        if (entity == null)
            return false;
        return true;
    }
}
