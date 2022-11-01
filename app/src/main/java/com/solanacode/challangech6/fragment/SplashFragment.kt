package com.solanacode.challangech6.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.solanacode.challangech6.R
import com.solanacode.challangech6.databinding.FragmentSplashBinding
import com.solanacode.challangech6.ds.UserPref
import com.solanacode.challangech6.viewmodel.AuthViewModel
import com.solanacode.challangech6.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userPref: UserPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            isLogin()
        },1500)
    }

    private fun isLogin(){
        authViewModel.getToken().observe(requireActivity()){
            if(it != null){
                if(!it.equals("undefined")){
                    Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                }else{
                    Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                }
            }else{
                Toast.makeText(requireActivity(), "Token Null", Toast.LENGTH_SHORT).show()
            }
        }

    }


}