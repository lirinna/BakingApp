package app.example.baking.bakingapp.utils;

import okhttp3.OkHttpClient;

/**
 * Created by Katy on 22.05.2018.
 * Source: https://github.com/chiuki/espresso-samples/tree/master/idling-resource-okhttp
 */

public abstract class OkHttpProvider {
    private static OkHttpClient instance = null;

    public static OkHttpClient getOkHttpInstance() {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        return instance;
    }
}