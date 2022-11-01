package com.solanacode.challangech6.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.solanacode.challangech6.R
import com.solanacode.challangech6.databinding.FragmentDetailMovieBinding
import com.solanacode.challangech6.model.MovieFav
import com.solanacode.challangech6.viewmodel.FavoriteViewModel
import com.solanacode.challangech6.viewmodel.MoviewViewModel
import com.solanacode.challangech6.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var binding : FragmentDetailMovieBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var moviewViewModel: MoviewViewModel
    private var Bookmarked = false
    private val i by navArgs<DetailMovieFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        moviewViewModel = ViewModelProvider(requireActivity())[MoviewViewModel::class.java]
        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviewViewModel.callDetailApiFilm(i.filmid)
        moviewViewModel.liveDataDetailFilms().observe(requireActivity()){
            if(it != null){
                binding.apply {
                    Glide.with(root.context).load(it.image).into(imageTVShow)
                    textName.text = it.title
                    tvjapanTitle.text = it.originalTitle
                    tvOriTitle.text = it.originalTitleRomanised
                    tvRealaseDate.text = it.releaseDate
                    textDescription.text = it.description
                    textRating.text = it.rtScore
                    textRuntime.text = it.runningTime
                    buttonWebsite.setOnClickListener { its ->
                        val x = Intent(Intent.ACTION_VIEW)
                        x.data= Uri.parse(it.url)
                        startActivity(x)
                    }


                }
            }

        }
        checkfilmFav()
        addToFavorite()
    }

    private fun checkfilmFav(){
        favoriteViewModel.checkfilmFavorite(i.movie.id)
        favoriteViewModel.checkmovieFav().observe(requireActivity()){
            if(it != null){
                if(it > 0){
                    binding.ivFav.setIconResource(R.drawable.ic_favorite)
                    Bookmarked = true
                }else{
                    binding.ivFav.setIconResource(R.drawable.ic_favorite_border)
                    Bookmarked = false
                }
            }
        }
    }

    private fun addToFavorite(){
        binding.ivFav.setOnClickListener {
            Bookmarked = ! Bookmarked
            if(Bookmarked){
                favoriteViewModel.postUser(
                    MovieFav(
                    i.movie.title,
                    0,
                    i.movie.image,
                    i.movie.director,
                    i.movie.originalTitle,
                    i.movie.releaseDate
                )
                )
            }else{
                favoriteViewModel.deleteUser(
                    MovieFav(
                        i.movie.title,
                        0,
                        i.movie.image,
                        i.movie.director,
                        i.movie.originalTitle,
                        i.movie.releaseDate
                )
                )
                favoriteViewModel.deleteMovieFav().observe(requireActivity()){
                    binding.ivFav.setIconResource(R.drawable.ic_favorite_border)
                }
            }
            binding.ivFav.setIconResource(R.drawable.ic_favorite
            )
        }
    }


}