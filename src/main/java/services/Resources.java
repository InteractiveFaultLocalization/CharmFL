package services;

import java.util.ResourceBundle;

public final class Resources {

    private Resources() {
    }

    public static String get(String bundle, String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle);
        return resourceBundle.getString(key);
    }

}
