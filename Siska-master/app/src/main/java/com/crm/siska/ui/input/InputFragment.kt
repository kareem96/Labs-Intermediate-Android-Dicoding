package com.crm.siska.ui.input

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.crm.siska.R
import com.crm.siska.model.response.input.InputResponse
import com.crm.siska.model.response.input.ProspectResponse
import com.crm.siska.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_input.*

class InputFragment : Fragment(), InputContract.View, InputAdapter.ItemAdapterCallback {

    private lateinit var presenter: InputPresenter
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
    private var provinceIdDomisili : ArrayList<Int>      = ArrayList()
    private var provinceNameDomisili : ArrayList<String> = ArrayList()
    private var kotaIdDomisili : ArrayList<Int>          = ArrayList()
    private var kotaNameDomisili : ArrayList<String>     = ArrayList()
    private var provinceId : ArrayList<Int>              = ArrayList()
    private var provinceName : ArrayList<String>         = ArrayList()
    private var kotaId : ArrayList<Int>                  = ArrayList()
    private var kotaName : ArrayList<String>             = ArrayList()

    private var genderID : Int        = 0
    private var usiaID : Int          = 0
    private var pekerjaanID : Int     = 0
    private var penghasilanID : Int   = 0
    private var unitID : Int          = 0
    private var tempatTinggalID : Int = 0
    private var tempatKerjaID : Int   = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_input, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        presenter = InputPresenter(this)
        presenter.getData()


        rbFemale.setOnClickListener {
            genderID = 2
        }
        rbMale.setOnClickListener {
            genderID = 1
        }

        btnSubmit.setOnClickListener {

            if (etNamaProspect.text.toString().isNullOrEmpty()) {
                etNamaProspect.error = "Nama Prospect harus diisi"
                etNamaProspect.requestFocus()
            }else if (etHp.text.toString().isNullOrEmpty()){
                etHp.error = "Nomor Hp harus diisi"
                etHp.requestFocus()
            }else {
                presenter.inputData(
                    etNamaProspect.text.toString(),
                    etEmail.text.toString(),
                    etHp.text.toString(),
                    etMessage.text.toString(),
                    genderID,
                    usiaID,
                    pekerjaanID,
                    penghasilanID,
                    unitID,
                    tempatTinggalID,
                    tempatKerjaID, it )
                initViewSuccess()
            }
        }
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

    private fun initViewSuccess(){
        showLoading()
        Toast.makeText(activity,"sukses",Toast.LENGTH_LONG).show()
        etNamaProspect.text.clear()
        etNamaProspect.requestFocus()
        etEmail.text.clear()
        etHp.text.clear()
        etMessage.text.clear()
        rbMale.isChecked = false
        rbFemale.isChecked = false
//        rbGender.clearCheck()
        spUsia.setSelection(0)
        spPekerjaan.setSelection(0)
        spPenghasilan.setSelection(0)
        spUnitType.setSelection(0)
        spProvinceDomisili.setSelection(0)
        spCityDomisili.setSelection(0)
        spProvince.setSelection(0)
        spCity.setSelection(0)

        val text = "Prospect berhasil diinput"
        val duration : Int = Toast.LENGTH_LONG
        val toast : Toast = Toast.makeText(activity, text, duration)
        toast.show()

        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP, 0, 0)
    }


    override fun onDataSuccess(inputResponse: InputResponse) {

        usiaId.add(0)
        rangUsia.add("Pilih Usia")
        inputResponse.usia?.forEach {
            usiaId.add(it.usiaID)
            rangUsia.add(it.rangeUsia)
        }

        val usiaAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, rangUsia)
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

        pekerjaanId.add(0)
        tipePekerjaan.add("Pilih Pekerjaan")
        inputResponse.pekerjaan?.forEach {
            pekerjaanId.add(it.pekerjaanID)
            tipePekerjaan.add(it.tipePekerjaan)
        }

        val pekerjaanAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, tipePekerjaan)
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

        penghasilanId.add(0)
        rangePenghasilan.add("Pilih Penghasilan")
        inputResponse.penghasilan?.forEach {
            penghasilanId.add(it.penghasilanID)
            rangePenghasilan.add(it.rangePenghasilan)
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

        unitId.add(0)
        unitName.add("Pilih Unit Type")
        inputResponse.unit?.forEach {
            unitId.add(it.unitID)
            unitName.add(it.unitName)
        }

        val unitAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, unitName)
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

        provinceIdDomisili.add(0)
        provinceNameDomisili.add("Pilih Provinsi")
        inputResponse.provinsi?.forEach {
            provinceIdDomisili.add(it.id)
            provinceNameDomisili.add(it.province)
        }

        val provinceAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, provinceNameDomisili)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProvinceDomisili.adapter = provinceAdapter

        spProvinceDomisili.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                p0?.getItemAtPosition(p2)
                if (p0?.selectedItem == spProvinceDomisili.selectedItem){
                    showKota(provinceIdDomisili[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        provinceId.add(0)
        provinceName.add("Pilih Provinsi")
        inputResponse.provinsi?.forEach {
            provinceId.add(it.id)
            provinceName.add(it.province)
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

    }

    override fun onProspectSuccess(prospectResponse: ProspectResponse, view: View) {
//        Toast.makeText(activity,"hai", Toast.LENGTH_LONG).show()
//        val i = Intent(Intent.ACTION_VIEW)
//        startActivity(i)
    }

    override fun onProspectFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
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

    override fun onDataFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View, data: InputResponse) {
        TODO("Not yet implemented")
    }

}