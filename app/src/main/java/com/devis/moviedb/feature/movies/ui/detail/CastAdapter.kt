package com.devis.moviedb.feature.movies.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devis.moviedb.core.helpers.Constants
import com.devis.moviedb.core.model.CastMdl
import com.devis.moviedb.core.utils.loadImage
import com.devis.moviedb.databinding.ItemCastBinding

/**
 * Created by devisevianus on 25/02/22
 */

class CastAdapter : ListAdapter<CastMdl, CastAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CastMdl) {
            with(binding) {
                ivCastAvatar.loadImage(Constants.IMAGE_URL + item.profilePath)
                tvCastName.text = item.name
                tvCharacterName.text = item.character
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CastMdl>() {
        override fun areItemsTheSame(oldItem: CastMdl, newItem: CastMdl): Boolean {
            return oldItem.castId == newItem.castId
        }

        override fun areContentsTheSame(oldItem: CastMdl, newItem: CastMdl): Boolean {
            return oldItem == newItem
        }
    }

}