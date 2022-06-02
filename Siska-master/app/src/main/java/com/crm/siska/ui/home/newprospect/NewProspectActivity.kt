package com.crm.siska.ui.home.newprospect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.crm.siska.R

class NewProspectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prospect)

        intent.extras?.let {
            val navController = Navigation.findNavController(findViewById(R.id.newProspectHostFragment))
            navController.setGraph(navController.graph)
        }
    }
}