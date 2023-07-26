package acme.critical.utils;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

public class Render3DUtils {
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

        bb.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION
        );

        bb.vertex(matrix, (float)p1.getX(), (float)p1.getY(), (float)p1.getZ())
            .next();
        bb.vertex(matrix, (float)p2.getX(), (float)p2.getY(), (float)p2.getZ())
            .next();

        tes.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
}
