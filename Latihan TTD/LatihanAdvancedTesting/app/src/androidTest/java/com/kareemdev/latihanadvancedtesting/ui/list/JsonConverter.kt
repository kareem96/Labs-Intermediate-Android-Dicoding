package com.kareemdev.latihanadvancedtesting.ui.list

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

object JsonConverter {
    fun readStringFromFile(fileNAme: String): String{
        try {
            val applicationContext = ApplicationProvider.getApplicationContext<Context>()
            val inputStream = applicationContext.assets.open(fileNAme)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach{
                builder.append(it)
            }
            return builder.toString()
        }catch (e:IOException){
            throw e
        }
    }
}