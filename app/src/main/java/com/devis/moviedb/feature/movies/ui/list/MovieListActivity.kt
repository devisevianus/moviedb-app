package com.devis.moviedb.feature.movies.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devis.moviedb.R
import com.devis.moviedb.core.base.BaseActivity
import com.devis.moviedb.core.base.BaseViewState
import com.devis.moviedb.core.di.CoreModule
import com.devis.moviedb.core.model.MovieMdl
import com.devis.moviedb.core.utils.EndlessRecyclerViewScrollListener
import com.devis.moviedb.core.utils.showIf
import com.devis.moviedb.core.utils.showOrHideShimmer
import com.devis.moviedb.databinding.ActivityMovieListBinding
import com.devis.moviedb.feature.movies.di.DaggerMoviesComponent
import com.devis.moviedb.feature.movies.di.MoviesModule
import com.devis.moviedb.feature.movies.ui.MoviesViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by devisevianus on 24/02/22
 */

class MovieListActivity : BaseActivity<ActivityMovieListBinding>() {

    companion object {
        private const val EXTRA_GENRE_ID = "extra_genre_id"
        private const val EXTRA_GENRE_NAME = "extra_genre_name"

        fun startThisActivity(context: Context, genreId: Int, genreName: String) {
            context.startActivity(Intent(context, MovieListActivity::class.java).apply {
                putExtra(EXTRA_GENRE_ID, genreId)
                putExtra(EXTRA_GENRE_NAME, genreName)
            })
        }
    }

    private lateinit var mAdapter: MovieListAdapter

    private var genreId: Int = 0
    private var genreName: String? = null
    private var mLoadMoreListener: RecyclerView.OnScrollListener? = null
    private var isInit: Boolean = true
    private var page: Int = 1

    private val viewModelMovies: MoviesViewModel by viewModels { viewModelFactory }
    private val movieList: ArrayList<MovieMdl> = arrayListOf()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutResource: Int
        get() = R.layout.activity_movie_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            genreId = it.getInt(EXTRA_GENRE_ID)
            genreName = it.getString(EXTRA_GENRE_NAME)
        }
        injectDI()
        initObserve()
        initToolbar()
        initViewAction()
        initRecyclerView()
    }

    private fun injectDI() {
        DaggerMoviesComponent.builder().coreModule(CoreModule(this))
            .moviesModule(MoviesModule())
            .build().inject(this)
    }

    private fun initObserve() {
        viewModelMovies.run {
            getMovieList(genreId)
            movieListResult.observe(this@MovieListActivity, {
                getDataBinding().layoutErrorGeneral.layoutParent.showIf(it is BaseViewState.Error && movieList.isEmpty())
                getDataBinding().shimmerMovie.showOrHideShimmer(it is BaseViewState.Loading && isInit)
                getDataBinding().prgBar.showIf(it is BaseViewState.Loading && isInit.not())
                getDataBinding().layoutErrorList.layoutParent.showIf(it is BaseViewState.Error && movieList.isNotEmpty())
                when (it) {
                    is BaseViewState.Loading -> {
                        Timber.d("loading get list movie")
                    }
                    is BaseViewState.Success -> {
                        mAdapter.isLoading = false
                        isInit = false
                        Timber.d("get list movie success")
                        it.data?.let { data ->
                            Timber.d("is last data: ${data.isLast}")
                            mAdapter.isLast = data.isLast
                            movieList.addAll(data.results!!)
                            mAdapter.submitList(movieList)
                        }
                    }
                    is BaseViewState.Error -> {
                        Timber.e("get list movie error")
                        Timber.e("errorMessage: ${it.errorMessage}")
                        mAdapter.isLoading = false
                    }
                }
            })
        }
    }

    private fun initToolbar() {
        with(getDataBinding()) {
            layoutToolbar.toolbar.title = genreName
            setSupportActionBar(layoutToolbar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initViewAction() {
        with(getDataBinding()) {
            layoutErrorGeneral.btnReload.setOnClickListener {
                isInit = true
                viewModelMovies.getMovieList(genreId)
            }
            layoutErrorList.btnReload.setOnClickListener {
                rvMovieList.smoothScrollToPosition(mAdapter.itemCount - 1)
                viewModelMovies.getMovieList(genreId, page)
            }
        }
    }

    private fun initRecyclerView() {
        with(getDataBinding()) {
            val gridLayoutManager = GridLayoutManager(
                this@MovieListActivity, 3
            )
            mAdapter = MovieListAdapter()
            mLoadMoreListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    if (mAdapter.isLoading.not() && mAdapter.isLast.not()) {
                        this@MovieListActivity.page = page
                        mAdapter.isLoading = true
                        viewModelMovies.getMovieList(genreId, page)
                    }
                }
            }
            rvMovieList.apply {
                layoutManager = gridLayoutManager
                adapter = mAdapter
                addOnScrollListener(mLoadMoreListener as EndlessRecyclerViewScrollListener)
                setHasFixedSize(true)
            }
        }
    }

}