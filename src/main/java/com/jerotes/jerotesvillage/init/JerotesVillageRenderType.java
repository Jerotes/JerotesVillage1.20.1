package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JerotesVillageRenderType extends JerotesRenderType {
	public JerotesVillageRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}

	public static RenderType glow_translucent(ResourceLocation resourceLocation) {
		return create("glow_translucent",
				DefaultVertexFormat.POSITION_COLOR_TEX,
				VertexFormat.Mode.QUADS,
				1536,
				false,
				false,
				CompositeState.builder().setShaderState(POSITION_COLOR_TEX_SHADER)
						.setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
						.setWriteMaskState(COLOR_WRITE)
						.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
						.setLightmapState(LIGHTMAP)
						.createCompositeState(false));
	}

	public static RenderType meror_rete() {
		return create("meror_rete",
				DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,
				VertexFormat.Mode.TRIANGLE_STRIP,
				1536,
				false,
				false,
				RenderType.CompositeState.builder()
						.setShaderState(RENDERTYPE_GLINT_SHADER)
						.setTextureState(new TextureStateShard(new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_rete_add.png"), true, false))
						.setCullState(NO_CULL)
						.setLightmapState(LIGHTMAP)
						.createCompositeState(false));

	}

	private static final RenderType BEAST_TOUGH_GLINT_CORROSIVER = create("tough_glint_corrosiver",
			DefaultVertexFormat.POSITION_TEX,
			VertexFormat.Mode.QUADS,
			1536,
			false,
			false,
			CompositeState.builder()
					.setShaderState(RENDERTYPE_GLINT_SHADER)
					.setTextureState(new TextureStateShard(new ResourceLocation(JerotesVillage.MODID, "textures/entity/tough_glint_corrosiver.png"), true, false))
					.setWriteMaskState(COLOR_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(EQUAL_DEPTH_TEST)
					.setTransparencyState(GLINT_TRANSPARENCY)
					.setTexturingState(GLINT_TEXTURING)
					.createCompositeState(false));

	public static RenderType tough_glint_corrosiver() {
		return BEAST_TOUGH_GLINT_CORROSIVER;
	}

	private static final RenderType BEAST_TOUGH_GLINT_GEMSTONE_MALIGNASAUR = create("tough_glint_gemstone_malignasaur",
			DefaultVertexFormat.POSITION_TEX,
			VertexFormat.Mode.QUADS,
			1536,
			false,
			false,
			CompositeState.builder()
					.setShaderState(RENDERTYPE_GLINT_SHADER)
					.setTextureState(new TextureStateShard(new ResourceLocation(JerotesVillage.MODID, "textures/entity/tough_glint_gemstone_malignasaur.png"), true, false))
					.setWriteMaskState(COLOR_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(EQUAL_DEPTH_TEST)
					.setTransparencyState(GLINT_TRANSPARENCY)
					.setTexturingState(GLINT_TEXTURING)
					.createCompositeState(false));

	public static RenderType tough_glint_gemstone_malignasaur() {
		return BEAST_TOUGH_GLINT_GEMSTONE_MALIGNASAUR;
	}

	private static final RenderType BEAST_TOUGH_GLINT_BRIGHT_LAND_BEAST = create("tough_glint_bright_land_beast",
			DefaultVertexFormat.POSITION_TEX,
			VertexFormat.Mode.QUADS,
			1536,
			false,
			false,
			CompositeState.builder()
					.setShaderState(RENDERTYPE_GLINT_SHADER)
					.setTextureState(new TextureStateShard(new ResourceLocation(JerotesVillage.MODID, "textures/entity/tough_glint_bright_land_beast.png"), true, false))
					.setWriteMaskState(COLOR_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(EQUAL_DEPTH_TEST)
					.setTransparencyState(GLINT_TRANSPARENCY)
					.setTexturingState(GLINT_TEXTURING)
					.createCompositeState(false));

	public static RenderType tough_glint_bright_land_beast() {
		return BEAST_TOUGH_GLINT_BRIGHT_LAND_BEAST;
	}

	private static final RenderType BEAST_TOUGH_GLINT_PROSPEROUS_ENVOY = create("tough_glint_prosperous_envoy",
			DefaultVertexFormat.POSITION_TEX,
			VertexFormat.Mode.QUADS,
			1536,
			false,
			false,
			CompositeState.builder()
					.setShaderState(RENDERTYPE_GLINT_SHADER)
					.setTextureState(new TextureStateShard(new ResourceLocation(JerotesVillage.MODID, "textures/entity/tough_glint_prosperous_envoy.png"), true, false))
					.setWriteMaskState(COLOR_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(EQUAL_DEPTH_TEST)
					.setTransparencyState(GLINT_TRANSPARENCY)
					.setTexturingState(GLINT_TEXTURING)
					.createCompositeState(false));

	public static RenderType tough_glint_prosperous_envoy() {
		return BEAST_TOUGH_GLINT_PROSPEROUS_ENVOY;
	}
}

