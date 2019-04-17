package com.eng.chula.se.hygeia.api;

import com.eng.chula.se.hygeia.models.DefaultResponse;
import com.eng.chula.se.hygeia.models.LoginResponse;
import com.eng.chula.se.hygeia.models.UsersResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    //Login
    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("school") String school
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("allusers")
    Call<UsersResponse> getUsers();

    @FormUrlEncoded
    @PUT("updateuser/{id}")
    Call<LoginResponse> updateUser(
            @Path("id") int id,
            @Field("email") String email,
            @Field("name") String name,
            @Field("school") String school
    );

    @FormUrlEncoded
    @PUT("updatepassword")
    Call<DefaultResponse> updatePassword(
            @Field("currentpassword") String currentpassword,
            @Field("newpassword") String newpassword,
            @Field("email") String email
    );

    @DELETE("deleteuser/{id}")
    Call<DefaultResponse> deleteUser(@Path("id") int id);

    //User Register
    @FormUrlEncoded
    @POST("registrationform")
    Call<DefaultResponse> registrationForm(
            @Field("password") String password,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("citizenId") String citizenId,
            @Field("birthday") String birthday,
            @Field("hometown") String hometown,
            @Field("telephoneNumber") String telephoneNumber,
            @Field("email") String email,
            @Field("contact") String contact
    );

    //Drug Request
    @FormUrlEncoded
    @POST("drugrequest")
    Call<DefaultResponse> drugRequest(
            @Field("requestId") String requestId,
            @Field("topic") String topic,
            @Field("attachment") String attachment
    );

    //Pharmacy Register
    @FormUrlEncoded
    @POST("pharmacyform")
    Call<DefaultResponse> pharmacyForm(
            @Field("pharmacistName") String pharmacistName,
            @Field("pharmacistSurname") String pharmacistSurname,
            @Field("pharmacistLicenseId") String pharmacistLicenseId
    );

    //Pharmacy Recommendation
    @FormUrlEncoded
    @POST("drugrecommendation")
    Call<DefaultResponse> drugRecommendation(
            @Field("recommendationId") String recommendationId,
            @Field("creatorId") String creatorId,
            @Field("creatorName") String creatorName,
            @Field("receiverId") String receiverId,
            @Field("receiverName") String receiverName,
            @Field("createDate") String createDate
    );

    //Pharmacy Recommendation
    @FormUrlEncoded
    @POST("drugrecommendationrequest")
    Call<DefaultResponse> DrugRecommendationRequest(
            @Field("requestId") String requestId,
            @Field("requesterId") String requesterId,
            @Field("pharamacistId") String pharamacistId,
            @Field("requestDate") String requestDate
    );

    //Chat Between User and Pharmarcy
    @FormUrlEncoded
    @POST("chatmessage")
    Call<DefaultResponse> chatMessage(
            @Field("messageId") String messageId,
            @Field("sendeId") String sendeId,
            @Field("receiverId") String receiverId,
            @Field("image") String image
    );

    //Chat Between User and Pharmarcy
    @FormUrlEncoded
    @POST("chatroom")
    Call<DefaultResponse> chatRoom(
            @Field("userId") String userId,
            @Field("username") String username,
            @Field("pharmacistId") String pharmacistId,
            @Field("pharmacistName") String pharmacistName
    );

    //Pharmarcy Webboard
    @FormUrlEncoded
    @POST("articleboard")
    Call<DefaultResponse> articleBoard(
            @Field("articleId") String articleId,
            @Field("articleName") String articleName,
            @Field("writerId") String writerId,
            @Field("articleContent") String articleContent
    );

    //Question
    @FormUrlEncoded
    @POST("questionrequest")
    Call<DefaultResponse> questionRequest(
            @Field("questionId") String questionId,
            @Field("textMessage") String textMessage,
            @Field("askerAccountId") String askerAccountId,
            @Field("askerName") String askerName
    );

    //Answer
    @FormUrlEncoded
    @POST("answerrequest")
    Call<DefaultResponse> answerRequest(
            @Field("answerId") String answerId,
            @Field("textMessage") String textMessage,
            @Field("questionId") String questionId,
            @Field("answererName") String answererName
    );

}
