package com.transdignity.driver.network;

import com.google.gson.JsonObject;
import com.transdignity.driver.fragments.bankDetailsFragments.ShowBankDetailResponseModel;
import com.transdignity.driver.models.SignupResponseModel;
import com.transdignity.driver.models.auth.AboutUsResponse;
import com.transdignity.driver.models.auth.ChangePasswordReq;
import com.transdignity.driver.models.auth.ChangePasswordResponse;
import com.transdignity.driver.models.auth.ForgetRequest;
import com.transdignity.driver.models.auth.ForgetResponse;
import com.transdignity.driver.models.auth.LoginRequest;
import com.transdignity.driver.models.auth.LoginResponse;
import com.transdignity.driver.models.auth.LogoutRequest;
import com.transdignity.driver.models.auth.LogoutResponse;
import com.transdignity.driver.models.auth.ProfileDetailsResponse;
import com.transdignity.driver.models.auth.SignupRequest;
import com.transdignity.driver.models.auth.SignupRequestModel;
import com.transdignity.driver.models.auth.SignupResponse;
import com.transdignity.driver.models.auth.pofileEdit.EditProfileRequest;
import com.transdignity.driver.models.auth.pofileEdit.EditProfileResponse;
import com.transdignity.driver.models.bankDetail.AddAccountDetailRequestModel;
import com.transdignity.driver.models.bankDetail.AddAccountDetailResponseModel;
import com.transdignity.driver.models.bankDetail.ModeOfPaymentRequest;
import com.transdignity.driver.models.bankDetail.ModeOfPaymentResponse;
import com.transdignity.driver.models.bankDetail.NewAddBankDetailModel;
import com.transdignity.driver.models.bankDetail.SendClientAddBankMerchantResponseResponseModel;
import com.transdignity.driver.models.bankDetail.SendClintAddBankMerchantResponseRequestModel;
import com.transdignity.driver.models.bankDetail.ShowAccountDetailResponseModel;
import com.transdignity.driver.models.contactus.ContactUsResponse;
import com.transdignity.driver.models.payment.EarningListModel;
import com.transdignity.driver.models.rating.RatignLIstResponseModel;
import com.transdignity.driver.models.requestService.AllServiceRequestDetailResponse;
import com.transdignity.driver.models.requestService.PickupDriverRequest;
import com.transdignity.driver.models.requestService.PickupDriverResponse;
import com.transdignity.driver.models.requestService.ReachedDriverRequestModel;
import com.transdignity.driver.models.requestService.ReachedDriverResponseModel;
import com.transdignity.driver.models.requestService.RemovalSplDetialsResponse;
import com.transdignity.driver.models.requestService.DecedentDetailsResponse;
import com.transdignity.driver.models.rideDetail.RideDetailResponseModel;
import com.transdignity.driver.models.trackingDriverStatus.TrackingDriver;
import com.transdignity.driver.models.vehicle.VehicleBrandResponseModel;
import com.transdignity.driver.models.vehicle.VehicleColorResponseModel;
import com.transdignity.driver.models.vehicle.VehicleDetailsResponseModel;
import com.transdignity.driver.models.vehicle.VehicleModelResponseModel;
import com.transdignity.driver.models.vehicle.VehicleTypeResponseModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Ram Bhawan on 12/09/18.
 */

public interface ApiInterface {
    @POST("api/auth/login")
    Call<LoginResponse> loginApi(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<SignupResponse> signupApi(@Body SignupRequest signupRequest);

    @POST("api/auth/driversignup")
    Call<SignupResponseModel> registerApi(@Body SignupRequestModel signupRequest);

    @POST("api/auth/forgetpassword")
    Call<ForgetResponse> forgetApi(@Body ForgetRequest request);


    @POST("api/auth/logout")
    Call<LogoutResponse> logoutApi(@Header("token") String token, @Body LogoutRequest request);

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

    @POST("api/auth/set_online_status")
    Call<JsonObject> updateOfflineStatusApi(@Header("token") String token, @Body JsonObject request);

    @GET("api/auth/online_info/{user_id}")
    Call<JsonObject> onlineAvailablityStatusApi(@Header("token") String token);

    //TODO: request service Api

    //todo: this api is for accept, reject, and drop
    @POST("api/request/change_cd_status")
    Call<RemovalSplDetialsResponse> acceptRejectApi(@Header("token") String token, @Body JsonObject request);

    //todo: reached to removal specialist
    @GET("api/request/reached_removal_specialists_location/{request_id}")
    Call<RemovalSplDetialsResponse> reachedToRsApi(@Header("token") String token, @Path("request_id")String request_id);

    //todo:  removal specialist details
    @GET("api/request/get_removal_specialists_detail/{request_id}")
    Call<RemovalSplDetialsResponse> removalsplDetailsApi(@Header("token") String token, @Path("request_id")String request_id);

    //todo: Decedent  details
    @GET("api/request/decendantdetail/{request_id}")
    Call<DecedentDetailsResponse> decedentDetailsApi(@Header("token") String token, @Path("request_id")String request_id);

    //todo: this api is used for reched to decedent
    @POST("api/request/reached_cab_driver")
    Call<JsonObject> reachedToDecedentApi(@Header("token") String token, @Body JsonObject request);

    //todo: picked removal specialist
    @POST("api/request/picked_removal_specialist")
    Call<DecedentDetailsResponse> pickedRsApi(@Header("token") String token, @Body JsonObject request);


    //todo:update location............
    @POST("api/request/cancel_ride")
    Call<JsonObject> cancelRideApi(@Header("token") String token,@Body JsonObject jsonObject);
    //todo:update location............
    @POST("api/auth/update_user_location/{user_id}")
    Call<JsonObject> updateLatlongApi(@Header("token") String token,@Path("user_id")String userId, @Body JsonObject request);

    //todo: Request Restore Api
    @GET("api/request/assigned_request_to_cd/{user_id}")
    Call<JsonObject> reStoreRequestApi(@Header("token") String token, @Path("user_id")String user_id);

    //todo: Request Details Api
    @GET("api/request/cd_requestdetail/{user_id}/{request_id}")
    Call<AllServiceRequestDetailResponse> getRequestDetailApi(@Header("token") String token, @Path("user_id")String user_id, @Path("request_id")String request_id);

    //todo: Reached Driver  Api
    @POST("api/request/reached_cab_driver")
    Call<ReachedDriverResponseModel> reachedDriverApi(@Header("token") String token, @Body ReachedDriverRequestModel request);

    //todo: Reached Driver Pickup  Api
    @POST("api/request/picked_decendent")
    Call<PickupDriverResponse> pickupDriverApi(@Header("token") String token, @Body PickupDriverRequest request);

    @GET("api/request/tracking_status/{request_id}")
    Call<TrackingDriver> trackStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @POST("api/setting/addbankaccount_detail")
    Call<AddAccountDetailResponseModel> addAccountDetail(@Header("token") String token, @Body AddAccountDetailRequestModel request);


    @GET("api/setting/bankaccountdetail/{user_id}")
    Call<ShowBankDetailResponseModel> accountDetailApi(@Header("token") String token, @Path("user_id") String userId);

    @POST("api/setting/updatemodeof_payment")
    Call<ModeOfPaymentResponse> modeOfPayment(@Header("token") String token, @Body ModeOfPaymentRequest request);

    // PAYMENT
    @FormUrlEncoded
    @POST("transdignity-payments/api/")
    Call<NewAddBankDetailModel> paymentApi(@FieldMap Map<String, String> fieldsMap);

    @POST("api/setting/addbankaccount_detail")
    Call<SendClientAddBankMerchantResponseResponseModel> sendMerchantAddBankResponse(@Header("token") String token, @Body SendClintAddBankMerchantResponseRequestModel request);


   @GET("api/reports/requestpayment/{usergroupid}/{userid}")
    Call<EarningListModel> paymentHistory(@Header("token") String token, @Path("usergroupid")String user_group_id, @Path("userid")String user_id);

    @GET("api/setting/all_vehicletypes/{user_id}")
    Call<VehicleTypeResponseModel> vehicleTypeList(@Header("token") String token, @Path("user_id")String user_id);

    @GET("api/setting/all_vehiclebrands/{user_id}")
    Call<VehicleBrandResponseModel> vehicleBrandList(@Header("token") String token, @Path("user_id")String user_id);

    @GET("api/setting/all_vehiclemodels/{user_id}")
    Call<VehicleModelResponseModel> vehicleModelList(@Header("token") String token, @Path("user_id")String user_id);

    @GET("api/setting/all_vehiclecolors/{user_id}")
    Call<VehicleColorResponseModel> vehicleColorList(@Header("token") String token, @Path("user_id")String user_id);

    @GET("api/setting/vehicledetail/{USERID}")
    Call<VehicleDetailsResponseModel> vehicleDetailList(@Header("token") String token, @Path("USERID")String user_id);

    @Multipart
    @POST("api/setting/savevehicle_detail")
    public Call<JsonObject> addVehicleDetails(@Header("token") String token,@Part MultipartBody.Part doc1,@Part MultipartBody.Part doc2,@Part MultipartBody.Part doc3,@Part("user_id") RequestBody user_id,
                                              @Part("user_group_id") RequestBody user_group_id,
                                              @Part("cab_no") RequestBody cab_no,
                                              @Part("registration_no") RequestBody registration_no,
                                              @Part("insurance_no") RequestBody insurance_no,
                                              @Part("vehicle_brand_id") RequestBody vehicle_brand_id,
                                              @Part("vehicle_model_id") RequestBody vehicle_model_id,
                                              @Part("vehicle_type_id") RequestBody vehicle_type_id,
                                              @Part("vehicle_color_id") RequestBody vehicle_color_id,
                                              @Part("pollution_doc") RequestBody pollution_doc,
                                              @Part("registration_doc") RequestBody registration_doc,
                                              @Part("cab_name") RequestBody cab_name,
                                              @Part("cab_id") RequestBody cab_id,
                                              @Part("owner_name") RequestBody owner_name,
                                              @Part("captain_name") RequestBody captain_name,
                                              @Part("location") RequestBody location,
                                              @Part("basrform") RequestBody basrform);

    @GET("api/request/tracking_status/{request_id}")
    Call<JsonObject> trackDriverStatusApi(@Header("token") String token, @Path("request_id") String requestId);

    @GET("api/request/cd_requestdetail/{user_id}/{request_id}")
    Call<RideDetailResponseModel> getDriverRequestDetailApi(@Header("token") String token, @Path("user_id")String user_id, @Path("request_id")String request_id);

    @GET("api/reports/ratingrequestlist/{group_id}/{user_id}")
    Call<RatignLIstResponseModel> getDriverRatingApi(@Header("token") String token, @Path("group_id")String group_id, @Path("user_id")String user_id);

    @GET("api/request/driver_tracking_status/{user_id}/{request_id}")
    Call<JsonObject> getDriverTrackApi(@Header("token") String token, @Path("user_id")String user_id, @Path("request_id")String request_id);

    //todo: this api is used for burial to decedent
    @POST("api/request/burial_insea")
    Call<JsonObject> doneBurialApi(@Header("token") String token, @Body JsonObject request);

    @GET("api/request/assigned_request_to_cd/{user_id}")
    Call<JsonObject> currentRideApi(@Header("token") String token, @Path("user_id")String user_id);

}