package com.transdignity.deathserviceprovider.network;

import com.google.gson.JsonObject;
import com.transdignity.deathserviceprovider.models.auth.AboutUsResponse;
import com.transdignity.deathserviceprovider.models.auth.ChangePasswordReq;
import com.transdignity.deathserviceprovider.models.auth.ChangePasswordResponse;
import com.transdignity.deathserviceprovider.models.auth.ForgetRequest;
import com.transdignity.deathserviceprovider.models.auth.ForgetResponse;
import com.transdignity.deathserviceprovider.models.auth.LoginRequest;
import com.transdignity.deathserviceprovider.models.auth.LoginResponse;
import com.transdignity.deathserviceprovider.models.auth.LogoutRequest;
import com.transdignity.deathserviceprovider.models.auth.LogoutResponse;
import com.transdignity.deathserviceprovider.models.auth.ProfileDetailsResponse;
import com.transdignity.deathserviceprovider.models.auth.SignupRequest;
import com.transdignity.deathserviceprovider.models.auth.SignupResponse;
import com.transdignity.deathserviceprovider.models.auth.pofileEdit.EditProfileRequest;
import com.transdignity.deathserviceprovider.models.auth.pofileEdit.EditProfileResponse;
import com.transdignity.deathserviceprovider.models.bankDetail.AddAccountDetailRequestModel;
import com.transdignity.deathserviceprovider.models.bankDetail.AddAccountDetailResponseModel;
import com.transdignity.deathserviceprovider.models.bankDetail.ClientPaymentModel;
import com.transdignity.deathserviceprovider.models.bankDetail.SendClientPaymentResponseRequestModel;
import com.transdignity.deathserviceprovider.models.bankDetail.SendClientPaymentResponseResponseModel;
import com.transdignity.deathserviceprovider.models.bankDetail.ShowAccountDetailResponseModel;
import com.transdignity.deathserviceprovider.models.checkDriver.CheckDriverRequestModel;
import com.transdignity.deathserviceprovider.models.checkDriver.CheckDriverResponseModel;
import com.transdignity.deathserviceprovider.models.contactus.ContactUsResponse;
import com.transdignity.deathserviceprovider.models.country.CountriesModel;
import com.transdignity.deathserviceprovider.models.notifications.NotificationCountResponse;
import com.transdignity.deathserviceprovider.models.notifications.NotificationListResponse;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryModel;
import com.transdignity.deathserviceprovider.models.payment.PaymentHistoryResponseModel;
import com.transdignity.deathserviceprovider.models.rating.RatingRequestModel;
import com.transdignity.deathserviceprovider.models.rating.RatingResponseModel;
import com.transdignity.deathserviceprovider.models.request.RequestListResponse;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.AirportRemovalDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.AshesDetailPageMOdel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.BurialSeaDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.CasketDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.CourierDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.DescendantDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.FlowerDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.LimoDetailPageModel;
import com.transdignity.deathserviceprovider.models.request.ServiceDetailPage.UsVeteranDetailPagesModel;
import com.transdignity.deathserviceprovider.models.request.Services.AirportArrivalDepartureResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.AshesRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.AshesResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.BurealSeeRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.BurialSeeResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.CasketRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.CasketResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.CourierRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.CourierResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.FlowerRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.FlowerResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.LimoRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.LimoResponseModel;
import com.transdignity.deathserviceprovider.models.request.Services.UsVeteranRequestModel;
import com.transdignity.deathserviceprovider.models.request.Services.UsVeteranResponseModel;
import com.transdignity.deathserviceprovider.models.request.newRequest.HospitalTypeResponse;
import com.transdignity.deathserviceprovider.models.request.newRequest.NewRequestReq;
import com.transdignity.deathserviceprovider.models.request.newRequest.NewRequestResponse;
import com.transdignity.deathserviceprovider.models.tracking.AirportTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.AshesTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.BurialTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.CasketTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.CourierTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.DecsendantTrakingModel;
import com.transdignity.deathserviceprovider.models.tracking.FlowerTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.LimooTrackingModel;
import com.transdignity.deathserviceprovider.models.tracking.UsVeteranTrakingModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ram Bhawan on 12/09/18.
 */

public interface ApiInterface {

    @POST("api/auth/login")
    Call<LoginResponse> loginApi(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<SignupResponse> signupApi(@Body SignupRequest signupRequest);

    @POST("api/auth/forgetpassword")
    Call<ForgetResponse> forgetApi(@Body ForgetRequest request);

    @POST("api/auth/changepassword")
    Call<ChangePasswordResponse> changePasswordApi(@Header("token") String token, @Body ChangePasswordReq request);

    @GET("api/request/requestlist/{id}")
    Call<RequestListResponse> requestListApi(@Header("token") String token,@Path("id") String id, @Query("per_page") String per_page);

    @GET("api/request/requestlist/{id}")
    Call<RequestListResponse> filterRequestListApi(@Header("token") String token,@Path("id") String id, @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("status") String status, @Query("per_page") String per_page);

    @GET("api/auth/profiledetails/{id}/{user_group_id}")
    Call<ProfileDetailsResponse> getProfileDetailsApi(@Header("token") String token,@Path("id") String id,@Path("user_group_id") String groupid);

    @POST("api/auth/edit_profile")
    Call<EditProfileResponse> editProfileApi(@Header("token") String token,@Body EditProfileRequest request);

    @POST("api/request/add_request")
    Call<NewRequestResponse> newRequestApi(@Header("token") String token, @Body NewRequestReq request);

    @GET("api/request/hospitaltypes")
    Call<HospitalTypeResponse> hospitalTypeListApi(@Header("token") String token);

    @POST("api/setting/contact_us")
    Call<ContactUsResponse> callContactUsApi(@Header("token") String token,@Body JsonObject request);

    @GET("api/request/requestdetail/{id}")
    Call<DescendantDetailPageModel> getRequestDetailsApi(@Header("token") String token, @Path("id") String id);

    @GET("api/setting/page")
    Call<AboutUsResponse> aboutUstApi(@Header("token") String token);

    @POST("api/auth/logout")
    Call<LogoutResponse> logoutApi(@Header("token") String token,@Body LogoutRequest request);

    @GET("api/setting/notifications/{userid}")
    Call<NotificationListResponse> notificationListApi(@Header("token") String token,@Path("userid") String userId, @Query("per_page") String page);

    @GET("api/setting/notifications/{userid}")
    Call<NotificationCountResponse> notificationCountApi(@Header("token") String token,@Path("userid") String userId);

    @GET("api/request/tracking_status/{request_id}")
    Call<DecsendantTrakingModel> trackStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @POST("api/request/add_request")
    Call<LimoResponseModel> limoServiceApi(@Header("token") String token, @Body LimoRequestModel request);

    @POST("api/request/add_request")
    Call<CasketResponseModel> CasketServiceApi(@Header("token") String token, @Body CasketRequestModel request);

    @POST("api/request/add_request")
    Call<FlowerResponseModel> FlowerServiceApi(@Header("token") String token, @Body FlowerRequestModel request);

    @POST("api/request/add_request")
    Call<AshesResponseModel> AshesServiceApi(@Header("token") String token, @Body AshesRequestModel request);

    @POST("api/request/add_request")
    Call<CourierResponseModel> CourierServiceApi(@Header("token") String token, @Body CourierRequestModel request);


    @GET("api/request/requestdetail/{id}")
    Call<LimoDetailPageModel> getRequestLimoDetailsPageApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<CasketDetailPageModel> getRequestCasketPageApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<FlowerDetailPageModel> getRequestFlowerPageApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<AshesDetailPageMOdel> getRequestAshesPageApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<CourierDetailPageModel> getRequestCourierPageApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<AirportRemovalDetailPageModel> getRequestAirportRemovalApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<UsVeteranDetailPagesModel> getRequestUSVeteranApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<BurialSeaDetailPageModel> getRequestBurialAtSeaApi(@Header("token") String token, @Path("id") String id);



    @GET("api/request/tracking_status/{request_id}")
    Call<LimooTrackingModel> trackLimoStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<CasketTrackingModel> trackCasketStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<FlowerTrackingModel> trackFlowerStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<AshesTrackingModel> trackAshesStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<AirportTrackingModel> trackAirportStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<CourierTrackingModel> trackCourierStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<UsVeteranTrakingModel> trackUSVeteranStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/tracking_status/{request_id}")
    Call<BurialTrackingModel> trackBurialStatusApi(@Header("token") String token, @Path("request_id") String requestId);


    @POST("api/setting/addbankaccount_detail")
    Call<AddAccountDetailResponseModel> addAccountDetail(@Header("token") String token, @Body AddAccountDetailRequestModel request);


    @GET("api/setting/bankaccountdetail/{user_id}")
    Call<ShowAccountDetailResponseModel> accountDetailApi(@Header("token") String token, @Path("user_id") String userId);


    @POST("api/request/add_airport_request")
    Call<AirportArrivalDepartureResponseModel> AirportRemovalServiceApi(@Header("token") String token, @Body JsonObject request);

    // PAYMENT
    @FormUrlEncoded
    @POST("transdignity-payments/api/")
    Call<ClientPaymentModel> paymentApi(@FieldMap Map<String, String> fieldsMap);

    @POST("api/setting/update_request_payment")
    Call<SendClientPaymentResponseResponseModel> sendMerchantPaymentResponse(@Header("token") String token, @Body SendClientPaymentResponseRequestModel request);

    @GET("api/reports/paymenthistory/{group_id}/{user_id}/{pagenumber}")
    Call<PaymentHistoryResponseModel> paymentHistory(@Header("token") String token, @Path("group_id")String user_group_id, @Path("user_id")String user_id, @Path("pagenumber")int page);

    @GET("api/setting/all_countries/{userid}")
    Call<CountriesModel> countryList(@Header("token") String token,@Path("userid")String user_id);

    @POST("api/request/add_request")
    Call<UsVeteranResponseModel> UsVeteranServiceApi(@Header("token") String token, @Body UsVeteranRequestModel request);

    @POST("api/request/add_request")
    Call<BurialSeeResponseModel> BurialSeeServiceApi(@Header("token") String token, @Body BurealSeeRequestModel request);


    @POST("api/request/addrating_request")
    Call<RatingResponseModel> giveRatingApi(@Header("token") String token, @Body RatingRequestModel request);

    @POST("api/request/check_assigned_cd_status")
    Call<CheckDriverResponseModel> checkDriverApi(@Header("token") String token, @Body CheckDriverRequestModel request);


}