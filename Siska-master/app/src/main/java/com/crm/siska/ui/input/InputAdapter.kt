package com.crm.siska.ui.input

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.input.InputResponse

class InputAdapter(
    private val listData: List<InputResponse>,
    private val itemAdapterCallback: InputFragment,
) : RecyclerView.Adapter<InputAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_home, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InputAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }


    override fun getItemCount(): Int {
        return listData.size
    }


    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: InputResponse, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
//                textViewProspect.text = data.prospect.toString()

                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it, data)
                }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick (v: View, data:InputResponse)
    }
}
