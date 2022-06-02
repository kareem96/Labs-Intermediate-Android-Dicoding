package com.crm.siska

import android.content.Context

class FcmSharedPref(context: Context) {
    companion object{
        const val token = ""
//        const val channelId = ""
    }

    val preferences = context.getSharedPreferences(token, Context.MODE_PRIVATE)
//    val prefChannelId = context.getSharedPreferences(channelId, Context.MODE_PRIVATE)

    fun setTokenFcm(fcm : String){
        val prefEditor = preferences.edit()
        prefEditor.putString(token, fcm)
        prefEditor.apply()
    }

    fun getTokenFcm(): String? {
        return preferences.getString(token, "")
    }

//    fun setDeviceId(deviceId : String){
//        val prefEditor = prefChannelId.edit()
//        prefEditor.putString(channelId, deviceId)
//        prefEditor.apply()
//    }
//
//    fun getDeviceId() :String{
//        return prefChannelId.getString(channelId, "")!!
//    }
}