package acme.critical.module.visual;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.FriendsUtils;
import acme.critical.utils.MiscUtils;
import acme.critical.utils.Render3DUtils;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

public class Tracers extends Mod {
    private List<Entity> ents = new ArrayList<>();
    
    private BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    private BooleanSetting self = new BooleanSetting("Self", false);
    private BooleanSetting players = new BooleanSetting("Players", true);
    private BooleanSetting offensive = new BooleanSetting("Offensive", false);
    private BooleanSetting passive = new BooleanSetting("Passive", false);
    
    public Tracers() {
        super("Tracers", "Trace entities.", Category.VISUAL);
        addSettings(
            rainbow, self, players, offensive, passive,
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
        var matrices = event.getMatrices();
        float tickDelta = event.getDelta();

        matrices.push();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Vector2i region = Render3DUtils.getCameraRegion();
        Render3DUtils.applyRegionOffset(matrices, region);

        Vec3d tracerStartPos =
            Render3DUtils.getCameraPos().add(Render3DUtils.getCameraDirVec());
        matrices.translate(-region.x, 0, -region.y);

        for (var ent : ents) {
            if (!shouldDraw(ent))
                continue;
            Render3DUtils.setGlColor(getColor(ent) & 0xa0ffffff);
            Render3DUtils.simpleLine(
                matrices, tracerStartPos, ent.getBoundingBox().getCenter()
            );
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
}
