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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.solanacode.challangech6.R
import com.solanacode.challangech6.adapter.MovieAdapter
import com.solanacode.challangech6.databinding.FragmentHomeBinding
import com.solanacode.challangech6.model.MovieResponseItem
import com.solanacode.challangech6.viewmodel.AuthViewModel
import com.solanacode.challangech6.viewmodel.FavoriteViewModel
import com.solanacode.challangech6.viewmodel.MoviewViewModel
import com.solanacode.challangech6.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var moviewViewModel: MoviewViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel : AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        moviewViewModel = ViewModelProvider(requireActivity())[MoviewViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMovie()
        showLoading(true)
        binding.ivProfile.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.profileFragment)
        }
        getdataUser()
        user()
        binding.tvFavorite.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

    }

    fun showMovie(){
        movieAdapter = MovieAdapter(object :MovieAdapter.Clicked{
            override fun onClick(movieResponseItem: MovieResponseItem) {
                val i = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(movieResponseItem.id,movieResponseItem)
                findNavController().navigate(i)
            }

        })
        moviewViewModel.showfilmList()
        moviewViewModel.getLiveDataFilms().observe(requireActivity()){
            if(it != null){
                movieAdapter.submitData(it)
                showLoading(false)
            }else{
                Toast.makeText(requireActivity(), "Null Blok", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvMoview.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
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
                    tvPerson.text = it.name
                }
            }else{
                Log.d("profile","Profile kosong")
            }
        }
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading) binding.progressbar.visibility = View.VISIBLE else
            binding.progressbar.visibility = View.GONE
    }




}