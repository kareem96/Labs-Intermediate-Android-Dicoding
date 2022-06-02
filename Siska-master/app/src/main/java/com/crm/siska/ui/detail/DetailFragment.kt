package com.crm.siska.ui.detail

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.crm.siska.R
import com.crm.siska.model.response.detail.DetailResponse
import com.crm.siska.model.response.input.ProspectResponse
import com.crm.siska.network.HttpClient
import com.crm.siska.ui.auth.AuthActivity
import com.crm.siska.utils.Helpers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.arrowBack
import kotlinx.android.synthetic.main.fragment_detail.rbFemale
import kotlinx.android.synthetic.main.fragment_detail.rbMale
import kotlinx.android.synthetic.main.fragment_detail.spCity
import kotlinx.android.synthetic.main.fragment_detail.spCityDomisili
import kotlinx.android.synthetic.main.fragment_detail.spPekerjaan
import kotlinx.android.synthetic.main.fragment_detail.spPenghasilan
import kotlinx.android.synthetic.main.fragment_detail.spProvince
import kotlinx.android.synthetic.main.fragment_detail.spProvinceDomisili
import kotlinx.android.synthetic.main.fragment_detail.spUnitType
import kotlinx.android.synthetic.main.fragment_detail.spUsia
import kotlinx.android.synthetic.main.fragment_detail.cardLeadsDetail
import kotlinx.android.synthetic.main.fragment_my_prospect.*


class DetailFragment : Fragment(), DetailContract.View, DetailAdapter.ItemAdapterCallback {

    var bundle:Bundle ?= null
    private lateinit var presenter: DetailPresenter
    var progressDialog : Dialog? = null

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }


    private var usiaId : ArrayList<Int>                  = ArrayList()
    private var rangUsia : ArrayList<String>             = ArrayList()
    private var pekerjaanId : ArrayList<Int>             = ArrayList()
    private var tipePekerjaan : ArrayList<String>        = ArrayList()
    private var penghasilanId : ArrayList<Int>           = ArrayList()
    private var rangePenghasilan : ArrayList<String>     = ArrayList()
    private var unitId : ArrayList<Int>                  = ArrayList()
    private var unitName : ArrayList<String>             = ArrayList()
    private var unitIdClosing : ArrayList<Int>           = ArrayList()
    private var unitNameClosing : ArrayList<String>      = ArrayList()
    private var provinceIdDomisili : ArrayList<Int>      = ArrayList()
    private var provinceNameDomisili : ArrayList<String> = ArrayList()
    private var kotaIdDomisili : ArrayList<Int>          = ArrayList()
    private var kotaNameDomisili : ArrayList<String>     = ArrayList()
    private var provinceId : ArrayList<Int>              = ArrayList()
    private var provinceName : ArrayList<String>         = ArrayList()
    private var kotaId : ArrayList<Int>                  = ArrayList()
    private var kotaName : ArrayList<String>             = ArrayList()
    private var notInterest : ArrayList<Int>          = ArrayList()
    private var reason : ArrayList<String>               = ArrayList()

    private var genderID : Int        = 0
    private var usiaID : Int          = 0
    private var pekerjaanID : Int     = 0
    private var penghasilanID : Int   = 0
    private var unitID : Int          = 0
    private var unitIDClosing : Int   = 0
    private var tempatTinggalID : Int = 0
    private var tempatKerjaID : Int   = 0
    private var notInterestID : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            DetailFragmentArgs.fromBundle(it).data.let {
                initView(it)
            }

        }

        arrowBack.setOnClickListener {
            this.activity?.finish()
        }

        fixed_layout.setOnClickListener {
            if (data_konsumen_hidden.visibility == View.VISIBLE){
                TransitionManager.beginDelayedTransition(base_cardview, AutoTransition());
                data_konsumen_hidden.visibility = View.GONE
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
            else {
                TransitionManager.beginDelayedTransition(base_cardview, AutoTransition());
                data_konsumen_hidden.visibility = View.VISIBLE
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

        fixed_layout2.setOnClickListener {
            if (closing_hidden.visibility == View.VISIBLE){
                TransitionManager.beginDelayedTransition(base_cardview2, AutoTransition());
                closing_hidden.visibility = View.GONE
                arrow_button.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
            else {
                TransitionManager.beginDelayedTransition(base_cardview2, AutoTransition());
                closing_hidden.visibility = View.VISIBLE
                arrow_button2.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

        fixed_layout3.setOnClickListener {
            if (not_interested_hidden.visibility == View.VISIBLE){
                TransitionManager.beginDelayedTransition(base_cardview3, AutoTransition());
                not_interested_hidden.visibility = View.GONE
                arrow_button3.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
            else {
                TransitionManager.beginDelayedTransition(base_cardview3, AutoTransition());
                not_interested_hidden.visibility = View.VISIBLE
                arrow_button3.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }

    }

    private fun initView(data: com.crm.siska.model.response.leads.Data?) {

        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }


        presenter = DetailPresenter(this)
        data?.prospectID?.let { presenter.getDetail(it) }

        hot_prospect.setOnClickListener {
            presenter = DetailPresenter(this)
            data?.prospectID?.let { presenter.hotleads(it) }

            Toast.makeText(activity, "Hot Prospect", Toast.LENGTH_LONG).show()
        }

        rbFemale.setOnClickListener {
            genderID = 2
        }
        rbMale.setOnClickListener {
            genderID = 1
        }

        btnWA.setOnClickListener {
            presenter.fuwa(data?.prospectID!!)

//            val packageManager : PackageManager
            val i = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=" + data?.kodeNegara.toString() + data?.hp.toString()
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            startActivity(i)

        }

        btnTelp.setOnClickListener {

            presenter.futelp(data?.prospectID!!)

            if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE), 101)
            }
            val i = Intent(Intent.ACTION_CALL)
            val url = "tel:" + data?.hp.toString()
            i.data = Uri.parse(url)
            startActivity(i)

        }

        btnUpdate.setOnClickListener {
            presenter.updateprospect(
                data?.prospectID!!,
                genderID,
                usiaID,
                pekerjaanID,
                penghasilanID,
                unitID,
                tempatTinggalID,
                tempatKerjaID,
                etMessage.text.toString(),it)

//            val detail = Intent(activity, DetailActivity::class.java)
//            detail.putExtra("data", data)
//            startActivity(detail)

            initViewSuccess()
        }

        btnClosing.setOnClickListener {
            presenter.closing(data?.prospectID!!, unitIDClosing, etKetUnit.text.toString(), etHargaJual.text.toString().toInt())
            val detail = Intent(activity, DetailActivity::class.java)
            detail.putExtra("data", data)
            startActivity(detail)
            initViewSuccess()
        }

        btnNotInterested.setOnClickListener {
            presenter.notInterested(data?.prospectID!!, notInterestID)
//            val detail = Intent(activity, DetailActivity::class.java)
//            detail.putExtra("data", data)
//            startActivity(detail)
//            initViewSuccess()
            Helpers.refreshFragment(context)
        }
    }

    private fun initViewSuccess() {
//        intent.extras?.let {
//            val navController = Navigation.findNavController(findViewById(R.id.detailHostFragment))
//            val bundle = Bundle()
//            bundle.putParcelable("data", it.get("data") as Parcelable?)
//            navController.setGraph(navController.graph, bundle)
//        }
        showLoading()
        val text = "Data berhasil diubah"
        val duration : Int = Toast.LENGTH_LONG
        val toast : Toast = Toast.makeText(activity, text, duration)
        toast.show()

        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, 0)
    }

    override fun onDetailSuccess(detailResponse: DetailResponse) {
        tvNamaProspect.text = detailResponse.namaProspect
        tvSource.text = detailResponse.namaPlatform
        tvCampaign.text = detailResponse.campaign
        tvMessage.setText(detailResponse.message)
        tvFu.text = detailResponse.fu.toString()
        tvFuDate.text = detailResponse.fuDate
        tvStatus.text = detailResponse.status
        etKetUnit.setText(detailResponse.Unit)
//        etHargaJual.setText(detailResponse.closingAmount)

        hot_prospect.isChecked = detailResponse.hot != 0 || detailResponse.hot == null

        if(detailResponse.status == "New"){
            cardLeadsDetail.setBackgroundColor(Color.parseColor("#4795ee"))
            tvStatus.setTextColor(Color.parseColor("#4795ee"))
        }
        if (detailResponse.status == "Process"){
            cardLeadsDetail.setBackgroundColor(Color.parseColor("#d671c9"))
            tvStatus.setTextColor(Color.parseColor("#d671c9"))
        }
        if(detailResponse.status == "Expired"){
            cardLeadsDetail.setBackgroundColor(Color.parseColor("#e98c22"))
            tvStatus.setTextColor(Color.parseColor("#e98c22"))
        }
        if(detailResponse.status == "Closing"){
            cardLeadsDetail.setBackgroundColor(Color.parseColor("#77B341"))
            tvStatus.setTextColor(Color.parseColor("#77B341"))
        }
        if (detailResponse.status == "Not Interested"){
            cardLeadsDetail.setBackgroundColor(Color.parseColor("#EF5350"))
            tvStatus.setTextColor(Color.parseColor("#EF5350"))
        }

        if (detailResponse.genderID == 1) {
            rbMale.isChecked = true
            genderID = 1
        } else if (detailResponse.genderID == 2) {
            rbFemale.isChecked = true
            genderID = 2
        }else{
            genderID = 0
        }

        if (detailResponse.usiaID == null || detailResponse.usiaID == 0) {
            usiaId.add(0)
            rangUsia.add("Pilih Usia")
            detailResponse.usia?.forEach {
                usiaId.add(it.usiaID)
                rangUsia.add(it.rangeUsia)
            }
        } else {
            usiaId.add(detailResponse.usiaID)
            rangUsia.add(detailResponse.rangeUsia)
            detailResponse.usia?.forEach {
                usiaId.add(it.usiaID)
                rangUsia.add(it.rangeUsia)
            }
        }


        val usiaAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, rangUsia)
        usiaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUsia.adapter = usiaAdapter

        spUsia.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                usiaID = usiaId[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        if (detailResponse.pekerjaanID == null || detailResponse.pekerjaanID == 0) {
            pekerjaanId.add(0)
            tipePekerjaan.add("Pilih Pekerjaan")
            detailResponse.pekerjaan?.forEach {
                pekerjaanId.add(it.pekerjaanID)
                tipePekerjaan.add(it.tipePekerjaan)
            }
        } else {
            pekerjaanId.add(detailResponse.pekerjaanID)
            tipePekerjaan.add(detailResponse.tipePekerjaan)
            detailResponse.pekerjaan?.forEach {
                pekerjaanId.add(it.pekerjaanID)
                tipePekerjaan.add(it.tipePekerjaan)
            }
        }

        val pekerjaanAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, tipePekerjaan)
        pekerjaanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPekerjaan.adapter = pekerjaanAdapter

        spPekerjaan.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                pekerjaanID = pekerjaanId[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        if (detailResponse.penghasilanID == null || detailResponse.penghasilanID == 0) {
            penghasilanId.add(0)
            rangePenghasilan.add("Pilih Penghasilan")
            detailResponse.penghasilan?.forEach {
                penghasilanId.add(it.penghasilanID)
                rangePenghasilan.add(it.rangePenghasilan)
            }
        } else {
            penghasilanId.add(detailResponse.penghasilanID)
            rangePenghasilan.add(detailResponse.rangePenghasilan)
            detailResponse.penghasilan?.forEach {
                penghasilanId.add(it.penghasilanID)
                rangePenghasilan.add(it.rangePenghasilan)
            }
        }

        val penghasilanAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, rangePenghasilan)
        penghasilanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPenghasilan.adapter = penghasilanAdapter

        spPenghasilan.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                penghasilanID = penghasilanId[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        if (detailResponse.unitID == null || detailResponse.unitID == 0) {
            unitId.add(0)
            unitName.add("Pilih Unit Type")
            detailResponse.unit?.forEach {
                unitId.add(it.unitID)
                unitName.add(it.unitName)
            }
        } else {
            unitId.add(detailResponse.unitID)
            unitName.add(detailResponse.unitName)
            detailResponse.unit?.forEach {
                unitId.add(it.unitID)
                unitName.add(it.unitName)
            }
        }


        val unitAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, unitName)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUnitType.adapter = unitAdapter

        spUnitType.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                unitID = unitId[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        if (detailResponse.tempatTinggalID == null || detailResponse.tempatTinggalID == 0) {
            provinceIdDomisili.add(0)
            provinceNameDomisili.add("Pilih Provinsi")
            detailResponse.provinsi?.forEach {
                provinceIdDomisili.add(it.id)
                provinceNameDomisili.add(it.province)
            }
        } else {
            provinceIdDomisili.add(detailResponse.provinsiDomisiliID)
            provinceNameDomisili.add(detailResponse.provinsiDomisiliName)
            detailResponse.provinsi?.forEach {
                provinceIdDomisili.add(it.id)
                provinceNameDomisili.add(it.province)
            }
        }


        val provinceAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            provinceNameDomisili
        )
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProvinceDomisili.adapter = provinceAdapter

        spProvinceDomisili.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                p0?.getItemAtPosition(p2)
                if (p0?.selectedItem == spProvinceDomisili.selectedItem) {
                    showKota(provinceIdDomisili[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        if(detailResponse.tempatKerjaID == null || detailResponse.tempatKerjaID == 0){
            provinceId.add(0)
            provinceName.add("Pilih Provinsi")
            detailResponse.provinsi?.forEach {
                provinceId.add(it.id)
                provinceName.add(it.province)
            }
        }else{
            provinceId.add(detailResponse.provinsiID)
            provinceName.add(detailResponse.provinsiName)
            detailResponse.provinsi?.forEach {
                provinceId.add(it.id)
                provinceName.add(it.province)
            }
        }


        val province2Adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, provinceName)
        province2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProvince.adapter = province2Adapter

        spProvince.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                p0?.getItemAtPosition(p2)
                if (p0?.selectedItem == spProvince.selectedItem){
                    getKota(provinceId[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        if(detailResponse.closingAmount == null || detailResponse.closingAmount == 0){
            unitIdClosing.add(0)
            unitNameClosing.add("Pilih Unit Type")
            detailResponse.unit?.forEach {
                unitIdClosing.add(it.unitID)
                unitNameClosing.add(it.unitName)
            }
        }else{
            unitIdClosing.add(detailResponse.unitID)
            unitNameClosing.add(detailResponse.unitName)
            detailResponse.unit?.forEach {
                unitIdClosing.add(it.unitID)
                unitNameClosing.add(it.unitName)
            }
        }


        val UnitClosingAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, unitNameClosing)
        UnitClosingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUnitTypeClosing.adapter = UnitClosingAdapter

        spUnitTypeClosing.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                unitIDClosing = unitIdClosing[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        if(detailResponse.notInterestedID == null || detailResponse.notInterestedID == 0){
            notInterest.add(0)
            reason.add("Choose Reason")
            detailResponse.notInterested?.forEach {
                notInterest.add(it.notInterestedID)
                reason.add(it.alasan)
            }
        }else{
            notInterest.add(detailResponse.notInterestedID)
            reason.add(detailResponse.notInterestedID.toString())
            detailResponse.notInterested?.forEach {
                unitIdClosing.add(it.notInterestedID)
                unitNameClosing.add(it.alasan)
            }
        }


        val ReasonAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, reason)
        ReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spReason.adapter = ReasonAdapter

        spReason.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                notInterestID = notInterest[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun showKota(province_id: Int) {
        val disposable = HttpClient.getInstance().getApi()!!.getKota(province_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    kotaIdDomisili.clear()
                    kotaNameDomisili.clear()
                    it.data?.let { it1 ->
                        kotaIdDomisili.add(0)
                        kotaNameDomisili.add("Pilih Kota")
                        it1.kota.forEach {
                            kotaIdDomisili.add(it.id)
                            kotaNameDomisili.add(it.city)
                        }
                    }

                    val cityAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, kotaNameDomisili)
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spCityDomisili.adapter = cityAdapter

                    spCityDomisili.onItemSelectedListener = object :

                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            tempatTinggalID = kotaIdDomisili[p2]
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                },
                {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            )

        mCompositeDisposable!!.add(disposable)
    }

    private fun getKota(province_id: Int) {
        val disposable = HttpClient.getInstance().getApi()!!.getKota(province_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    kotaId.clear()
                    kotaName.clear()
                    it.data?.let { it1 ->
                        kotaId.add(0)
                        kotaName.add("Pilih Kota")
                        it1.kota.forEach {
                            kotaId.add(it.id)
                            kotaName.add(it.city)
                        }
                    }

                    val cityAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, kotaName)
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spCity.adapter = cityAdapter

                    spCity.onItemSelectedListener = object :

                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            tempatKerjaID = kotaId[p2]
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                },
                {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            )

        mCompositeDisposable!!.add(disposable)
    }


    override fun onDetailFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess(prospectResponse: ProspectResponse, view: View) {
        Toast.makeText(activity, "activity",Toast.LENGTH_LONG).show()
//        showLoading()
//        arguments?.let {
//            DetailFragmentArgs.fromBundle(it).data.let {
//                initView(it)
//            }
//
//        }

//        val text = "Data berhasil diubah"
//        val duration : Int = Toast.LENGTH_LONG
//        val toast : Toast = Toast.makeText(activity, text, duration)
//        toast.show()
//
//        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, 0)
    }

    override fun onUpdateFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View, data: DetailResponse) {
        TODO("Not yet implemented")
    }


}