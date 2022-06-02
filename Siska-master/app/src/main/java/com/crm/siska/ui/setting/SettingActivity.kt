package com.crm.siska.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.crm.siska.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        intent.extras?.let {
            val navController = Navigation.findNavController(findViewById(R.id.settingHostFragment))
            navController.setGraph(navController.graph)
        }

//        val navController = Navigation.findNavController(findViewById(R.id.settingHostFragment))
//        NavigationUI.setupWithNavController(navView, navController)
    }
}