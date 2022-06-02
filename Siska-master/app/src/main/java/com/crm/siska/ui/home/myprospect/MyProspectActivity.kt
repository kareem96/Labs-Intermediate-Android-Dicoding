package com.crm.siska.ui.home.myprospect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.crm.siska.R

class MyProspectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_prospect)

        intent.extras?.let {
            val navController = Navigation.findNavController(findViewById(R.id.myProspectHostFragment))
            navController.setGraph(navController.graph)
        }
    }
}