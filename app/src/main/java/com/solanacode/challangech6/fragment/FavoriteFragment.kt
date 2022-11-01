package com.solanacode.challangech6.fragment

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.solanacode.challangech6.R
import com.solanacode.challangech6.adapter.FavoriteMovieAdapter
import com.solanacode.challangech6.databinding.FragmentFavoriteBinding
import com.solanacode.challangech6.viewmodel.FavoriteViewModel


class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteMovieAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieFav()
        setRecyclerMovie()
    }

    private fun setMovieFav(){
        favoriteViewModel.getlistmovieFavorite()
        favoriteViewModel.getMovieFav().observe(requireActivity()){
            if(it != null){
                favoriteAdapter.submitData(it)
            }
        }
    }

    private fun setRecyclerMovie(){
        favoriteAdapter = FavoriteMovieAdapter()
        binding.apply {
            rvFav.apply {
                adapter = favoriteAdapter
                layoutManager = if(context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    GridLayoutManager(requireActivity(),2)
                }else{
                    LinearLayoutManager(requireActivity())
                }
            }

        }
    }


}