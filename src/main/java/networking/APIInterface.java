package networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("otplog")
    Call<ResponseBody> postOtp(
            @Field("otpupdate") String otp,
            @Field("token") String token
    );

}
