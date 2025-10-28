package com.saveetha.findamaid.api

import com.saveetha.findamaid.MaidNotifyResponse
import com.saveetha.findamaid.model.ReviewSubmitResponse
import com.saveetha.findamaid.server.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // ✅ Login API
    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // ✅ Signup API
    @Multipart
    @POST("signup.php")
    fun signupUser(
        @Part("name") name: RequestBody,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("contact_number") contact: RequestBody,
        @Part profile_image: MultipartBody.Part?
    ): Call<SignupResponse>

    // ✅ Upload Maid Details
    @Multipart
    @POST("post_request.php")
    fun uploadMaidDetails(
        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("location") location: RequestBody,
        @Part("experience") experience: RequestBody,
        @Part("language") language: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part("expected_salary") expectedSalary: RequestBody,
        @Part("working_hours") workingHours: RequestBody,
        @Part("category") category: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<PostRequestResponse>

    // ✅ Profile API
    @POST("get_profile.php")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getProfile(@Body requestBody: RequestBody): Call<ProfileResponse>

    // ✅ Maid list & details
    @GET("list_posts.php")
    fun getMaidsByCategory(@Query("category") category: String): Call<MaidListResponse>

    @GET("get_post_by_id.php")
    fun getMaidById(@Query("id") id: Int): Call<MaidDetailResponse>

    // ✅ Reviews
    @GET("get_reviews.php")
    fun getReviews(@Query("maid_post_id") maidPostId: Int): Call<ReviewResponse>

    @FormUrlEncoded
    @POST("submit_review.php")
    fun submitReview(
        @Field("maid_post_id") maidPostId: Int,
        @Field("user_id") userId: Int,
        @Field("user_name") userName: String,
        @Field("rating") rating: Int,
        @Field("comment") comment: String
    ): Call<ReviewSubmitResponse>

    // ✅ Booking Requests
    @FormUrlEncoded
    @POST("get_booking_request.php")
    fun getBookingRequests(@Field("user_id") userId: Int): Call<MaidRequestsResponse>

    @FormUrlEncoded
    @POST("update_booking_status.php")
    fun updateBookingStatus(
        @Field("id") requestId: Int,
        @Field("status") status: String
    ): Call<Map<String, Any>>

    // ✅ Submit address
    @FormUrlEncoded
    @POST("submit_address.php")
    fun submitAddress(@FieldMap params: Map<String, String>): Call<LocationResponse>

    // ✅ User Notifications
    @FormUrlEncoded
    @POST("n_get_notification.php")
    fun getNNotifications(@Field("user_id") userId: String): Call<NNotificationResponse>

    @FormUrlEncoded
    @POST("n_update_status.php")
    fun updateStatus(
        @Field("request_id") requestId: String,
        @Field("status") status: String
    ): Call<GenericResponse>

    @GET("get_user_notifications.php")
    fun getNotifications(@Query("user_id") userId: String): Call<MaidNotificationResponse>

    @GET("get_user_notification.php")
    fun getUserNotifications(@Query("user_id") userId: String): Call<MaidRequestsResponse>

    // ✅ Maid Notifications
    @GET("get_maid_notifications.php")
    fun getMaidNotifications(@Query("owner_id") ownerId: Int): Call<MaidNotifyResponse>

    @FormUrlEncoded
    @POST("submit_request_action.php")
    fun submitRequestAction(
        @Field("username") username: String,
        @Field("action") action: String
    ): Call<MaidNotifyResponse>


    @GET("kget_notifications.php")
    fun kGetNotifications(
        @Query("user_id") userId: Int
    ): Call<NotificationsResponse>

    @GET("n_update_status.php")
    fun kUpdateStatus(@Query("request_id") requestId: Int, @Query("status") status: String): Call<Map<String, Any>>


    // ✅ Search
    @GET("search_maid.php")
    fun searchMaids(@Query("filter") query: String): Call<ServerMaidResponse>
}
