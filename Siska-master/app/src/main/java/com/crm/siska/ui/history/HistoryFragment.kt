package com.crm.siska.ui.history

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crm.siska.R
import com.crm.siska.model.response.history.Data
import com.crm.siska.model.response.history.HistoryResponse
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment(), HistoryAdapter.ItemAdapterCallback, HistoryContract.View{

    val history : ArrayList<Data> = ArrayList()
    private lateinit var presenter: HistoryPresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initDataDummy()
        initView()
        presenter = HistoryPresenter(this)
        presenter.getHistory()
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

//    private fun initDataDummy() {
//        history.add(HistoryVerticalModel("Prospect : TEST - Agus Pratama","Hallo! Ada Prospect baru yang menghubungi kamu nih, Follow Up sekarang!","21 January 9:52"))
//        history.add(HistoryVerticalModel("Prospect : TEST - Rika Gusalamah","Hallo! Ada Prospect baru yang menghubungi kamu nih, Follow Up sekarang!","21 January 9:52"))
//        history.add(HistoryVerticalModel("Prospect : TEST - Vicky Gunawan","Hallo! Ada Prospect baru yang menghubungi kamu nih, Follow Up sekarang!","21 January 9:52"))
//    }

    override fun onHistorySuccess(historyResponse: HistoryResponse) {

//        history = arguments?.getParcelableArrayList("data")

        if(historyResponse.data.isEmpty()){
            empty_state.visibility = View.VISIBLE
        }

        var adapter = HistoryAdapter(historyResponse.data, this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter
    }

    override fun onHistoryFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View, data: Data) {
//        Toast.makeText(activity, data.subject, Toast.LENGTH_SHORT).show()
//        val detail = Intent(activity, DetailActivity::class.java)
//        detail.putExtra("data", data)
//        startActivity(detail)
    }
}