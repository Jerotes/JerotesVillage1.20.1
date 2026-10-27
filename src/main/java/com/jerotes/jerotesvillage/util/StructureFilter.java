package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;

public class StructureFilter {
	//白名单写法：modid（该模组全部结构）、modid:path（单个结构）、#modid:tag（结构tag）
	public static boolean isAllowed(Registry<Structure> registry, Structure structure) {
		ResourceLocation structureId = registry.getKey(structure);
		if (structureId == null) {
			return true;
		}
		List<String> whitelist = OtherMainConfig.SecondRoundWorldStructureWhitelist;
		if (whitelist == null || whitelist.isEmpty()) {
			return false;
		}
		Holder<Structure> holder = null;
		ResourceKey<Structure> structureKey = registry.getResourceKey(structure).orElse(null);
		if (structureKey != null) {
			holder = registry.getHolder(structureKey).orElse(null);
		}
		for (String entry : whitelist) {
			if (entry == null) {
				continue;
			}
			String string = entry.trim();
			if (string.isEmpty()) {
				continue;
			}
			if (string.charAt(0) == '#') {
				ResourceLocation tagId = ResourceLocation.tryParse(string.substring(1));
				if (tagId != null && holder != null && holder.is(TagKey.create(Registries.STRUCTURE, tagId))) {
					return true;
				}
			} else if (string.indexOf(':') >= 0) {
				ResourceLocation exactId = ResourceLocation.tryParse(string);
				if (exactId != null && exactId.equals(structureId)) {
					return true;
				}
			} else if (string.equals(structureId.getNamespace())) {
				return true;
			}
		}
		return false;
	}
}
