package com.crm.siska.ui.leads.my_leads.arsip

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.leads.Data
import kotlinx.android.synthetic.main.item_leads_vertical.view.*

class MyArsipAdapter(
    private val listData: List<Data>,
    private val itemAdapterCallback: MyArsipFragment,
) : RecyclerView.Adapter<MyArsipAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_leads_vertical, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }


    override fun getItemCount(): Int {
        return listData.size
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tvInputDate.text = data.addDate
                tvStatus.text = data.status
                tvNamaProspect.text = data.namaProspect
                tvSource.text = data.namaPlatform
                tvCampaign.text = data.campaign

                if (data.moveID != null && data.hot == 0){
                    ic_move.visibility = View.VISIBLE
                    ic_hot.visibility = View.GONE
                }else if (data.moveID != null && data.hot == 1){
                    ic_move.visibility = View.VISIBLE
                    ic_hot.visibility = View.VISIBLE
                }else if (data.moveID == null && data.hot == 0){
                    ic_move.visibility = View.GONE
                    ic_hot.visibility = View.GONE
                }else if (data.moveID == null && data.hot == 1){
                    ic_move.visibility = View.GONE
                    ic_hot.visibility = View.VISIBLE
                }

                if(data.hot == 1){
                    ic_hot.visibility = View.VISIBLE
                }
                if (data.status == "New"){
                    cardLeads.setBackgroundColor(Color.parseColor("#4795ee"))
                    tvStatus.setTextColor(Color.parseColor("#4795ee"))
                }
                if (data.status == "Process"){
                    cardLeads.setBackgroundColor(Color.parseColor("#d671c9"))
                    tvStatus.setTextColor(Color.parseColor("#d671c9"))
                }
                if (data.status == "Expired"){
                    cardLeads.setBackgroundColor(Color.parseColor("#e98c22"))
                    tvStatus.setTextColor(Color.parseColor("#e98c22"))
                }
                if (data.status == "Closing") {
                    cardLeads.setBackgroundColor(Color.parseColor("#77B341"))
                    tvStatus.setTextColor(Color.parseColor("#77B341"))
                }
                if (data.status == "Not Interested"){
                    cardLeads.setBackgroundColor(Color.parseColor("#EF5350"))
                    tvStatus.setTextColor(Color.parseColor("#EF5350"))
                }

                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick (v: View, data:Data)
    }
}
