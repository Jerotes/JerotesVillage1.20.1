package com.jerotes.jerotesvillage.client;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class WonderlinPaintingTextureManager extends PaintingTextureManager {
    private static final ResourceLocation BACK_SPRITE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "back");
    public WonderlinPaintingTextureManager(TextureManager textureManager) {
        super(textureManager);
    }
    public TextureAtlasSprite get(PaintingVariant paintingVariant) {
        return this.getSprite(BuiltInRegistries.PAINTING_VARIANT.getKey(paintingVariant));
    }

    public TextureAtlasSprite getBackSprite() {
        return this.getSprite(BACK_SPRITE_LOCATION);
    }
}

