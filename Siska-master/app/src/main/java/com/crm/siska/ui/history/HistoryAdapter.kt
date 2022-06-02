package com.crm.siska.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.history.Data
import kotlinx.android.synthetic.main.item_history_vertical.view.*

class HistoryAdapter(
    private val listData: List<Data>,
    private val itemAdapterCallback: HistoryFragment,
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_history_vertical, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }


    override fun getItemCount(): Int {
        return listData.size
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                val day = data.day.toString()
                val month = data.month
                val hour = data.hour.toString()
                val minute = data.minute.toString()
                subject.text = data.subject
                date.text = day +" "+ month +" "+ hour+":"+ minute
                notes.text = data.notes

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
