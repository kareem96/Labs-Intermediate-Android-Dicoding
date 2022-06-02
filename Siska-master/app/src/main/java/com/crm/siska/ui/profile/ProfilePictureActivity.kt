package com.crm.siska.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.Navigation
import com.crm.siska.R

class ProfilePictureActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture)

//        intent.extras?.let {
//            val navController = Navigation.findNavController(findViewById(R.id.ppHostFragment))
//            val bundle = Bundle()
//            bundle.putParcelable("data", it.get("data") as Parcelable?)
//            navController.setGraph(navController.graph, bundle)
//        }

    }


}