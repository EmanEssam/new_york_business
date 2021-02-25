package com.example.moviesapp.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.model.Businesses
import com.example.moviesapp.model.State
import com.example.moviesapp.ui.base.BaseActivity
import com.example.moviesapp.ui.details.BusinessDetailsActivity
import com.example.moviesapp.ui.main.adapter.BusinessListAdapter
import com.example.moviesapp.utils.getColorRes
import com.example.moviesapp.utils.hide
import com.example.moviesapp.utils.show
import com.example.moviesapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val mViewModel: MainViewModel by viewModels()

    private val mAdapter = BusinessListAdapter(this::onItemClicked)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        // Initialize RecyclerView
        mViewBinding.postsRecyclerView.adapter = mAdapter

        initLocalBusiness()

        handleNetworkChanges()
    }

    private fun initLocalBusiness() {
        mViewModel.businessesLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        }

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getBusiness()
        }

        // If State isn't `Success` then reload posts.
        if (mViewModel.businessesLiveData.value !is State.Success) {
            getBusiness()
        }
    }


    private fun getBusiness() {
        mViewModel.getBusinesses()
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mViewModel.businessesLiveData.value is State.Error<*> || mAdapter.itemCount == 0) {
                    getBusiness()
                }
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        }
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onItemClicked(business: Businesses, imageView: ImageView) {
        val intent = Intent(this, BusinessDetailsActivity::class.java)
        intent.putExtra(BusinessDetailsActivity.Businesses_ID, business.id)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            imageView.transitionName
        )

        startActivity(intent, options.toBundle())
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }
}