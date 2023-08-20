package acme.critical.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

import acme.critical.utils.FileUtils;

import com.mojang.blaze3d.systems.RenderSystem;

public class ObjParser {
    private float[][] vertices = null;

    private VertexFormat.DrawMode drawmode;
    private VertexFormat vertexformat;

    public ObjParser(
        String filename, VertexFormat.DrawMode dm, VertexFormat vf
    ) {
        // TODO: add support for more formats & more le heckin safety
        if (dm != VertexFormat.DrawMode.QUADS)
            throw new RuntimeException("Unknown draw mode");
        if (vf != VertexFormats.POSITION_COLOR)
            throw new RuntimeException("Unknown vertex format");
        drawmode = dm;
        vertexformat = vf;

        var iverts = new ArrayList<double[]>();
        var faces = new ArrayList<int[]>();

        // parse commands
        String[] lines = FileUtils.loadAsset("banana.obj").split("\n");
        for (String line : lines) {
            char command = line.charAt(0);
            // comment
            if (command == '#')
                continue;
            // remove command before splitting
            String[] args = line.substring(2).split(" ");
            switch (command) {
            // funny
            case 'v':
                double[] darr = Arrays.stream(args)
                                    .mapToDouble(Double::parseDouble)
                                    .toArray();
                iverts.add(darr);
                break;
            case 'f':
                int[] iarr =
                    Arrays.stream(args).mapToInt(Integer::parseInt).toArray();
                faces.add(iarr);
                break;
            }
        }
        // split indices
        var vlist = new ArrayList<float[]>();
        for (int[] face : faces) {
            for (int vertid : face) {
                var ivert = iverts.get(vertid - 1);
                var vert = new float[ivert.length];
                for (int i = 0; i < ivert.length; i++)
                    vert[i] = (float)ivert[i];
                vlist.add(vert);
            }
        }
        vertices = new float[vlist.size()][];
        vertices = vlist.toArray(vertices);
    }

    public void draw(Matrix4f matrix) {
        Tessellator tes = RenderSystem.renderThreadTesselator();
        BufferBuilder bb = tes.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bb.begin(drawmode, vertexformat);

        for (float[] vert : vertices) {
            bb.vertex(matrix, vert[0], vert[1], vert[2])
                .color(vert[3], vert[4], vert[5], 1.0f)
                .next();
        }

        tes.draw();
    }

    public void draw(MatrixStack matrices) {
        draw(matrices.peek().getPositionMatrix());
    }
}
