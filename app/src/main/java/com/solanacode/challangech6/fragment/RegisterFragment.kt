package com.solanacode.challangech6.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challangech6.R
import com.solanacode.challangech6.databinding.FragmentRegisterBinding
import com.solanacode.challangech6.viewmodel.AuthViewModel
import com.solanacode.challangech6.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRegist()
        toLogin()
    }

    private fun userRegist(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etnamaReg.text.toString().trim()
            val username = binding.etusernmaeReg.text.toString().trim()
            val password = binding.etpasswordReg.text.toString().trim()

            if (name.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                if(password.length >= 6){
                    authViewModel.userRegist(name, username, password)
                    authViewModel.registuser().observe(requireActivity()) {
                        if (it != null) {
                            Toast.makeText(requireActivity(), "registrasi berhasil", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                        }else{
                            Toast.makeText(requireActivity(), "Null Sayang", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(requireActivity(), "password harus 6 karakter", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireActivity(), "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toLogin(){
        binding.tvLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}