package com.cts.removalspecialist.network;

import com.cts.removalspecialist.fragments.bankDetailsFragments.ShowBankDetailResponseModel;
import com.cts.removalspecialist.models.auth.AboutUsResponse;
import com.cts.removalspecialist.models.auth.ChangePasswordReq;
import com.cts.removalspecialist.models.auth.ChangePasswordResponse;
import com.cts.removalspecialist.models.auth.ForgetRequest;
import com.cts.removalspecialist.models.auth.ForgetResponse;
import com.cts.removalspecialist.models.auth.LoginRequest;
import com.cts.removalspecialist.models.auth.LoginResponse;
import com.cts.removalspecialist.models.auth.ProfileDetailsResponse;
import com.cts.removalspecialist.models.auth.pofileEdit.EditProfileRequest;
import com.cts.removalspecialist.models.auth.pofileEdit.EditProfileResponse;
import com.cts.removalspecialist.models.bankDetail.AddAccountDetailRequestModel;
import com.cts.removalspecialist.models.bankDetail.AddAccountDetailResponseModel;
import com.cts.removalspecialist.models.bankDetail.ModeOfPaymentRequest;
import com.cts.removalspecialist.models.bankDetail.ModeOfPaymentResponse;
import com.cts.removalspecialist.models.bankDetail.NewAddBankDetailModel;
import com.cts.removalspecialist.models.bankDetail.SendClientAddBankMerchantResponseResponseModel;
import com.cts.removalspecialist.models.bankDetail.SendClintAddBankMerchantResponseRequestModel;
import com.cts.removalspecialist.models.bankDetail.ShowAccountDetailResponseModel;
import com.cts.removalspecialist.models.contactus.ContactUsResponse;
import com.cts.removalspecialist.models.notifications.NotificationListResponse;
import com.cts.removalspecialist.models.payment.EarningListModel;
import com.cts.removalspecialist.models.rating.RatignLIstResponseModel;
import com.cts.removalspecialist.models.requestDetails.RequestDetailsResponse;
import com.cts.removalspecialist.models.requestList.RequestListResponse;
import com.cts.removalspecialist.models.viewDetails.AirportRemovalDetailPageModel;
import com.cts.removalspecialist.models.viewDetails.AshesDetailPageMOdel;
import com.cts.removalspecialist.models.viewDetails.BurialSeaDetailPageModel;
import com.cts.removalspecialist.models.viewDetails.DescendantDetailPageModel;
import com.cts.removalspecialist.models.viewDetails.UsVeteranDetailPagesModel;
import com.google.gson.JsonObject;

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

    @POST("api/auth/forgetpassword")
    Call<ForgetResponse> forgetApi(@Body ForgetRequest request);

    @POST("api/setting/contact_us")
    Call<ContactUsResponse> callContactUsApi(@Header("token") String token, @Body JsonObject request);

    @POST("api/auth/changepassword")
    Call<ChangePasswordResponse> changePasswordApi(@Header("token") String token, @Body ChangePasswordReq request);

    @GET("api/auth/profiledetails/{id}/{user_group_id}")
    Call<ProfileDetailsResponse> getProfileDetailsApi(@Header("token") String token, @Path("id") String id, @Path("user_group_id") String groupid);

    @POST("api/auth/edit_profile")
    Call<EditProfileResponse> editProfileApi(@Header("token") String token, @Body EditProfileRequest request);

    @GET("api/setting/page")
    Call<AboutUsResponse> aboutUstApi(@Header("token") String token);

    @GET("api/request/rs_requestlist/{id}")
    Call<RequestListResponse> requestListApi(@Header("token") String token, @Path("id") String id, @Query("per_page") String per_page);

    @GET("api/request/rs_requestlist/{id}")
    Call<RequestListResponse> filterRequestListApi(@Header("token") String token, @Path("id") String id, @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("status") String status, @Query("per_page") String per_page);

    @GET("api/request/rs_requestdetail/{userid}/{requestid}")
    Call<RequestDetailsResponse> getRequestDetailsApi(@Header("token") String token, @Path("userid") String user_id, @Path("requestid") String id);

    @POST("api/auth/logout")
    Call<JsonObject> logoutApi(@Header("token") String token, @Body JsonObject request);

    @POST("api/request/change_rs_status")
    Call<JsonObject> changeStatusApi(@Header("token") String token, @Body JsonObject request);

    @GET("api/setting/notifications/{userid}")
    Call<NotificationListResponse> notificationListApi(@Header("token") String token, @Path("userid") String userId, @Query("per_page") String page);

    @POST("api/auth/update_user_location/{user_id}")
    Call<JsonObject> updateLatlongApi(@Header("token") String token,@Path("user_id")String userId, @Body JsonObject request);

    @POST("api/auth/set_online_status")
    Call<JsonObject> updateOfflineStatusApi(@Header("token") String token, @Body JsonObject request);

    @GET("api/auth/online_info/{user_id}")
    Call<JsonObject> onlineAvailablityStatusApi(@Header("token") String token);


    @POST("api/setting/addbankaccount_detail")
    Call<AddAccountDetailResponseModel> addAccountDetail(@Header("token") String token, @Body AddAccountDetailRequestModel request);


    @GET("api/setting/bankaccountdetail/{user_id}")
    Call<ShowBankDetailResponseModel> accountDetailApi(@Header("token") String token, @Path("user_id") String userId);

    @POST("api/setting/updatemodeof_payment")
    Call<ModeOfPaymentResponse> modeOfPayment(@Header("token") String token, @Body ModeOfPaymentRequest request);

    @GET("api/request/requestdetail/{id}")
    Call<AirportRemovalDetailPageModel> getRequestAirportRemovalApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<AshesDetailPageMOdel> getRequestAshesPageApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<DescendantDetailPageModel> getRequestDesDetailsApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<UsVeteranDetailPagesModel> getRequestUSVeteranApi(@Header("token") String token, @Path("id") String id);

    @GET("api/request/requestdetail/{id}")
    Call<BurialSeaDetailPageModel> getRequestBurialAtSeaApi(@Header("token") String token, @Path("id") String id);





    // PAYMENT
    @FormUrlEncoded
    @POST("transdignity-payments/api/")
    Call<NewAddBankDetailModel> paymentApi(@FieldMap Map<String, String> fieldsMap);

    @POST("api/setting/addbankaccount_detail")
    Call<SendClientAddBankMerchantResponseResponseModel> sendMerchantAddBankResponse(@Header("token") String token, @Body SendClintAddBankMerchantResponseRequestModel request);

    @GET("api/reports/requestpayment/{usergroupid}/{userid}")
    Call<EarningListModel> paymentHistory(@Header("token") String token, @Path("usergroupid")String user_group_id, @Path("userid")String user_id);

    @GET("api/reports/ratingrequestlist/{group_id}/{user_id}")
    Call<RatignLIstResponseModel> getDriverRatingApi(@Header("token") String token, @Path("group_id")String group_id, @Path("user_id")String user_id);

    /*


    @POST("api/request/add_request")
    Call<NewRequestResponse> newRequestApi(@Body NewRequestReq request);

    @GET("api/request/hospitaltypes")
    Call<HospitalTypeResponse> hospitalTypeListApi();

    @POST("api/setting/contact_us")
    Call<ContactUsResponse> callContactUsApi(@Body JsonObject request);




    @GET("api/setting/notifications/{userid}")
    Call<NotificationListResponse> notificationListApi(@Path("userid") String userId, @Query("per_page") String page);

    @GET("api/setting/notifications/{userid}")
    Call<NotificationCountResponse> notificationCountApi(@Path("userid") String userId);
*/


}