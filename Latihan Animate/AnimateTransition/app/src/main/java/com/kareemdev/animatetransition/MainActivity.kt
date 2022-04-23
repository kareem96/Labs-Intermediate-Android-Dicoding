package com.kareemdev.animatetransition

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.kareemdev.animatetransition.databinding.ActivityHeroBinding
import com.kareemdev.animatetransition.databinding.ActivityMainBinding
import com.kareemdev.animatetransition.model.Hero

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Hero>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHeroes.setHasFixedSize(true)
        list.addAll(listHero)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        if(application.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvHeroes.layoutManager = GridLayoutManager(this, 2)
        }else{
            binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        }
        val listAdapter = com.kareemdev.animatetransition.adapter.ListAdapter(list)
        binding.rvHeroes.adapter = listAdapter
    }

    private val listHero : ArrayList<Hero>
    get(){
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listHeroes = ArrayList<Hero>()
        for (i in dataName.indices){
            val hero = Hero(dataName[i], dataDescription[i], dataPhoto[i])
            listHeroes.add(hero)
        }
        return listHeroes
    }

}