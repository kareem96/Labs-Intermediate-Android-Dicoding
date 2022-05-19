package com.kareemdev.mystudentdata.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kareemdev.mystudentdata.database.Student
import com.kareemdev.mystudentdata.databinding.ItemStudentBinding

class StudentListAdapter : PagedListAdapter<Student, StudentListAdapter.WordViewHolder>(WordsComparator()){

    class WordsComparator: DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.name == newItem.name
        }

    }

    class WordViewHolder(private val binding: ItemStudentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Student){
            binding.tvItemName.text = data.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}