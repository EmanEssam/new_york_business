package com.example.moviesapp.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import coil.api.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityBusinessDetailsBinding
import com.example.moviesapp.model.BusinessDetailsResponse
import com.example.moviesapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_business_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BusinessDetailsActivity :
    BaseActivity<BusinessDetailsViewModel, ActivityBusinessDetailsBinding>() {
    override val mViewModel: BusinessDetailsViewModel by viewModels()

    private lateinit var business: BusinessDetailsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val businessesId = intent.extras?.getString(Businesses_ID)
            ?: throw IllegalArgumentException("`BusinessesId` must be non-null")

        initBusinesses(businessesId)
    }

    private fun initBusinesses(BusinessesId: String) {
        mViewModel.getBusinessById(BusinessesId)
        mViewModel.businessesLiveData.observe(this, { businesses ->
            business = businesses
            mViewBinding.postContent.apply {
                businessTitle.text = businesses.name
                businessCategory.text = businesses.categories.first().alias
                businessReview.text = businesses.name

            }
            mViewBinding.imageView.load(businesses.image_url)

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun getViewBinding(): ActivityBusinessDetailsBinding =
        ActivityBusinessDetailsBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }

            R.id.action_share -> {
                val shareMsg = getString(
                    R.string.share_message,
                    business.name,
                    business.phone
                )

                val intent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareMsg)
                    .intent

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val Businesses_ID = "BusinessesId"
    }

}