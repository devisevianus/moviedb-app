package com.devis.moviedb.feature.movies.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devis.moviedb.core.helpers.Constants
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.utils.loadImage
import com.devis.moviedb.core.utils.showIf
import com.devis.moviedb.databinding.ItemLoadingBinding
import com.devis.moviedb.databinding.ItemMovieBinding
import com.devis.moviedb.feature.movies.ui.detail.MovieDetailActivity

/**
 * Created by devisevianus on 24/02/22
 */

class MovieListAdapter : ListAdapter<MovieMdl, MovieListAdapter.ViewHolder>(DiffCallback()) {

    var isLoading = false
    var isLast = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<MovieMdl>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieMdl) {
            with(binding) {
                ivMoviePoster.loadImage(Constants.IMAGE_URL + item.posterPath)
                tvMovieName.text = item.title
                root.setOnClickListener {
                    MovieDetailActivity.startThisActivity(root.context, movie = item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MovieMdl>() {
        override fun areItemsTheSame(
            oldItem: MovieMdl, newItem: MovieMdl
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MovieMdl, newItem: MovieMdl
        ): Boolean {
            return oldItem == newItem
        }
    }

}