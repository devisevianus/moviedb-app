package com.devis.moviedb.feature.movies.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.devis.moviedb.R
import com.devis.moviedb.core.model.ReviewMdl
import com.devis.moviedb.core.utils.loadImage
import com.devis.moviedb.core.utils.showIf
import com.devis.moviedb.databinding.ItemLoadingBinding
import com.devis.moviedb.databinding.ItemReviewBinding
import timber.log.Timber

/**
 * Created by devisevianus on 24/02/22
 */

class ReviewAdapter : ListAdapter<ReviewAdapter.DataItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private val loading = 1
    private val content = 2

    var isLoading = false
    var isLast = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == loading) {
            ViewHolderLoadMore(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ViewHolder(
                ItemReviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderLoadMore) {
            holder.bind(isLast)
        } else if (holder is ViewHolder) {
            val item = getItem(position) as DataItem.ReviewItem
            holder.bind(item.reviewMdl)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.ReviewItem -> content
            is DataItem.LoadingItem -> loading
        }
    }

    override fun submitList(list: List<DataItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    fun addDataAndSubmitList(list: MutableList<ReviewMdl>) {
        val items: List<DataItem> = if (list.size != 1) {
            when (isLast) {
                true -> list.map { DataItem.ReviewItem(it) }
                false -> list.map { DataItem.ReviewItem(it) } + listOf(DataItem.LoadingItem)
            }
        } else {
            list.map {
                DataItem.ReviewItem(it)
            }
        }
        submitList(items)
    }

    sealed class DataItem {
        data class ReviewItem(val reviewMdl: ReviewMdl) : DataItem() {
            override val reviewId = reviewMdl.id!!
        }

        object LoadingItem : DataItem() {
            override val reviewId = (Int.MIN_VALUE).toString()
        }

        abstract val reviewId: String
    }


    class ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewMdl) {
            with(binding) {
                Timber.d("avatar url: ${item.authorDetails.avatarUrl}")
                if (item.authorDetails.avatarUrl.contains("http")) {
                    ivAuthorAvatar.loadImage(item.authorDetails.avatarUrl)
                } else {
                    val generator: ColorGenerator = ColorGenerator.MATERIAL
                    val color: Int = generator.randomColor
                    val drawable: TextDrawable = TextDrawable.builder()
                        .buildRound(item.author!!.substring(0, 1).uppercase(), color)
                    ivAuthorAvatar.setImageDrawable(drawable)
                }
                tvAuthorName.text = item.author
                tvAuthorReview.apply {
                    text = item.content
                    setShowMoreColor(ContextCompat.getColor(root.context, R.color.red))
                    setShowLessTextColor(ContextCompat.getColor(root.context, R.color.red))
                    setShowingLine(3)
                }
            }
        }
    }

    class ViewHolderLoadMore(private val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(isLast: Boolean) {
            binding.root.showIf(isLast.not())
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.reviewId == newItem.reviewId
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

}