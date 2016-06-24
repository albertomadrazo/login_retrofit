package mx.fesa.android.login;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface Interface {

    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/api.php")
    void postData(@Field("method") String method,
                  @Field("username") String username,
                  @Field("password") String password,
                  Callback<ServerResponse> serverResponseCallback);

    //This method is used for "GET"
    @GET("/api.php")
    void getData(@Query("method") String method,
                 @Query("username") String username,
                 @Query("password") String password,
                 Callback<ServerResponse> serverResponseCallback);
}