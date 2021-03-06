package susankyatech.com.consultancymanagement.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import susankyatech.com.consultancymanagement.Model.Login;

public interface ClientInterestAPI {
    @FormUrlEncoded
    @POST("students/client/interested/")
    Call<Login> interestedOnClient(@Field("client_id") int id,
                                   @Field("is_interested") int is_interested,
                                   @Field("is_enquiring") int is_enquiring,
                                   @Field("to_owner") int to_owner);


    @FormUrlEncoded
    @POST("students/client/remove-interested/")
    Call<Login> unInterestedOnClient(@Field("client_id") int id);
}
