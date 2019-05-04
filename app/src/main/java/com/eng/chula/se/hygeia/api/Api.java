package com.eng.chula.se.hygeia.api;

import com.eng.chula.se.hygeia.models.DefaultResponse;
import com.eng.chula.se.hygeia.models.LoginResponse;
import com.eng.chula.se.hygeia.models.UsersResponse;

import java.math.BigDecimal;
import java.util.Date;

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

    //----------------------------------------------------------------------------------------------
    //User Register
    @FormUrlEncoded
    @POST("registereduser")
    Call<DefaultResponse> registrationForm(
            @Field("accountId") Integer accountId,
            @Field("password") String password,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("citizenId") String citizenId,
            @Field("birthday") Date birthday,
            @Field("hometown") String hometown,
            @Field("telephoneNumber") String telephoneNumber,
            @Field("email") String email,
            @Field("contact") String contact
    );

    /*@FormUrlEncoded
    @POST("healthInfo")
    Call<DefaultResponse> healthInfo (
            @Field("accountId") Integer accountId,
            @Field("weight") BigDecimal weight,
            @Field("height") BigDecimal height,
            @Field("diseaseList") String diseaseList,
            @Field("allergicList") String allergicList
    );*/

    @FormUrlEncoded
    @POST("pharmacist")
    Call<DefaultResponse> pharmacist (
            @Field("pharmacistId") Integer pharmacistId,
            @Field("pharmacistName") String pharmacistName,
            @Field("pharmacistSurname") String pharmacistSurname,
            @Field("pharmacistLicenseId") String pharmacistLicenseId
    );

    @FormUrlEncoded
    @POST("drugRecRequest")
    Call<DefaultResponse> drugRecRequest (
            @Field("requestId") Integer requestId,
            @Field("requesterId") Integer requesterId,
            @Field("pharamacistId") Integer pharamacistId,
            @Field("requestDate") Date requestDate
    );

    @FormUrlEncoded
    @POST("drugRecommend")
    Call<DefaultResponse> drugRecommend (
            @Field("recommendationId") Integer recommendationId,
            @Field("creatorId") Integer creatorId,
            @Field("creatorName") String creatorName,
            @Field("receiverId") Integer receiverId,
            @Field("receiverName") String receiverName,
            @Field("createDate") Date createDate
    );

    //Google API
    /*@FormUrlEncoded
    @POST("drugStore")
    Call<DefaultResponse> drugStore (
            @Field("storeId") Integer storeId,
            @Field("storeName") String storeName,
            @Field("ownerName") String ownerName,
            @Field("licenseId") String licenseId,
            @Field("storeLocation") String storeLocation,
            @Field("isQualified") String isQualified
    );*/

    @FormUrlEncoded
    @POST("article")
    Call<DefaultResponse> articleBoard (
            @Field("articleId") Integer articleId,
            @Field("articleName") String articleName,
            @Field("writerId") Integer writerId,
            @Field("articleContent") String articleContent
    );

    @FormUrlEncoded
    @POST("question")
    Call<DefaultResponse> questionBoard (
            @Field("questionId") Integer questionId,
            @Field("textMessage") String textMessage,
            @Field("askerAccountId") Integer askerAccountId,
            @Field("askerName") String askerName
    );

    @FormUrlEncoded
    @POST("answer")
    Call<DefaultResponse> answerBoard (
            @Field("answerId") Integer answerId,
            @Field("textMessage") String textMessage,
            @Field("questionId") Integer questionId,
            @Field("answererAccountId") Integer answererAccountId,
            @Field("answererName") String answererName
    );

    //Drug Request
    @FormUrlEncoded
    @POST("drugrequest")
    Call<DefaultResponse> drugRequest(
            @Field("requestId") String requestId,
            @Field("topic") String topic,
            @Field("attachment") String attachment
    );


    /*@FormUrlEncoded
    @POST("chatroom")
    Call<DefaultResponse> chatroom (
            @Field("userId") Integer userId,
            @Field("username") String username,
            @Field("pharmacistId") Integer pharmacistId,
            @Field("pharmacistName") String pharmacistName
    );

    @FormUrlEncoded
    @POST("chatmessage")
    Call<DefaultResponse> chatmessage (
            @Field("messageId") Integer messageId,
            @Field("sendeId") Integer sendeId,
            @Field("receiverId") Integer receiverId,
            @Field("textMessage") String textMessage,
            @Field("imageMessage") String imageMessage
    );
    */
}
