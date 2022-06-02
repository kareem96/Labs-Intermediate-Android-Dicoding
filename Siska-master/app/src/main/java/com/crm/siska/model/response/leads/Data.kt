package com.crm.siska.model.response.leads


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("AcceptDate")
    val acceptDate: String?,
    @Expose
    @SerializedName("AcceptStatus")
    val acceptStatus: Int?,
    @Expose
    @SerializedName("AddDate")
    val addDate: String?,
    @Expose
    @SerializedName("AssignDate")
    val assignDate: String?,
    @Expose
    @SerializedName("BestDayID")
    val bestDayID: String?,
    @Expose

    @SerializedName("BestTimeID")
    val bestTimeID: String?,
    @Expose
    @SerializedName("BlastAgentID")
    val blastAgentID: String?,
    @Expose
    @SerializedName("BlastSalesID")
    val blastSalesID: String?,
    @Expose
    @SerializedName("Campaign")
    val campaign: String?,
    @Expose
    @SerializedName("Catatan")
    val catatan: String?,
    @Expose
    @SerializedName("CatatanSales")
    val catatanSales: String?,
    @Expose
    @SerializedName("ClosingAmount")
    val closingAmount: String?,
    @Expose
    @SerializedName("ClosingDate")
    val closingDate: String?,
    @Expose
    @SerializedName("EditBy")
    val editBy: String?,
    @Expose
    @SerializedName("EditDate")
    val editDate: String,
    @Expose
    @SerializedName("Email")
    val email: String?,
    @Expose
    @SerializedName("EmailProspect")
    val emailProspect: String?,
    @Expose
    @SerializedName("GenderID")
    val genderID: Int?,
    @Expose
    @SerializedName("HistoryProsID")
    val historyProsID: Int?,
    @Expose
    @SerializedName("Hot")
    val hot: Int?,
    @Expose
    @SerializedName("Hp")
    val hp: String?,
    @Expose
    @SerializedName("InputBy")
    val inputBy: String?,
    @Expose
    @SerializedName("KodeAds")
    val kodeAds: String?,
    @Expose
    @SerializedName("KodeAgent")
    val kodeAgent: String?,
    @Expose
    @SerializedName("KodeNegara")
    val kodeNegara: String?,
    @Expose
    @SerializedName("KodePT")
    val kodePT: String?,
    @Expose
    @SerializedName("KodePlatform")
    val kodePlatform: Int?,
    @Expose
    @SerializedName("KodeProject")
    val kodeProject: String?,
    @Expose
    @SerializedName("KodeSales")
    val kodeSales: String?,
    @Expose
    @SerializedName("LevelInputID")
    val levelInputID: String?,
    @Expose
    @SerializedName("Message")
    val message: String?,
    @Expose
    @SerializedName("MoveDate")
    val moveDate: String?,
    @Expose
    @SerializedName("MoveID")
    val moveID: String?,
    @Expose
    @SerializedName("NamaAgent")
    val namaAgent: String,
    @Expose
    @SerializedName("NamaPlatform")
    val namaPlatform: String,
    @Expose
    @SerializedName("NamaProspect")
    val namaProspect: String,
    @Expose
    @SerializedName("NamaSales")
    val namaSales: String,
    @Expose
    @SerializedName("NamaSumber")
    val namaSumber: String,
    @Expose
    @SerializedName("NotInterestedDate")
    val notInterestedDate: String?,
    @Expose
    @SerializedName("NotInterestedID")
    val notInterestedID: String?,
    @Expose
    @SerializedName("NoteSumberData")
    val noteSumberData: String?,
    @Expose
    @SerializedName("NumberMove")
    val numberMove: Int?,
    @Expose
    @SerializedName("PekerjaanID")
    val pekerjaanID: Int?,
    @Expose
    @SerializedName("PenghasilanID")
    val penghasilanID: Int?,
    @Expose
    @SerializedName("ProspectID")
    val prospectID: Int?,
    @Expose
    @SerializedName("Return")
    val returnX: Int?,
    @Expose
    @SerializedName("Status")
    val status: String?,
    @Expose
    @SerializedName("SumberDataID")
    val sumberDataID: Int?,
    @Expose
    @SerializedName("TempatKerjaID")
    val tempatKerjaID: Int?,
    @Expose
    @SerializedName("TempatTinggalID")
    val tempatTinggalID: Int?,
    @Expose
    @SerializedName("TertarikTipe")
    val tertarikTipe: String?,
    @Expose
    @SerializedName("TertarikTipeUnitID")
    val tertarikTipeUnitID: String?,
    @Expose
    @SerializedName("Unit")
    val unit: String?,
    @Expose
    @SerializedName("UnitID")
    val unitID: String?,
    @Expose
    @SerializedName("UpdatedAt")
    val updatedAt: String?,
    @Expose
    @SerializedName("UsiaID")
    val usiaID: String?,
    @Expose
    @SerializedName("VerifiedDate")
    val verifiedDate: String?,
    @Expose
    @SerializedName("VerifiedStatus")
    val verifiedStatus: Int?
) : Parcelable