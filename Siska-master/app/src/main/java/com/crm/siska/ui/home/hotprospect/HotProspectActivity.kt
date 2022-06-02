package com.crm.siska.ui.home.hotprospect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.crm.siska.R

class HotProspectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hot_prospect)

        intent.extras?.let {
            val navController = Navigation.findNavController(findViewById(R.id.hotProspectHostFragment))
            navController.setGraph(navController.graph)
        }
    }
}