package app.example.baking.bakingapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Katy on 21.05.2018.
 * https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
 */

public class ApiClient {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpProvider.getOkHttpInstance())
                    .build();
        }
        return retrofit;
    }
}
