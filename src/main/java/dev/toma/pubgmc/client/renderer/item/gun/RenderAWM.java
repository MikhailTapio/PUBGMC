package dev.toma.pubgmc.client.renderer.item.gun;

import dev.toma.pubgmc.client.models.weapons.ModelAWM;
import dev.toma.pubgmc.client.models.weapons.ModelGun;
import dev.toma.pubgmc.client.renderer.item.IRenderConfig;
import dev.toma.pubgmc.init.PMCItems;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;

public class RenderAWM extends WeaponRenderer {

    final ModelAWM model = new ModelAWM();

    @Override
    public void preRender(ItemCameraTransforms.TransformType transformType) {
        GlStateManager.rotate(1.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.003, -0.031, 0.0);
    }

    @Override
    public ModelGun getWeaponModel() {
        return model;
    }

    @Override
    public void registerAttachmentRenders() {
        registerRenderConfig(PMCItems.SCOPE4X, IRenderConfig.translated(0F, 0.1F, -0.04F));
        registerRenderConfig(PMCItems.SCOPE15X, IRenderConfig.translated(0F, 0.19F, 0.04F));
        registerRenderConfig(PMCItems.RED_DOT, IRenderConfig.translated(0F, 0.1F, -0.1F));
        registerRenderConfig(PMCItems.HOLOGRAPHIC, IRenderConfig.translated(0F, 0.07F, -0.06F));
        registerRenderConfig(PMCItems.SCOPE2X, IRenderConfig.translatedScaled(0.1F, 0.24F, 0F, 0.8F, 0.8F, 0.8F));
        registerRenderConfig(PMCItems.SILENCER_SNIPER, IRenderConfig.translatedScaled(0F, 0.09F, -0.34F, 1F, 1F, 1.2F));
        registerRenderConfig(PMCItems.SCOPE8X, IRenderConfig.translated(0F, 0.17F, 0F));
    }
}
