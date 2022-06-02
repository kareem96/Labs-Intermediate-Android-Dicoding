package com.crm.siska.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.detail.DetailResponse

class DetailAdapter(
    private val listData: List<DetailResponse>,
    private val itemAdapterCallback: DetailFragment,
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_home, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }


    override fun getItemCount(): Int {
        return listData.size
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: DetailResponse, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
//                tvNamaProspect.text = data.data.namaProspect
//                tvHp.text = data.data.hp
//                tvEmail.text = data.data.emailProspect
//                tvMessage.text = data.data.message


                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick (v: View, data:DetailResponse)
    }
}
