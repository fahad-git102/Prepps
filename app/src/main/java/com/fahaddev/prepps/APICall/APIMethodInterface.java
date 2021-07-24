package com.fahaddev.prepps.APICall;

import com.fahaddev.prepps.APICall.APIModels.CollegeSignUpInformation;
import com.fahaddev.prepps.APICall.APIModels.LoginInformation;
import com.fahaddev.prepps.APICall.APIModels.ResponseAllUsers;
import com.fahaddev.prepps.APICall.APIModels.ResponseCheckSuccess;
import com.fahaddev.prepps.APICall.APIModels.ResponseCollegeSignUpInfo;
import com.fahaddev.prepps.APICall.APIModels.ResponsePasswordLink;
import com.fahaddev.prepps.APICall.APIModels.ResponseStudentSearch;
import com.fahaddev.prepps.APICall.APIModels.SendMessageInformation;
import com.fahaddev.prepps.APICall.APIModels.SignUpInformation;
import com.fahaddev.prepps.models.ApiModels.SearchCollegeInfo;
import com.fahaddev.prepps.models.StudentFilterModel;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIMethodInterface {
    @POST("login")
    Call<ResponseLoginFile> login(@Body LoginInformation loginInformation);

    @POST("register")
    Call<ResponseSignUpFile> signUp(@Body SignUpInformation signUpInformation);

    @POST("register")
    Call<ResponseCollegeSignUpInfo> signUpCollege(@Body CollegeSignUpInformation signUpInformation);

    @POST("forgot/password")
    Call<ResponsePasswordLink> resetPassword(@Body String email);

    @GET("article/list")
    Call<ResponseArticlesCall> getArtices();

    @GET("timeline/get/list")
    Call<ResponseTimelineFile> getTimeline(@Header("Authorization") String token);

    @GET("profile/{id}")
    Call<ResponseUserDetail> getUserDetail(@Header("Authorization") String token, @Path("id") float id);

    @GET("article/{id}")
    Call<ResponseArticleDetail> getArticlesDetail(@Header("Authorization") String token, @Path("id") float id);

    @GET("timeline/{id}")
    Call<ResponseTimelineDetail> getTimelineDetail(@Header("Authorization") String token, @Path("id") float id);

    @GET("profile/get/list")
    Call<ResponseAllUsers> getAllUsers(@Header("Authorization") String token);

    @GET("student/search/tool")
    Call<ResponseStudentSearch> searchStudents(@Header("Authorization") String token);

//    @Headers("Accept: text/html")
    @GET("student/search/tool")
    Call<ResponseStudentSearch> filterStudents(@Header("Authorization") String token, @Query("name") String name,
                                               @Query("state") String state, @Query("career_interest") String career_interest,
                                               @Query("gpa") String gpa, @Query("zip_code") String zip_code);

    @GET("favorite/list")
    Call<ResponseFavouriteStudents> getFavouriteStudents(@Header("Authorization") String token);

    @POST("remove/favorite/{id}")
    Call<ResponseCheckSuccess> removeFavourite(@Header("Authorization") String token, @Path("id") float id);

    @POST("add/favorite/{id}")
    Call<ResponseCheckSuccess> addToFavourite(@Header("Authorization") String token, @Path("id") float id);

    @GET("college/navigator/list")
    Call<ResponseCollegeNavigator> getCollegeNavigatorsList();

    @POST("college/navigator/search")
    Call<ResponseCollegeNavigator> getFilteredCollegeList(@Body SearchCollegeInfo searchCollegeInfo);

    @GET("college/navigator/{id}")
    Call<ResponseCollegeDetail> getCollegeDetail(@Path("id") int id);

    @GET("get/inbox/list")
    Call<ResponseInboxCall> getInboxList(@Header("Authorization") String token);

    @GET("inbox/{id}")
    Call<ResponseChatCall> getChat(@Header("Authorization") String token, @Path("id") float id);

    @POST("message")
    Call<ResponseSentMessageCall> sendMessage(@Header("Authorization") String token, @Body SendMessageInformation sendMessageInformation);

    @POST("profile/update-about-me")
    Call<ResponseAboutMe> updateAboutMe(@Query("about_us") String aboutUs, @Query("token") String token);

    @POST("profile/update-profile")
    Call<ResponseUpdateProfile> updateProfile(@Query("token") String token, @Query("gpa") String gpa,
                                              @Query("hobbies") String hobbies, @Query("activities") String activities,
                                              @Query("career_interested") String career_interested);

    @Multipart
    @POST("profile/update-cover")
    Call<ResponseUploadCover> uploadCover(@Part MultipartBody.Part file, @Part MultipartBody.Part token);
}
