package net.superscary.toasts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ToastLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToastLoader.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static List<CustomToast> loadToasts(Path configDir) {
        List<CustomToast> loadedToasts = new ArrayList<>();
        Path toastsDir = configDir.resolve("toasts").resolve("toasts");

        try {
            if (!Files.exists(toastsDir)) {
                Files.createDirectories(toastsDir);
                return loadedToasts;
            }

            Files.walk(toastsDir)
                .filter(path -> path.toString().endsWith(".json"))
                .forEach(path -> {
                    try {
                        CustomToast toast = loadToast(path);
                        if (toast != null) {
                            loadedToasts.add(toast);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Failed to load toast from {}", path, e);
                    }
                });
        } catch (IOException e) {
            LOGGER.error("Failed to load toasts from {}", toastsDir, e);
        }

        return loadedToasts;
    }

    private static CustomToast loadToast(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path);
             InputStreamReader reader = new InputStreamReader(is)) {
            
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            
            // Validate required fields
            if (!json.has("id") || !json.has("title")) {
                LOGGER.error("Toast in {} is missing required fields (id and title)", path);
                return null;
            }

            CustomToast.Builder builder = new CustomToast.Builder()
                .id(json.get("id").getAsString())
                .title(json.get("title").getAsString());

            // Optional fields with defaults
            if (json.has("subtitle")) {
                builder.subtitle(json.get("subtitle").getAsString());
            }
            if (json.has("icon")) {
                builder.icon(json.get("icon").getAsString());
            }
            if (json.has("background")) {
                builder.background(parseColor(json.get("background").getAsString()));
            }
            if (json.has("text_color")) {
                builder.textColor(parseColor(json.get("text_color").getAsString()));
            }
            if (json.has("subtitle_color")) {
                builder.subtitleColor(parseColor(json.get("subtitle_color").getAsString()));
            }
            if (json.has("duration")) {
                builder.duration(json.get("duration").getAsInt());
            }

            return builder.build();
        } catch (JsonParseException e) {
            LOGGER.error("Invalid JSON in toast file: {}", path, e);
            return null;
        }
    }

    private static Color parseColor(String colorString) {
        try {
            if (colorString.startsWith("#")) {
                colorString = colorString.substring(1);
            }
            return new Color(Integer.parseInt(colorString, 16));
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid color format: {}", colorString);
            return Color.WHITE; // Default to white on error
        }
    }
} 