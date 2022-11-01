package com.solanacode.challangech6.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solanacode.challangech6.databinding.ItemFavoriteBinding
import com.solanacode.challangech6.model.MovieFav

class FavoriteMovieAdapter() : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    private val data = object : DiffUtil.ItemCallback<MovieFav>(){
        override fun areItemsTheSame(oldItem: MovieFav, newItem: MovieFav): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: MovieFav, newItem: MovieFav): Boolean {
            return when{
                oldItem.name != newItem.name -> false
                oldItem.image != newItem.image -> false
                oldItem.director != newItem.director-> false
                oldItem.oriTitle != newItem.oriTitle-> false
                oldItem.releaseDate != newItem.releaseDate-> false
                else -> true
            }
        }
    }

    val util = AsyncListDiffer(this,data)

    fun submitData(list : List<MovieFav>) = util.submitList(list)

    inner class FavoriteMovieViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        return FavoriteMovieViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.binding.apply {
            tvcardName.text = util.currentList[position].name
            tvDirectorMovie.text = util.currentList[position].director
            tvoriginalTitle.text = util.currentList[position].oriTitle
            tvRealaseDateMovie.text = util.currentList[position].releaseDate
            Glide.with(root.context).load(util.currentList[position].image).into(imgMovie)

        }
    }

    override fun getItemCount(): Int = util.currentList.size


}