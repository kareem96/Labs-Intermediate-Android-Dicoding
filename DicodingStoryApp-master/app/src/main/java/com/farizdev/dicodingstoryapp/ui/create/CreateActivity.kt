package com.farizdev.dicodingstoryapp.ui.create

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.farizdev.dicodingstoryapp.R
import com.farizdev.dicodingstoryapp.ViewModelFactory
import com.farizdev.dicodingstoryapp.data.local.UserPreferences
import com.farizdev.dicodingstoryapp.data.model.UploadResponse
import com.farizdev.dicodingstoryapp.data.remote.ApiConfig
import com.farizdev.dicodingstoryapp.databinding.ActivityCreateBinding
import com.farizdev.dicodingstoryapp.ui.CameraActivity
import com.farizdev.dicodingstoryapp.utils.reduceFileImage
import com.farizdev.dicodingstoryapp.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class CreateActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var viewModel: CreateViewModel
    private var getFile: File? = null

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.title = resources.getString(R.string.new_story)

        if(!allPermissionsGranted()){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        viewModelFactory()

        viewModel.getUser().observe(this){
            viewModel.token = it.token
        }
        viewModel.loading.observe(this){
            stateLoading(it)
        }
        binding.btnCamera.setOnClickListener(this)
        binding.btnGallery.setOnClickListener(this)
        binding.btnUpload.setOnClickListener(this)
    }

    private fun stateLoading(b: Boolean) {
        with(binding) {
            if (b) {
                pbLoading.visibility = View.VISIBLE
                bgLoading.visibility = View.VISIBLE
            } else {
                pbLoading.visibility = View.INVISIBLE
                bgLoading.visibility = View.INVISIBLE
            }
        }
    }

    private fun viewModelFactory() {
        viewModel = ViewModelProvider(this@CreateActivity, ViewModelFactory.getInstance(
            UserPreferences.getInstance(dataStore)))[CreateViewModel::class.java]
    }


    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_camera -> {
                val intent = Intent(this, CameraActivity::class.java)
                launcherCamera.launch(intent)
            }
            R.id.btn_gallery -> {
                val intent = Intent().also {
                    it.action = Intent.ACTION_GET_CONTENT
                    it.type = "image/*"
                }
                val chooser = Intent.createChooser(intent, "choose image")
                launcherGallery.launch(chooser)
            }
            R.id.btn_upload -> {
                uploadStory()
            }

        }
    }

    private fun uploadStory() {
        if(getFile != null && binding.etDescription.text.toString() != ""){
            viewModel.loading.value = true
            val file = reduceFileImage(getFile as File)
            val description = binding.etDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestImageFile)

            val service = ApiConfig.getApiService().uploadImage("Bearer ${viewModel.token}", imageMultipart, description)
            service.enqueue(object: Callback<UploadResponse>{
                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {
                    viewModel.loading.value = false
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            if(!responseBody.error){
                                showNotif(true, responseBody.message)
                            }else{
                                showNotif(false, " Camera failed with error" + response.message())
                            }
                        }
                    }else{
                        showNotif(false, " Camera failed with error" + response.message())
                    }
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    viewModel.loading.value = false
                    showNotif(false, " Camera failed with error" + t.message)
                }
            })
        }else{
            showNotif(false, "Please insert the image and description")
        }
    }

    private val  launcherCamera= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == CAMERA_X_RESULT){
            val myFile = it.data?.getSerializableExtra("picture") as File
            val result = BitmapFactory.decodeFile(myFile.path)
            getFile = myFile
            binding.ivNewStory.setImageBitmap(result)
        }
    }

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val selectedImg : Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@CreateActivity)
            getFile = myFile
            binding.ivNewStory.setImageURI(selectedImg)
        }
    }

    private fun showNotif(success: Boolean, message: String) {
        val title = if (success) R.string.camera_success_title else R.string.camera_failed_title

        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(R.string.ok) { _, _ ->
                if (success) {
                    finish()
                }
            }
            create()
            show()
        }
    }

}