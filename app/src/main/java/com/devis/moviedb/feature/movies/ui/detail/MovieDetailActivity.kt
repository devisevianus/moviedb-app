package com.devis.moviedb.feature.movies.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devis.moviedb.core.base.BaseActivity
import com.devis.moviedb.core.base.BaseViewState
import com.devis.moviedb.core.di.CoreModule
import com.devis.moviedb.core.helpers.Constants
import com.devis.moviedb.core.helpers.DateHelper
import com.devis.moviedb.core.helpers.DateHelper.changeFormatDate
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.model.ReviewMdl
import com.devis.moviedb.core.utils.*
import com.devis.moviedb.databinding.ActivityMovieDetailBinding
import com.devis.moviedb.feature.movies.di.DaggerMoviesComponent
import com.devis.moviedb.feature.movies.di.MoviesModule
import com.devis.moviedb.feature.movies.ui.MoviesViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import timber.log.Timber
import javax.inject.Inject
import com.devis.moviedb.R

/**
 * Created by devisevianus on 24/02/22
 */

class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding>() {

    companion object {
        private const val EXTRA_MOVIE = "extra_movie"
        private const val TYPE_TEASER = "teaser"
        private const val TYPE_TRAILER = "trailer"
        private const val JOB_ACTING = "acting"

        fun startThisActivity(context: Context, movie: MovieMdl) {
            context.startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, Gson().toJson(movie))
            })
        }
    }

    private lateinit var mAdapterCast: CastAdapter
    private lateinit var mAdapterTrailer: TrailerAdapter
    private lateinit var mAdapterReview: ReviewAdapter

    private var movieMdl: MovieMdl? = null
    private var mLoadMoreListener: RecyclerView.OnScrollListener? = null
    private var page: Int = 1
    private var isInit: Boolean = true

    private val viewModelMovie: MoviesViewModel by viewModels { viewModelFactory }
    private val reviewList: ArrayList<ReviewMdl> = arrayListOf()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutResource: Int
        get() = R.layout.activity_movie_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            movieMdl = Gson().fromJson(it.getString(EXTRA_MOVIE), MovieMdl::class.java)
        }
        injectDI()
        initObserve()
        initToolbar()
        initView()
        initViewAction()
        initRecyclerViewCast()
        initRecyclerViewTrailer()
        initRecyclerViewReview()
    }

    private fun injectDI() {
        DaggerMoviesComponent.builder().moviesModule(MoviesModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    private fun initObserve() {
        viewModelMovie.run {
            getMovieDetail(movieId = movieMdl?.id ?: 0)
            getMovieReviews(movieId = movieMdl?.id ?: 0)
            movieDetailResult.observe(this@MovieDetailActivity, {
                getDataBinding().shimmerCast.showOrHideShimmer(it is BaseViewState.Loading)
                getDataBinding().shimmerVideo.showOrHideShimmer(it is BaseViewState.Loading)
                getDataBinding().layoutErrorCast.layoutParent.showIf(it is BaseViewState.Error)
                getDataBinding().layoutErrorVideo.layoutParent.showIf(it is BaseViewState.Error)
                when (it) {
                    is BaseViewState.Loading -> {
                        Timber.d("loading get movie detail")
                    }
                    is BaseViewState.Success -> {
                        Timber.d("get movie detail success")
                        it.data?.videos?.let { videoListMdl ->
                            if (videoListMdl.results.isNullOrEmpty().not()) {
                                mAdapterTrailer.submitList(videoListMdl.results!!.filter { videoMdl ->
                                    videoMdl.type?.contains(TYPE_TEASER, ignoreCase = true) == true ||
                                            videoMdl.type?.contains(TYPE_TRAILER, ignoreCase = true) == true
                                })
                            }
                        }
                        it.data?.credits?.let { creditsMdl ->
                            if (creditsMdl.cast.isNullOrEmpty().not()) {
                                mAdapterCast.submitList(creditsMdl.cast!!.filter { castMdl ->
                                    castMdl.knownForDepartment?.contains(JOB_ACTING, ignoreCase = true) == true
                                })
                            }
                        }
                    }
                    is BaseViewState.Error -> {
                        Timber.e("get movie detail error")
                        Timber.e("errorMessage: ${it.errorMessage}")
                    }
                }
            })
            movieReviewsResult.observe(this@MovieDetailActivity, {
                getDataBinding().layoutErrorReview.layoutParent.showIf(it is BaseViewState.Error)
                getDataBinding().shimmerReview.showOrHideShimmer(it is BaseViewState.Loading && isInit)
                when (it) {
                    is BaseViewState.Loading -> {
                        Timber.d("loading get movie reviews")
                    }
                    is BaseViewState.Success -> {
                        Timber.d("get movie reviews success")
                        mAdapterReview.isLoading = false
                        isInit = false
                        it.data?.let { data ->
                            Timber.d("is last data: ${data.isLast}")
                            getDataBinding().tvLabelReviews.text = String.format(
                                getString(R.string.label_review_with_total),
                                data.totalResults ?: 0
                            )
                            mAdapterReview.isLast = data.isLast
                            data.results?.let { reviewListMdl ->
                                reviewList.addAll(reviewListMdl)
                                mAdapterReview.addDataAndSubmitList(reviewList)
                            }
                            getDataBinding().tvNoReview.showIf(reviewList.isEmpty())
                        }
                    }
                    is BaseViewState.Error -> {
                        Timber.e("get movie reviews error")
                        Timber.e("errorMessage: ${it.errorMessage}")
                        mAdapterReview.isLoading = false
                    }
                }
            })
        }
    }

    private fun initToolbar() {
        with(getDataBinding()) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val layoutParams = tvMovieName.layoutParams as ConstraintLayout.LayoutParams
            val defaultMargin = layoutParams.topMargin
            appbarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    Timber.d("vertical offset: $verticalOffset")
                    var scrollRange = -1
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                    Timber.d("scroll range: $scrollRange")
                    if (scrollRange + verticalOffset == 0) {
                        Timber.d("total offset: ${scrollRange + verticalOffset}")
                        collapsingToolbar.title = movieMdl?.title
                    } else {
                        collapsingToolbar.title = " "
                    }
                    var totalMargin = verticalOffset + defaultMargin
                    if (totalMargin < 0) {
                        totalMargin = 0
                    }
                    layoutParams.topMargin = totalMargin
                    tvMovieName.layoutParams = layoutParams
            })
        }
    }

    private fun initView() {
        with(getDataBinding()) {
            ivMoviePoster.loadImage(Constants.IMAGE_URL_SIZE_342 + movieMdl?.posterPath)
            ivAppbar.loadImage(Constants.IMAGE_URL_BACKDROP + movieMdl?.backdropPath)
            tvOverview.apply {
                text = movieMdl?.overview
                setShowingLine(8)
                setShowMoreColor(ContextCompat.getColor(this@MovieDetailActivity, R.color.red))
                setShowLessTextColor(ContextCompat.getColor(this@MovieDetailActivity, R.color.red))
            }
            tvMovieName.text = movieMdl?.title
            tvAverageRating.text = movieMdl?.voteAverage.toString()
            tvTotalVotes.text = String.format(
                getString(R.string.label_total_votes),
                movieMdl?.voteCount ?: 0
            )
            tvLanguage.text = movieMdl?.originalLanguage
            tvReleaseDate.text = movieMdl?.releaseDate?.changeFormatDate(
                oldFormat = DateHelper.FORMAT_YYYY_MM_DD,
                newFormat = DateHelper.FORMAT_DD_MMM_YYYY
            )
            tvLabelReviews.text = String.format(
                getString(R.string.label_review_with_total),
                0
            )
        }
    }

    private fun initViewAction() {
        with(getDataBinding()) {
            layoutErrorCast.btnReload.setOnClickListener {
                viewModelMovie.getMovieDetail(movieId = movieMdl?.id ?: 0)
            }
            layoutErrorVideo.btnReload.setOnClickListener {
                viewModelMovie.getMovieDetail(movieId = movieMdl?.id ?: 0)
            }
            layoutErrorReview.btnReload.setOnClickListener {
                mAdapterReview.isLoading = true
                viewModelMovie.getMovieReviews(
                    movieId = movieMdl?.id ?: 0,
                    page = if (isInit) {
                        1
                    } else {
                        page
                    }
                )
            }
        }
    }

    private fun initRecyclerViewCast() {
        with(getDataBinding()) {
            val linearLayoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            mAdapterCast = CastAdapter()
            rvCast.apply {
                layoutManager = linearLayoutManager
                adapter = mAdapterCast
            }
        }
    }

    private fun initRecyclerViewTrailer() {
        with(getDataBinding()) {
            val linearLayoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            mAdapterTrailer = TrailerAdapter()
            rvVideo.apply {
                layoutManager = linearLayoutManager
                adapter = mAdapterTrailer
            }
        }
    }

    private fun initRecyclerViewReview() {
        with(getDataBinding()) {
            val linearLayoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            mAdapterReview = ReviewAdapter()
            mLoadMoreListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    if (mAdapterReview.isLoading.not() && mAdapterReview.isLast.not()) {
                        this@MovieDetailActivity.page = page
                        mAdapterReview.isLoading = true
                        viewModelMovie.getMovieReviews(
                            movieId = movieMdl?.id ?: 0,
                            page = page
                        )
                    }
                }
            }
            rvReviews.apply {
                layoutManager = linearLayoutManager
                adapter = mAdapterReview
                addOnScrollListener(mLoadMoreListener as EndlessRecyclerViewScrollListener)
            }
        }
    }

}