package acme.critical.module.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventWorldRender;
import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.combat.Killaura;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.visual.esp.EntMatrixCollector;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.FriendsUtils;
import acme.critical.utils.Render3DUtils;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

public class ESP extends Mod {
    private ArrayList<Entity> ents = new ArrayList<>();

    private ModeSetting mode =
        new ModeSetting("Mode", "Box", "Box", "2D", "Glow", "Walls");
    private BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    private BooleanSetting self = new BooleanSetting("Self", false);
    public BooleanSetting players = new BooleanSetting("Players", true);
    public BooleanSetting offensive = new BooleanSetting("Offensive", true);
    public BooleanSetting passive = new BooleanSetting("Passive", false);

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
        MatrixStack matrices = event.getMatrices();

        matrices.push();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Vector2i region = Render3DUtils.getCameraRegion();
        Render3DUtils.applyRegionOffset(matrices, region);

        for (Entity ent : ents) {
            if (!shouldDraw(ent))
                continue;
            Render3DUtils.setGlColor(getColor(ent) & 0xa0ffffff);
            matrices.push();
            matrices.translate(
                ent.getX() - region.x, ent.getY(), ent.getZ() - region.y
            );
            Render3DUtils.boxAABB(
                matrices, ent.getBoundingBox().offset(ent.getPos().negate())
            );
            matrices.pop();
        }

        matrices.pop();

        Render3DUtils.setGlColor(0xffffffff);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public void onRender2D(DrawContext context, float tickDelta) {
        if (mode.getMode() != "2D")
            return;

        DrawContext context2 = new DrawContext(
            MinecraftClient.getInstance(), context.getVertexConsumers()
        );

        for (Map.Entry<Entity, MatrixStack> entry :
             EntMatrixCollector.list.entrySet()) {
            Entity ent = entry.getKey();
            context2.matrices = entry.getValue();

            context2.fill(0, 0, 100, 100, getColor(ent) & 0xa0ffffff);
        }
    }

    public String getMode() { return mode.getMode(); }

    public int getColor(Entity entity) {
        if (!(entity instanceof PlayerEntity))
            return ColorUtils.GetEntColor(entity);
        if (FriendsUtils.isFriend(entity))
            return ColorUtils.friendColor;
        if (rainbow.isEnabled())
            return ColorUtils.Rainbow();
        return new Color(255, 255, 255).getRGB();
    }

    private boolean shouldDraw(Entity entity) {
        Killaura ka = ModMan.INSTANCE.getMod(Killaura.class);
        if (!self.isEnabled() &&
            entity.equals(MinecraftClient.getInstance().player))
            return false;
        if (players.isEnabled() && entity.isPlayer())
            return true;
        if (passive.isEnabled() &&
            ka.getEntityType(entity.getType()) == "Passive")
            return true;
        if (offensive.isEnabled() &&
            ka.getEntityType(entity.getType()) == "Offensive")
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
