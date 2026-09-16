package com.jerotes.jerotesvillage.util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ViewerNameManager implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ViewerNameManager INSTANCE = new ViewerNameManager();
	private static final Gson GSON = new Gson();

	private List<String> nameList = new ArrayList<>();
	private final Random random = new Random();

	public static ViewerNameManager getInstance() {
		return INSTANCE;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		nameList.clear();
		try {
			for (String namespace : resourceManager.getNamespaces()) {
				ResourceLocation location = new ResourceLocation(namespace, "viewer_name/viewer_name.json");

				resourceManager.getResource(location).ifPresent(resource -> {
					try (InputStream is = resource.open();
						 InputStreamReader reader = new InputStreamReader(is)) {
						JsonObject json = GSON.fromJson(reader, JsonObject.class);
						JsonArray values = json.getAsJsonArray("values");
						for (var element : values) {
							String name = element.getAsString();
							if (!nameList.contains(name)) {
								nameList.add(name);
							}
						}
						LOGGER.debug("Loaded {} names from namespace: {}", values.size(), namespace);
					} catch (Exception e) {
						LOGGER.error("Failed to load viewer names from {}", location, e);
					}
				});
			}
			if (nameList.isEmpty()) {
				nameList.add("Jerotes_");
				LOGGER.warn("No viewer names loaded, using default");
			} else {
				LOGGER.info("Loaded {} viewer names", nameList.size());
			}

		} catch (Exception e) {
			LOGGER.error("Failed to load viewer names", e);
			nameList.add("Jerotes_");
		}
	}
	public String getRandomName() {
		if (nameList.isEmpty()) {
			return "Jerotes_";
		}
		return nameList.get(random.nextInt(nameList.size()));
	}
	public List<String> getAllNames() {
		return new ArrayList<>(nameList);
	}
	public boolean containsName(String name) {
		return nameList.contains(name);
	}

}