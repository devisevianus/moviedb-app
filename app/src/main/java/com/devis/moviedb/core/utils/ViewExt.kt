package com.devis.moviedb.core.utils

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.devis.moviedb.R
import com.facebook.shimmer.ShimmerFrameLayout

/**
 * Created by devisevianus on 24/02/22
 */

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_placeholder_image))
        .error(ContextCompat.getDrawable(context, R.drawable.ic_placeholder_image))
        .into(this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(type: Int = 0) {
    visibility = if (type == 0) {
        View.GONE
    } else {
        View.INVISIBLE
    }
}

fun View.showIf(condition: Boolean, type: Int = 0) {
    if (condition) {
        show()
    } else {
        hide(type)
    }
}

fun ShimmerFrameLayout.showOrHideShimmer(isShow: Boolean) {
    showIf(isShow)
    if (isShow) {
        startShimmer()
    } else {
        stopShimmer()
    }
}