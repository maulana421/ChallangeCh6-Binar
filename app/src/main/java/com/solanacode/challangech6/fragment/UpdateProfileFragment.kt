package com.solanacode.challangech6.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.solanacode.challangech6.R
import com.solanacode.challangech6.databinding.FragmentUpdateProfileBinding
import com.solanacode.challangech6.ds.UserPref
import com.solanacode.challangech6.viewmodel.AuthViewModel
import com.solanacode.challangech6.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private lateinit var binding : FragmentUpdateProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userPref: UserPref
    private val args by navArgs<UpdateProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataUser()
        updateUser()
    }

    private fun setDataUser(){
        binding.apply {
            val(username,name,password) = args.userUpdate
            etusernmaeUpdate.setText(username)
            etnamaUpdate.setText(name)
            etpasswordUpdate.setText(password)
        }
    }
    private fun updateUser(){
        binding.btnupdateProfile.setOnClickListener {
            val name = binding.etusernmaeUpdate.text.toString().trim()
            val email = binding.etnamaUpdate.text.toString().trim()
            val password = binding.etpasswordUpdate.text.toString().trim()

            authViewModel.getToken().observe(requireActivity()){ token ->
                if(token != null){
                    if(name.isNotBlank() && email.isNotBlank() && password.isNotBlank()){
                        if(password.length >= 6){
                            userViewModel.updatedataUser("Bearer $token",name,email,password)
                            userViewModel.updateUser().observe(requireActivity()){
                                if(it != null){
                                    Toast.makeText(requireActivity(), "update profile berhasil", Toast.LENGTH_SHORT).show()
                                    Navigation.findNavController(binding.root).navigate(R.id.profileFragment)
                                }else{
                                    Toast.makeText(requireActivity(), "update profile gagal", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else{
                            Toast.makeText(requireActivity(), "password harus 6 karakter", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireActivity(), "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Log.d("TOKEN","Token Null")
                }
            }
        }
    }


}