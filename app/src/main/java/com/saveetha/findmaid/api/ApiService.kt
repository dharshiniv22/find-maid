package com.saveetha.findmaid.api

import com.saveetha.findmaid.server.*
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

    @Multipart
    @POST("post_request.php")
    fun uploadMaidDetails(
        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("location") location: RequestBody,
        @Part("experience") experience: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part("expected_salary") expectedSalary: RequestBody,
        @Part("working_hours") workingHours: RequestBody,
        @Part("category") category: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<PostRequestResponse>



    @POST("get_profile.php")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getProfile(@Body requestBody: RequestBody): Call<ProfileResponse>


    @GET("list_posts.php")
    fun getMaidsByCategory(@Query("category") category: String): Call<MaidListResponse>


    @GET("get_post_by_id.php")
    fun getMaidById(@Query("id") id: Int): Call<MaidDetailResponse>

    @GET("search_posts.php")
    fun searchMaids(
        @Query("filter") filter: String
    ): Call<SearchResponse>


    @FormUrlEncoded
    @POST("submit_review.php")
    fun submitReview(
        @Field("maid_name") maidName: String,
        @Field("user_id") userId: Int,
        @Field("rating") rating: Int,
        @Field("comment") comment: String
    ): Call<ReviewSubmitResponse>

    @GET("get_reviews.php")
    fun getReviews(@Query("maid_name") maidName: String): Call<ReviewResponse>

    @FormUrlEncoded
    @POST("get_booking_request.php")
    fun getBookingRequests(
        @Field("user_id") userId: Int
    ): Call<MaidRequestsResponse>

    @FormUrlEncoded
    @POST("submit_address.php")
    fun submitAddress(
        @Field("user_id") userId: String,
        @Field("maid_post_id") maidPostId: String,
        @Field("address") address: String,
        @Field("pincode") pincode: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String
    ): Call<LocationResponse>
    @GET("get_user_requests.php")
    fun getUserRequests(@Query("user_id") userId: Int): Call<MaidRequestsResponse>


    @FormUrlEncoded
    @POST("submit_address.php")
    fun submitAddress(
        @FieldMap params: Map<String, String>
    ): Call<LocationResponse>

    @FormUrlEncoded
    @POST("update_booking_status.php")
    fun updateRequestStatus(
        @Field("id") id: Int,
        @Field("status") status: String
    ): Call<GenericResponse>





    @GET("get_maid_notifications.php")
    fun getMaidNotifications(@Query("owner_id")ownerId:String): Call<MaidNotificationResponse>


    @GET("get_user_notification.php")
    fun getUserNotifications(@Query("user_id") userId: String): Call<MaidRequestsResponse>

    @FormUrlEncoded
    @POST("update_booking_status.php")
    fun updateBookingStatus(
        @Field("id") requestId: Int,
        @Field("status") status: String
    ): Call<StatusResponse>


    @GET("user_notifications.php") // Ensure this matches your PHP backend route
    fun getUserNotifications(
        @Query("user_id") userId: Int
    ): Call<MaidRequestsResponse>
    }







