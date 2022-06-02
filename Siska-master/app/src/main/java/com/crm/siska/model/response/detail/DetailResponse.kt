package com.crm.siska.model.response.detail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @Expose
    @SerializedName("AddDate")
    val addDate: String,
    @Expose
    @SerializedName("CatatanSales")
    val catatanSales: Any,
    @Expose
    @SerializedName("ClosingAmount")
    val closingAmount: Int,
    @Expose
    @SerializedName("EmailProspect")
    val emailProspect: String,
    @Expose
    @SerializedName("Fu")
    val fu: Int,
    @Expose
    @SerializedName("FuDate")
    val fuDate: String?,
    @Expose
    @SerializedName("gender")
    val gender: List<Gender>,
    @Expose
    @SerializedName("GenderID")
    val genderID: Int,
    @Expose
    @SerializedName("Hot")
    val hot: Int,
    @Expose
    @SerializedName("Hp")
    val hp: String,
    @Expose
    @SerializedName("JenisKelamin")
    val jenisKelamin: Any,
    @Expose
    @SerializedName("KodeNegara")
    val kodeNegara: String,
    @Expose
    @SerializedName("KodeProject")
    val kodeProject: String,
    @Expose
    @SerializedName("KodeSales")
    val kodeSales: String,
    @Expose
    @SerializedName("KotaDomisili")
    val kotaDomisili: String,
    @Expose
    @SerializedName("Kota")
    val kota: String,
    @Expose
    @SerializedName("NamaPlatform")
    val namaPlatform: String,
    @Expose
    @SerializedName("NamaCampaign")
    val campaign: String,
    @Expose
    @SerializedName("provinsi")
    val provinsi: List<Provinsi>,
    @Expose
    @SerializedName("Message")
    val message: String,
    @Expose
    @SerializedName("NamaProspect")
    val namaProspect: String,
    @Expose
    @SerializedName("notInterested")
    val notInterested: List<NotInterested>,
    @Expose
    @SerializedName("NotInterestedID")
    val notInterestedID: Int,
    @Expose
    @SerializedName("pekerjaan")
    val pekerjaan: List<Pekerjaan>,
    @Expose
    @SerializedName("PekerjaanID")
    val pekerjaanID: Int,
    @Expose
    @SerializedName("penghasilan")
    val penghasilan: List<Penghasilan>,
    @Expose
    @SerializedName("PenghasilanID")
    val penghasilanID: Int,
    @Expose
    @SerializedName("ProspectID")
    val prospectID: Int,
    @Expose
    @SerializedName("ProvinsiDomisiliID")
    val provinsiDomisiliID : Int,
    @Expose
    @SerializedName("ProvinsiDomisiliName")
    val provinsiDomisiliName : String,
    @Expose
    @SerializedName("ProvinsiID")
    val provinsiID : Int,
    @Expose
    @SerializedName("ProvinsiName")
    val provinsiName : String,
    @Expose
    @SerializedName("RangePenghasilan")
    val rangePenghasilan: String,
    @Expose
    @SerializedName("RangeUsia")
    val rangeUsia: String,
    @Expose
    @SerializedName("Status")
    val status: String,
    @Expose
    @SerializedName("TempatKerjaID")
    val tempatKerjaID: Int,
    @Expose
    @SerializedName("TempatTinggalID")
    val tempatTinggalID: Int,
    @Expose
    @SerializedName("TipePekerjaan")
    val tipePekerjaan: String,
    @Expose
    @SerializedName("unit")
    val unit: List<Unit>,
    @Expose
    @SerializedName("Unit")
    val Unit: String,
    @Expose
    @SerializedName("UnitID")
    val unitID: Int,
    @Expose
    @SerializedName("UnitName")
    val unitName: String,
    @Expose
    @SerializedName("usia")
    val usia: List<Usia>,
    @Expose
    @SerializedName("UsiaID")
    val usiaID: Int
)