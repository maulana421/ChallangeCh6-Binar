package com.solanacode.challangech6.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.solanacode.challangech6.R
import com.solanacode.challangech6.databinding.FragmentLoginBinding
import com.solanacode.challangech6.ds.UserPref
import com.solanacode.challangech6.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userPref: UserPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        userPref = UserPref(requireActivity())
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogin()
        toRegist()
    }

    private fun userLogin(){
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if(username.isNotBlank() && password.isNotBlank() ){
                authViewModel.toLogin(username,password)
                authViewModel.loginuser().observe(requireActivity()){
                    if(it != null){
                        authViewModel.setToken(it.token)
                        Toast.makeText(requireActivity(), "Login Succes", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                    }
                }
            }else{
                Toast.makeText(requireActivity(), "data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toRegist(){
        binding.tvRegister.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}