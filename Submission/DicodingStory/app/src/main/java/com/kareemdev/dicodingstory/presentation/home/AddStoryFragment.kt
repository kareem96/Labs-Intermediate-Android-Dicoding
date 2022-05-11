package com.kareemdev.dicodingstory.presentation.home

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.kareemdev.dicodingstory.R
import com.kareemdev.dicodingstory.data.StateHandler
import com.kareemdev.dicodingstory.databinding.FragmentAddStoryBinding
import com.kareemdev.dicodingstory.domain.viewmodel.StoryViewModel
import com.kareemdev.dicodingstory.utils.CommonFunction
import com.kareemdev.dicodingstory.utils.createCustomTempFile
import com.kareemdev.dicodingstory.utils.reduceFileImage
import com.kareemdev.dicodingstory.utils.uriToFile
import kotlinx.coroutines.currentCoroutineContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File


/**
 * A simple [Fragment] subclass.
 * Use the [AddStoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddStoryFragment : Fragment(), View.OnClickListener {
    private var photo: File? = null
    private lateinit var binding: FragmentAddStoryBinding
    private var myLocation: Location? = null
    private val storyViewModel by sharedViewModel<StoryViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.closeOptionsMenu()
        binding = FragmentAddStoryBinding.inflate(layoutInflater)

        askPermission()
        binding.btnCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.btnGallery.setOnClickListener {
            openGallery()
        }
        binding.btnPost.setOnClickListener(this)
        storyViewModel.storyPostState.observe(viewLifecycleOwner) {
            when (it) {
                is StateHandler.Loading -> {
                    showLoading()
                }
                is StateHandler.Success -> {
                    hideLoading()
                    CommonFunction.showSnackBar(
                        binding.root,
                        requireContext(),
                        "Berhasil Upload",
                    )
                    storyViewModel.getStories("1")
                    findNavController().popBackStack()
                    storyViewModel.resetPostStory()
                }
                is StateHandler.Error -> {
                    hideLoading()
                    CommonFunction.showSnackBar(
                        binding.root,
                        requireContext(),
                        message = "Upload Gagal" + it.message + "!",
                        error = true
                    )
                }
                else -> {
                }
            }
        }
        return binding.root
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.btnPost.isEnabled = true
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnPost.isEnabled = false
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih gambar")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireContext()).also {
            photoURI = FileProvider.getUriForFile(
                requireContext(),
                getString(R.string.package_name),
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun askPermission() {
        if (!CommonFunction.allPermissionsGranted(requireContext())) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                CommonFunction.REQUIRED_PERMISSIONS,
                CommonFunction.REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private lateinit var photoURI: Uri
    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                photo = File(currentPhotoPath)
                photo?.let { photo ->
                    val result = BitmapFactory.decodeFile(photo.path)

                    binding.storyImageView.setImageBitmap(result)
                    binding.storyImageView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.rounded_outline)
                    binding.storyImageView.clipToOutline = true
                }


            } else {
                CommonFunction.showSnackBar(
                    binding.root,
                    requireContext(),
                    getString(R.string.failed_to_get_image),
                    true
                )
            }
        }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                binding.storyImageView.setImageURI(selectedImg)
                binding.storyImageView.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.rounded_outline)
                binding.storyImageView.clipToOutline = true
                photo = uriToFile(selectedImg, requireContext())
            } else {
                CommonFunction.showSnackBar(
                    binding.root,
                    requireContext(),
                    getString(R.string.failed_to_get_image),
                    true
                )
            }
        }

    override fun onClick(p0: View?) {
        if (photo == null) {
            CommonFunction.showSnackBar(
                binding.root,
                requireContext(),
                "Photo harus dipilih terdahulu",
                true,
            )
            return
        }
        photo?.let {
            try {
                reduceFileImage(it).let { reduced ->
                    if (binding.etDescription.text?.isEmpty() == true) {
                        binding.etDescription.error = "Description tidak boleh kosong"
                        return
                    }
                    storyViewModel.postStory(
                        binding.etDescription.text.toString(),
                        reduced,
                    )
                }
            } catch (e: Exception) {
                CommonFunction.showSnackBar(
                    binding.root,
                    requireContext(),
                    "File gagal di konversi",
                    true,
                )
            }
        }
    }


}