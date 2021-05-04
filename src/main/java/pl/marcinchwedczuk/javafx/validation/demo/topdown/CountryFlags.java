package pl.marcinchwedczuk.javafx.validation.demo.topdown;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CountryFlags {
    private CountryFlags() {
    }

    private static final Map<String, Image> flagImages = new HashMap<>();

    public static Image loadFlag(Country country) {
        String countryCode = country.countryCode();

        if (flagImages.containsKey(countryCode)) {
            return flagImages.get(countryCode);
        } else {
            return loadFlagFromResources(countryCode);
        }
    }

    private static Image loadFlagFromResources(String countryCode) {
        try (InputStream tmp = CountryFlags.class.getResourceAsStream("/pl/marcinchwedczuk/javafx/validation/flags/" + countryCode + ".png")) {
            Image img = new Image(tmp);
            flagImages.put(countryCode, img);
            return img;
        } catch (Exception e) {
            // Prevent further loading
            flagImages.put(countryCode, null);
            return null;
        }
    }
}
