package networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitClient {

    public static Retrofit retrofit;

    public static synchronized Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://mininetworkisp.xyz/")
                    .client(new OkHttpClient())
                    .build();
            }

        return retrofit;
    }

}
