package acme.critical.utils;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;

public class Render3DUtils {
    public static ObjParser banana = null;

    public static BufferBuilder texbb = new BufferBuilder(512);
    public static VertexConsumerProvider.Immediate texvi =
        VertexConsumerProvider.immediate(texbb);

    public static void
    applyRegionOffset(MatrixStack matrices, Vector2i region) {
        Vec3d camPos = getCameraPos();
        matrices.translate(region.x - camPos.x, -camPos.y, region.y - camPos.z);
    }

    public static void applyRegionOffset(MatrixStack matrices) {
        applyRegionOffset(matrices, getCameraRegion());
    }

    public static Camera getCamera() {
        return MinecraftClient.getInstance()
            .getBlockEntityRenderDispatcher()
            .camera;
    }

    public static BlockPos getCameraBlockPos() {
        Camera camera = getCamera();
        if (camera == null)
            return BlockPos.ORIGIN;
        return camera.getBlockPos();
    }

    public static Vec3d getCameraPos() {
        Camera camera = getCamera();
        if (camera == null)
            return Vec3d.ZERO;
        return camera.getPos();
    }

    public static Vec3d getCameraDirVec() {
        Camera cam = getCamera();
        double DR = Math.PI / 180.0;
        double PI = Math.PI;
        double pcos = -Math.cos(-cam.getPitch() * DR);
        return new Vec3d(
            Math.sin(-cam.getYaw() * DR - PI) * pcos,
            Math.sin(-cam.getPitch() * DR),
            Math.cos(-cam.getYaw() * DR - PI) * pcos
        );
    }

    public static Vector2i getCameraRegion() {
        BlockPos blockPos = getCameraBlockPos();
        return new Vector2i(
            (blockPos.getX() >> 9) * 512, (blockPos.getZ() >> 9) * 512
        );
    }

    public static void setGlColor(Color color) {
        RenderSystem.setShaderColor(
            color.getRed() / 255.0f, color.getGreen() / 255.0f,
            color.getBlue() / 255.0f, color.getAlpha() / 255.0f
        );
    }

    public static void setGlColor(int color) {
        setGlColor(new Color(color, true));
    }

    public static void spin(MatrixStack matrices, boolean temple, int speed) {
        float rot = ((System.currentTimeMillis() % (speed * 1000)) /
                     (speed * 1000.0f)) *
                    (float)Math.PI * 2;
        var quat = new Quaternionf();
        quat = temple ? quat.rotateXYZ(rot, rot, rot) : quat.rotateY(rot);
        matrices.multiply(quat);
    }

    public static void banana(MatrixStack matrices) {
        if (banana == null) {
            banana = new ObjParser(
                "banana.obj", VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION_COLOR
            );
        }

        matrices.push();

        matrices.translate(95.0f, 6.0f, 0.0f);
        spin(matrices, false, 4);
        matrices.scale(1.5f, -1.5f, 1.5f);

        banana.draw(matrices);

        matrices.pop();
    }

    public static void boxAABB(MatrixStack matrices, Box box) {
        Tessellator tes = RenderSystem.renderThreadTesselator();
        BufferBuilder bb = tes.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        bb.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        // front
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.minZ)
            .next();
        // back
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ)
            .next();
        // right
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.maxZ)
            .next();
        // left
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ)
            .next();
        // top
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.minZ)
            .next();
        // bottom
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ)
            .next();

        tes.draw();
    }

    public static void boxAABBOutline(MatrixStack matrices, Box box) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        Tessellator tes = RenderSystem.renderThreadTesselator();
        BufferBuilder bb = tes.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        bb.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION
        );

        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.maxY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.maxZ)
            .next();
        bb.vertex(matrix, (float)box.minX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.minZ)
            .next();
        bb.vertex(matrix, (float)box.maxX, (float)box.minY, (float)box.maxZ)
            .next();

        tes.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void simpleLine(MatrixStack matrices, Vec3d p1, Vec3d p2) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        Tessellator tes = RenderSystem.renderThreadTesselator();
        BufferBuilder bb = tes.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        bb.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

        bb.vertex(matrix, (float)p1.getX(), (float)p1.getY(), (float)p1.getZ())
            .next();
        bb.vertex(matrix, (float)p2.getX(), (float)p2.getY(), (float)p2.getZ())
            .next();

        tes.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void simpleText(
        MatrixStack matrices, String text, float x, float y, int color,
        boolean shadow
    ) {
        var mat = matrices.peek().getPositionMatrix();

        MinecraftClient.getInstance().textRenderer.draw(
            text, x, y, color, shadow, mat, texvi, TextLayerType.NORMAL, 0,
            LightmapTextureManager.MAX_LIGHT_COORDINATE
        );

        // HAHHAHAHAAHA SUCK MY BALLS MOJANG YOU WILL NEVER TAKE ME ALIVE
        GL11.glClear(GL11C.GL_DEPTH_BUFFER_BIT);

        texvi.draw();
    }

    public static void simpleTextCentered(
        MatrixStack matrices, String text, float x, float y, int color,
        boolean shadow
    ) {
        var tr = MinecraftClient.getInstance().textRenderer;
        simpleText(matrices, text, x - tr.getWidth(text) / 2, y, color, shadow);
    }
}
