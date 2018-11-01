package susankyatech.com.consultancymanagement.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import susankyatech.com.consultancymanagement.Model.Login;

public interface EnquiryAPI {
    @FormUrlEncoded
    @POST("students/detail")
    Call<Login> saveDetails(@Field("qualification") String qualification,
                            @Field("interested_country") String interested_country,
                            @Field("interested_course") String interested_course,
                            @Field("summary") String summary,
                            @Field("student_id") int student_id,
                            @Field("completed_year") int completed_year);

    @FormUrlEncoded
    @POST("students/detail")
    Call<Login> saveDetailsNew(@Field("qualification") String qualification,
                            @Field("interested_country") String interested_country,
                            @Field("interested_course") String interested_course,
                            @Field("summary") String summary,
                            @Field("student_id") int student_id,
                            @Field("completed_year") int completed_year,
                               @Field("test_attended") String tests);
}
