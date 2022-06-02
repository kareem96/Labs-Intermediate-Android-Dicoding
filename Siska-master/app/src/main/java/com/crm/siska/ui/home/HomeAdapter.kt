package com.crm.siska.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.home.HomeResponse
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeAdapter(
    private val listData: List<HomeResponse>,
        private val itemAdapterCallback: HomeFragment,
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_home, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }


    override fun getItemCount(): Int {
        return listData.size
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: HomeResponse, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                textViewProspect.text = data.prospect.toString()


                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick (v: View, data:HomeResponse)
    }
}
