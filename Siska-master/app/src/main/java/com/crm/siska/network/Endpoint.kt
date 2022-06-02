package com.crm.siska.network


import com.crm.siska.model.response.Wrapper
import com.crm.siska.model.response.detail.DetailResponse
import com.crm.siska.model.response.device.DeviceResponse
import com.crm.siska.model.response.history.HistoryResponse
import com.crm.siska.model.response.home.HomeResponse
import com.crm.siska.model.response.input.InputResponse
import com.crm.siska.model.response.input.ProspectResponse
import com.crm.siska.model.response.leads.LeadsResponse
import com.crm.siska.model.response.login.LoginResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface Endpoint {

    @FormUrlEncoded
    @POST("loginAppKotlin")
    fun login(@Field("UsernameKP") username:String,
              @Field("PasswordKP") password:String) : Observable<Wrapper<LoginResponse>>

//    @FormUrlEncoded
//    @POST("register")
//    fun register(@Field("name") name:String,
//                 @Field("email") email:String,
//                 @Field("password") password:String,
//                 @Field("password_confirmation") password_confirmation:String,
//                 @Field("address") address:String,
//                 @Field("city") city:String,
//                 @Field("houseNumber") houseNumber:String,
//                 @Field("phoneNumber") phoneNumber:String) : Observable<Wrapper<LoginResponse>>

    @GET("user")
    fun user(): Observable<Wrapper<HomeResponse>>

    @Multipart
    @POST("change-profil")
    fun changeProfileImg(
        @Part file: MultipartBody.Part
    ) : Observable<Wrapper<Any>>

//ALL LEADS
    @GET("leads/all/aktif")
    fun allaktif(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/all/arsip")
    fun allarsip(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/all/hot")
    fun allhot(): Observable<Wrapper<LeadsResponse>>

//MY LEADS
    @GET("leads/my/aktif")
    fun myaktif(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/my/arsip")
    fun myarsip(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/my/hot")
    fun myhot(): Observable<Wrapper<LeadsResponse>>

//DEV LEADS
    @GET("leads/dev/aktif")
    fun devaktif(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/dev/arsip")
    fun devarsip(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/dev/hot")
    fun devhot(): Observable<Wrapper<LeadsResponse>>

    @GET("leads/new")
    fun leadsnew(): Observable<Wrapper<LeadsResponse>>

    @GET("detail_leads/{ProspectID}")
    fun detail(@Path(value = "ProspectID") prospectId:Int): Observable<Wrapper<DetailResponse>>

    @GET("get-kota/{province_id}")
    fun getKota(@Path(value = "province_id") province_id:Int): Observable<Wrapper<InputResponse>>

    @GET("get-kota/{province_id}")
    fun getProvince(@Path(value = "province_id") province_id:Int): Observable<Wrapper<InputResponse>>

    @GET("history_sales")
    fun history(): Observable<Wrapper<HistoryResponse>>

    @GET("data_input")
    fun data(): Observable<Wrapper<InputResponse>>

    @FormUrlEncoded
    @POST("prospect")
    fun prospect(@Field("NamaProspect") namaProspect:String,
                 @Field("EmailProspect") emailProspect:String,
                 @Field("Hp") hp:String,
                 @Field("Message") message:String,
                 @Field("GenderID") genderId:Int,
                 @Field("UsiaID") usiaId:Int,
                 @Field("PekerjaanID") pekerjaanId:Int,
                 @Field("PenghasilanID") penghasilanId:Int,
                 @Field("UnitID") unitId:Int,
                 @Field("TempatTinggalID") tempatTinggalId:Int,
                 @Field("TempatKerjaID") tempatKerjaId:Int,
                ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("prospect/updates")
    fun prospectupdate(@Field("ProspectID") prospectID:Int,
                 @Field("GenderID") genderId:Int,
                 @Field("UsiaID") usiaId:Int,
                 @Field("PekerjaanID") pekerjaanId:Int,
                 @Field("PenghasilanID") penghasilanId:Int,
                 @Field("UnitID") unitId:Int,
                 @Field("TempatTinggalID") tempatTinggalId:Int,
                 @Field("TempatKerjaID") tempatKerjaId:Int,
                 @Field("CatatanSales") catatanSales:String,
    ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("fuwa")
    fun fuwa(@Field("ProspectID") prospectID:Int
    ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("futelp")
    fun futelp(@Field("ProspectID") prospectID:Int
    ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("leads/hot")
    fun hotleads(@Field("ProspectID") prospectID:Int
    ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("device/store")
    fun storetokenfcm(@Field("DeviceID") deviceID:String,
                      @Field("TokenFCM") tokenFcm:String,
                      @Field("UsernameKP") usernameKp:String
    ) : Observable<Wrapper<DeviceResponse>>

    @FormUrlEncoded
    @POST("closing")
    fun closing(@Field("ProspectID") prospectID:Int,
                      @Field("UnitID") unitID:Int,
                      @Field("KetUnit") ketUnit:String,
                      @Field("HargaJual") hargaJual:Int
    ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("notinterested")
    fun notinterested(@Field("ProspectID") prospectID:Int,
                      @Field("NotInterestedID") notInterestedID:Int,
    ) : Observable<Wrapper<ProspectResponse>>

    @FormUrlEncoded
    @POST("change-password")
    fun changePassword(@Field("old_password") old_password:String,
                      @Field("new_password") new_password:String,
                      @Field("confirm_password") confirm_password:String,
    ) : Observable<Wrapper<ProspectResponse>>

//    @Multipart
//    @POST("user/photo")
//    fun registerPhoto(@Part profileImage:MultipartBody.Part) : Observable<Wrapper<Any>>


}