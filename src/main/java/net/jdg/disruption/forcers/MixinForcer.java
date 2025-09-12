package net.jdg.disruption.forcers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.jdg.disruption.Disruption;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MixinForcer {

    /**
     * Anytime we make a mix-in we must add it to the check in the main mod class {@link Disruption}
     */

    public static void checkMixinList(String resourcePath, List<String> expectedMixins) {
        try {
            InputStream is = MixinForcer.class.getClassLoader().getResourceAsStream(resourcePath);
            if (is == null) {
                throw new RuntimeException("Could not find mixins.json at: " + resourcePath);
            }

            String jsonText = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonObject root = JsonParser.parseString(jsonText).getAsJsonObject();

            if (!root.has("mixins")) {
                throw new RuntimeException("'mixins' array missing from mixins.json");
            }

            JsonArray mixinArray = root.getAsJsonArray("mixins");
            mixinArray.addAll(root.getAsJsonArray("client"));

            for (String expected : expectedMixins) {
                boolean found = false;
                for (int i = 0; i < mixinArray.size(); i++) {
                    if (mixinArray.get(i).getAsString().equals(expected)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new RuntimeException("Brother how do you think I fought TBS LOL. Go home, we are two steps ahead at all times <o>");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to check mixin list: " + e.getMessage(), e);
        }
    }
}