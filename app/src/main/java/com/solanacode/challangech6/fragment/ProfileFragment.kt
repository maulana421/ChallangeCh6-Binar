package com.solanacode.challangech6.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.solanacode.challangech6.R
import com.solanacode.challangech6.databinding.FragmentProfileBinding
import com.solanacode.challangech6.ds.UserPref
import com.solanacode.challangech6.model.UserUpdateLatest
import com.solanacode.challangech6.viewmodel.AuthViewModel
import com.solanacode.challangech6.viewmodel.BlurViewModel
import com.solanacode.challangech6.viewmodel.UserViewModel
import com.solanacode.challangech6.worker.IMAGE_BLURRED
import com.solanacode.challangech6.worker.OUTPUT_PATH
import com.solanacode.challangech6.worker.PROFILE_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userPref: UserPref
    private val blur by lazy{
        activity?.application?.let { BlurViewModel(it) }
    }
    private var uri : Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdataUser()
        user()
        binding.tvLogout.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
        }
        openGallery()
        setImageBlured()
    }

    private fun getdataUser(){
        authViewModel.getToken().observe(requireActivity()){
            if(it != null){
                userViewModel.getDataUser("Bearer $it")
            }else{
                Log.d("TOKEN","Token Null")
            }
        }

    }

    private fun user(){
        userViewModel.getUser().observe(requireActivity()){
            if(it != null){
                binding.apply {
                    tvusernameProfile.text = it.email
                    tvnameProfile.text = it.name

                }
                updateProfile(it.name,it.email,it.password)
            }else{
                Log.d("profile","Profile kosong")
            }
        }
    }
    private fun updateProfile(name:String,email :String,password :String){
        binding.btnupdateProfile.setOnClickListener {
            val editProfile = UserUpdateLatest(name,email,password)
            val args = ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(editProfile)
            findNavController().navigate(args)
        }
    }

    private val useGallery = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
            uri = it
            Toast.makeText(requireActivity(), "$it", Toast.LENGTH_LONG).show()
            binding.profileUser.setImageURI(it)
            blur?.setImageUri(it)
            if(uri != null){
                saveImage()
            }
        }else{
            Toast.makeText(requireActivity(), "Null sayang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery(){
        binding.btnGalery.setOnClickListener {
            activity?.intent?.type = "image/*"
            useGallery.launch("image/*")
        }
    }

    private fun setImageBlured(){
        val image = BitmapFactory.decodeFile(requireActivity().applicationContext.filesDir.path + File.separator + OUTPUT_PATH+ File.separator + IMAGE_BLURRED)
        binding.profileUser.setImageBitmap(image)
    }

    private fun saveImage(){
        val resolver = requireActivity().applicationContext.contentResolver
        val picture = BitmapFactory.decodeStream(
            resolver.openInputStream(Uri.parse(uri.toString())))
        saveImageProfile(requireContext(), picture)
        blur?.applyBlur()
    }


    private fun saveImageProfile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "profile_image.png"
        val outputDir = File(applicationContext.filesDir, PROFILE_IMAGE)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
    }



}