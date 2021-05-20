package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CountryFlags {
    private CountryFlags() {
    }

    // Poor man's cache...
    private static final Map<String, Image> flagImagesCache = new HashMap<>();

    public static Image loadFlag(Country country) {
        String countryCode = country.countryCode();

        if (flagImagesCache.containsKey(countryCode)) {
            return flagImagesCache.get(countryCode);
        } else {
            return loadFlagFromResources(countryCode);
        }
    }

    private static Image loadFlagFromResources(String countryCode) {
        try (InputStream tmp = CountryFlags.class.getResourceAsStream(
                "/flags/" + countryCode + ".png")) {
            Image img = new Image(tmp);
            flagImagesCache.put(countryCode, img);
            return img;
        } catch (Exception e) {
            // Do not retry...
            flagImagesCache.put(countryCode, null);
            return null;
        }
    }
}
