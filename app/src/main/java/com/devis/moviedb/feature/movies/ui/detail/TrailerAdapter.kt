package com.devis.moviedb.feature.movies.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devis.moviedb.core.helpers.Constants
import com.devis.moviedb.core.model.VideoMdl
import com.devis.moviedb.core.utils.loadImage
import com.devis.moviedb.databinding.ItemTrailerBinding

/**
 * Created by devisevianus on 24/02/22
 */

class TrailerAdapter : ListAdapter<VideoMdl, TrailerAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTrailerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoMdl) {
            with(binding) {
                ivThumbnail.loadImage(
                    Constants.YOUTUBE_THUMBNAIL_URL + item.key + Constants.YOUTUBE_JPG_FORMAT
                )
                root.setOnClickListener {
                    val intent: Intent = if (Intent().resolveActivity(root.context.packageManager) != null) {
                        Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + item.key))
                    } else {
                        Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_WEB_URL + item.key))
                    }
                    root.context.startActivity(intent)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<VideoMdl>() {
        override fun areItemsTheSame(oldItem: VideoMdl, newItem: VideoMdl): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VideoMdl, newItem: VideoMdl): Boolean {
            return oldItem == newItem
        }
    }

}