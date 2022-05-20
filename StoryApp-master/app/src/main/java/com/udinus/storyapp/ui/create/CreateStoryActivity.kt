package com.udinus.storyapp.ui.create

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.exifinterface.media.ExifInterface
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.google.android.material.snackbar.Snackbar
import com.udinus.storyapp.R
import com.udinus.storyapp.databinding.ActivityCreateStoryBinding
import com.udinus.storyapp.ui.home.HomeActivity
import com.udinus.storyapp.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import com.udinus.storyapp.utils.MediaUtils
import com.udinus.storyapp.utils.animateVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@AndroidEntryPoint
@ExperimentalPagingApi
class CreateStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStoryBinding
    private lateinit var photoPath: String
    private var getFile: File? = null
    private var location: Location? = null
    private var token: String = ""
    private val createViewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        createViewModel.viewModelScope.launch {
            createViewModel.getToken().collect {
                token = it!!
            }
        }
        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnPost.setOnClickListener {
            uploadStory()
        }

    }

    private fun uploadStory() {
        stateLoading(true)
        val edDescription = binding.etDescription
        var isValid = true

        if (edDescription.text.toString().isBlank()) {
            edDescription.error = "Please this field cannot empty"
            isValid = false
        }
        if (getFile == null) {
            showSnackbar("Please select an image")
            isValid = false
        }

        if (isValid){
            lifecycleScope.launchWhenCreated {
                launch {
                    val description = edDescription.text.toString().toRequestBody("text/plain".toMediaType())
                    val file = MediaUtils.reduceFileImage(getFile as File)
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        requestImageFile
                    )

                    var lat: RequestBody? = null
                    var lon: RequestBody? = null

                    if (location != null) {
                        lat = location?.latitude.toString().toRequestBody("text/plain".toMediaType())
                        lon = location?.longitude.toString().toRequestBody("text/plain".toMediaType())
                    }
                    createViewModel.viewModelScope.launch {
                        createViewModel.uploadImage(token, imageMultipart, description, lat, lon).collect {response ->
                            response.onSuccess {
                                Intent(this@CreateStoryActivity, HomeActivity::class.java).also {
                                    it.apply {
                                        putExtra(EXTRA_TOKEN, token)
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    }
                                    startActivity(it)
                                    finish()
                                }
                                Toast.makeText(
                                    this@CreateStoryActivity,
                                    getString(R.string.story_upload),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }

                            response.onFailure {
                                stateLoading(false)
                                showSnackbar(getString(R.string.image_upload_failed))
                            }
                        }
                    }
                }
            }
        }else stateLoading(false)

    }

    private val launchGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                MediaUtils.uriToFile(selectedImg, this).also { getFile = it }
                binding.imageView.setImageURI(selectedImg)
            }
        }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, " choose a picture")
        launchGallery.launch(chooser)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        MediaUtils.createTempFile(application).also {
            val photoUri = FileProvider.getUriForFile(
                this,
                "com.udinus.storyapp",
                it
            )
            photoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launchCamera.launch(intent)
        }
    }

    private val launchCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val file = File(photoPath).also { getFile = it }
                val os: OutputStream
                val bitmap = BitmapFactory.decodeFile(getFile?.path)
                val exif = ExifInterface(photoPath)
                val orientation: Int = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )

                val rotatedBitmap: Bitmap = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(
                        bitmap,
                        90
                    )
                    ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(
                        bitmap,
                        180
                    )
                    ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(
                        bitmap,
                        270
                    )
                    ExifInterface.ORIENTATION_NORMAL -> bitmap
                    else -> bitmap
                }

                try {
                    os = FileOutputStream(file)
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                    os.flush()
                    os.close()
                    getFile = file
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                binding.imageView.setImageBitmap(rotatedBitmap)
            }
        }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun stateLoading(b: Boolean) {
        binding.apply {
            btnCamera.isEnabled = !b
            btnGallery.isEnabled = !b
            etDescription.isEnabled = !b

            viewLoading.animateVisibility(b)
        }
    }
}