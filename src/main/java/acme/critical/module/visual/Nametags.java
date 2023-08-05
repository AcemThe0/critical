package acme.critical.module.visual;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;
import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.utils.Render3DUtils;

import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

public class Nametags extends Mod {
    private ArrayList<Entity> ents = new ArrayList<>();
    private MinecraftClient mc = MinecraftClient.getInstance();

    private NumberSetting size = new NumberSetting("Size", 0, 7, 2.4, 0.1);

    public Nametags() {
        super(
            "Nametags", "View additional info about entities.", Category.VISUAL
        );
        addSettings(size, new KeybindSetting("Key", 0));
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

        Render3DUtils.setGlColor(0xffffffff);

        for (var ent : ents) {
            matrices.push();
            matrices.translate(-region.x, 0, -region.y);
            var lerp = new Vec3d(
                MathHelper.lerp(tickDelta, ent.prevX, ent.getX()),
                MathHelper.lerp(tickDelta, ent.prevY, ent.getY()),
                MathHelper.lerp(tickDelta, ent.prevZ, ent.getZ())
            );
            matrices.translate(lerp.x, lerp.y, lerp.z);
            matrices.translate(0.0, ent.getHeight() + 0.5, 0.0);

            var dir = lerp.subtract(Render3DUtils.getCameraPos()).normalize();
            var quat = new Quaternionf();
            quat = quat.rotateY((float)-MathHelper.atan2(dir.z, dir.x));
            quat = quat.rotateY(MathHelper.PI / 2.0f);

            matrices.multiply(quat);

            float scale = size.getValueFloat() * 0.01f;
            float scale_dist = mc.player.distanceTo(ent) / 10;
            if (scale_dist > 1.0f)
                scale *= scale_dist;
            matrices.scale(-scale, -scale, -scale);

            String text = "Macaque";
            if (ent instanceof LivingEntity) {
                int hp = (int)((LivingEntity)ent).getHealth();
                text = text + " \u00a7a" + hp;
            }

            Render3DUtils.simpleTextCentered(
                matrices, text, 0.0f, 0.0f, 0xffffffff, true
            );
            matrices.pop();
        }

        Render3DUtils.setGlColor(0xffffffff);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        matrices.pop();
    }

    public float getSize() { return size.getValueFloat(); }
}
