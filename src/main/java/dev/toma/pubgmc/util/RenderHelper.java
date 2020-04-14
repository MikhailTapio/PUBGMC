package dev.toma.pubgmc.util;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.toma.pubgmc.Pubgmc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderHelper {

    public static final ResourceLocation TEXTURES_X16 = Pubgmc.makeResource("textures/icons/icons_x16.png");
    public static final ResourceLocation TEXTURES_X32 = Pubgmc.makeResource("textures/icons/icons_x32.png");

    public static void color_x16Blit(int x, int y, int x2, int y2, int ax, int ay, int aw, int ah, float r, float g, float b, float a) {
        colorBlit(TEXTURES_X16, x, y, x2, y2, 16, ax, ay, aw, ah, r, g, b, a);
    }

    public static void x16Blit(int x, int y, int x2, int y2, int ax, int ay, int aw, int ah) {
        blit(TEXTURES_X16, x, y, x2, y2, 16, ax, ay, aw, ah);
    }

    public static void color_x32Blit(int x, int y, int x2, int y2, int ax, int ay, int aw, int ah, float r, float g, float b, float a) {
        colorBlit(TEXTURES_X32, x, y, x2, y2, 32, ax, ay, aw, ah, r, g, b, a);
    }

    public static void x32Blit(int x, int y, int x2, int y2, int ax, int ay, int aw, int ah) {
        blit(TEXTURES_X32, x, y, x2, y2, 32, ax, ay, aw, ah);
    }

    public static void colorBlit(ResourceLocation location, int x, int y, int x2, int y2, int size, int atlasX, int atlasY, int atlasWidth, int atlasHeight, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        double us = (atlasX * size) / 256.0D;
        double vs = (atlasY * size) / 256.0D;
        double ue = us + (atlasWidth * size) / 256.0D;
        double ve = vs + (atlasHeight * size) / 256.0D;
        BufferBuilder builder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        Minecraft.getInstance().getTextureManager().bindTexture(location);
        color_tex_shape(builder, x, y, x2, y2, us, vs, ue, ve, r, g, b, a);
        tessellator.draw();
        GlStateManager.disableBlend();
    }

    public static void blit(ResourceLocation location, int x, int y, int x2, int y2, int size, int atlasX, int atlasY, int atlasWidth, int atlasHeight) {
        Tessellator tessellator = Tessellator.getInstance();
        double us = (atlasX * size) / 256.0D;
        double vs = (atlasY * size) / 256.0D;
        double ue = us + (atlasWidth * size) / 256.0D;
        double ve = vs + (atlasHeight * size) / 256.0D;
        BufferBuilder builder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        Minecraft.getInstance().getTextureManager().bindTexture(location);
        tex_shape(builder, x, y, x2, y2, us, vs, ue, ve);
        tessellator.draw();
        GlStateManager.disableBlend();
    }

    public static void drawColoredShape(int x, int y, int x2, int y2, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        color_shape(builder, x, y, x2, y2, r, g, b, a);
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture();
    }

    public static void tex_shape(BufferBuilder builder, int x, int y, int x2, int y2, double fromU, double fromV, double toU, double toV) {
        builder.begin(7, DefaultVertexFormats.POSITION_TEX);
        builder.pos(x, y2, 0).tex(fromU, toV).endVertex();
        builder.pos(x2, y2, 0).tex(toU, toV).endVertex();
        builder.pos(x2, y, 0).tex(toU, fromV).endVertex();
        builder.pos(x, y, 0).tex(fromU, fromV).endVertex();
    }

    public static void color_shape(BufferBuilder builder, int x, int y, int x2, int y2, float r, float g, float b, float a) {
        builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        builder.pos(x, y2, 0).color(r, g, b, a).endVertex();
        builder.pos(x2, y2, 0).color(r, g, b, a).endVertex();
        builder.pos(x2, y, 0).color(r, g, b, a).endVertex();
        builder.pos(x, y, 0).color(r, g, b, a).endVertex();
    }

    public static void color_tex_shape(BufferBuilder builder, int x, int y, int x2, int y2, double fromU, double fromV, double toU, double toV, float r, float g, float b, float a) {
        builder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        builder.pos(x, y2, 0).tex(fromU, toV).color(r, g, b, a).endVertex();
        builder.pos(x2, y2, 0).tex(toU, toV).color(r, g, b, a).endVertex();
        builder.pos(x2, y, 0).tex(toU, fromV).color(r, g, b, a).endVertex();
        builder.pos(x, y, 0).tex(fromU, fromV).color(r, g, b, a).endVertex();
    }
}
