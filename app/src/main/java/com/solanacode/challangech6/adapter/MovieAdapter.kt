package com.solanacode.challangech6.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solanacode.challangech6.databinding.ItemMovieBinding
import com.solanacode.challangech6.model.MovieResponseItem

class MovieAdapter(val listener: Clicked) : RecyclerView.Adapter<MovieAdapter.MovieVieHolder>() {

    val data = object : DiffUtil.ItemCallback<MovieResponseItem>(){
        override fun areItemsTheSame(
            oldItem: MovieResponseItem,
            newItem: MovieResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieResponseItem,
            newItem: MovieResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val util = AsyncListDiffer(this,data)

    fun submitData(list : List<MovieResponseItem>) = util.submitList(list)

    inner class MovieVieHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVieHolder {
        return MovieVieHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieVieHolder, position: Int) {
        holder.binding.apply {
            tvcardName.text = util.currentList[position].title
            tvDirectorMovie.text = util.currentList[position].director
            tvoriginalTitle.text = util.currentList[position].originalTitleRomanised
            tvRealaseDateMovie.text = util.currentList[position].releaseDate
            Glide.with(root.context).load(util.currentList[position].image).into(imgMovie)
            cardMovie.setOnClickListener {
                listener.onClick(util.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int = util.currentList.size

    interface Clicked{
        fun onClick(MovieResponseItem : MovieResponseItem)
    }
}