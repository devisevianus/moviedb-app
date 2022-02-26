package com.devis.moviedb.feature.main.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devis.moviedb.core.model.MasterDataMdl
import com.devis.moviedb.databinding.ItemGenreBinding
import com.devis.moviedb.feature.movies.ui.list.MovieListActivity

/**
 * Created by devisevianus on 23/02/22
 */

class GenreListAdapter : ListAdapter<MasterDataMdl, GenreListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),)
    }

    class ViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MasterDataMdl) {
            with(binding) {
                tvGenreName.text = item.name
                root.setOnClickListener {
                    MovieListActivity.startThisActivity(
                        root.context,
                        genreId = item.id ?: 0,
                        genreName = item.name.orEmpty())
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MasterDataMdl>() {
        override fun areItemsTheSame(
            oldItem: MasterDataMdl, newItem: MasterDataMdl
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MasterDataMdl, newItem: MasterDataMdl
        ): Boolean {
            return oldItem == newItem
        }
    }

}