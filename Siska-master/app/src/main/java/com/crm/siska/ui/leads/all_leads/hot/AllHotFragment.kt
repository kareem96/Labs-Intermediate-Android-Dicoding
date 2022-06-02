package com.crm.siska.ui.leads.all_leads.hot

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.leads.Data
import com.crm.siska.model.response.leads.LeadsResponse
import com.crm.siska.ui.detail.DetailActivity
import com.crm.siska.ui.leads.LeadsContract
import kotlinx.android.synthetic.main.fragment_all_aktif.empty_state
import kotlinx.android.synthetic.main.fragment_all_aktif.rcList

class AllHotFragment : Fragment(), AllHotAdapter.ItemAdapterCallback, LeadsContract.View {

    private var leads : ArrayList<Data>? = ArrayList()
    private lateinit var presenter: AllHotPresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_hot, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        presenter = AllHotPresenter(this)
        presenter.getLeads()
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onLeadsSuccess(leadsResponse: LeadsResponse) {
        leads = arguments?.getParcelableArrayList("data")

        if(leadsResponse.data.isEmpty()){
            empty_state.visibility = View.VISIBLE
        }

        var adapter = AllHotAdapter(leadsResponse.data, this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter
    }

    override fun onLeadsFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View, data: Data) {
//        Toast.makeText(activity, data.namaProspect, Toast.LENGTH_SHORT).show()
        val detail = Intent(activity, DetailActivity::class.java)
        detail.putExtra("data", data)
        startActivity(detail)
    }
}