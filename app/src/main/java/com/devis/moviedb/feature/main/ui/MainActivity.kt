package com.devis.moviedb.feature.main.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devis.moviedb.R
import com.devis.moviedb.core.base.BaseActivity
import com.devis.moviedb.core.base.BaseViewState
import com.devis.moviedb.core.di.CoreModule
import com.devis.moviedb.core.model.MasterDataMdl
import com.devis.moviedb.core.utils.hide
import com.devis.moviedb.core.utils.show
import com.devis.moviedb.core.utils.showIf
import com.devis.moviedb.core.utils.showOrHideShimmer
import com.devis.moviedb.databinding.ActivityMainBinding
import com.devis.moviedb.feature.main.di.DaggerMainComponent
import com.devis.moviedb.feature.main.di.MainModule
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var mAdapter: GenreListAdapter

    private val viewModelMain: MainViewModel by viewModels { viewModelFactory }
    private val genreList: ArrayList<MasterDataMdl> = arrayListOf()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        initObserve()
        initToolbar()
        initViewAction()
        initRecyclerView()
    }

    private fun injectDI() {
        DaggerMainComponent.builder().mainModule(MainModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    private fun initObserve() {
        viewModelMain.run {
            getGenreList()
            genreListResult.observe(this@MainActivity, {
                getDataBinding().layoutError.layoutParent.showIf(it is BaseViewState.Error)
                getDataBinding().shimmerGenre.showOrHideShimmer(it is BaseViewState.Loading)
                when (it) {
                    is BaseViewState.Loading -> {
                        Timber.d("loading get list genre")
                    }
                    is BaseViewState.Success -> {
                        Timber.d("get list genre success")
                        getDataBinding().rvGenre.show()
                        it.data?.let { data ->
                            if (data.genres.isNullOrEmpty().not()) {
                                genreList.clear()
                                genreList.addAll(data.genres!!)
                                mAdapter.submitList(genreList)
                            }
                        }
                    }
                    is BaseViewState.Error -> {
                        Timber.e("get list genre error")
                        Timber.e("errorMessage: ${it.errorMessage}")
                        getDataBinding().rvGenre.hide()
                    }
                }
            })
        }
    }

    private fun initToolbar() {
        with(getDataBinding()) {
            setSupportActionBar(layoutToolbar.toolbar)
        }
    }

    private fun initViewAction() {
        with(getDataBinding()) {
            layoutError.btnReload.setOnClickListener {
                viewModelMain.getGenreList()
            }
        }
    }

    private fun initRecyclerView() {
        with(getDataBinding()) {
            val linearLayoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            mAdapter = GenreListAdapter()
            rvGenre.apply {
                layoutManager = linearLayoutManager
                adapter = mAdapter
            }
        }
    }

}