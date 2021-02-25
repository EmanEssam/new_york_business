package com.example.moviesapp.data.repository

import androidx.annotation.MainThread
import com.example.moviesapp.data.remote.api.BusinessService
import com.example.moviesapp.model.BusinessDetailsResponse
import com.example.moviesapp.model.Businesses
import com.example.moviesapp.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
@ExperimentalCoroutinesApi
@Singleton
class BusinessesRepository @Inject constructor(
    private val businessService: BusinessService
) {

    @ExperimentalCoroutinesApi
    suspend fun getAllBusiness(): Flow<State<List<Businesses>>> {
        return object : NetworkBoundRepository<List<Businesses>>() {
            override suspend fun fetchFromRemote(): List<List<Businesses>> =
                listOf(
                    businessService.getNewYorkCityBusinesses("Bearer F-LD2qSoIKKc6MC96FZtjTDO_jO_wAQ1I0Xz-OCLukJOdXyVcPcaVw0lfs-t1HBwoc8oa3sli6Dirs6oXVti6HH1Yqo4XWDtw6yZDifbYsMHColr_qjZDJtY6_6rX3Yx")
                        .body()!!.businesses
                )

        }.asFlow()

    }


    @MainThread
    suspend fun fetchBusinessDetailsFromRemote(id: String): BusinessDetailsResponse =
        businessService.getBusinessById(
            id,
            "Bearer F-LD2qSoIKKc6MC96FZtjTDO_jO_wAQ1I0Xz-OCLukJOdXyVcPcaVw0lfs-t1HBwoc8oa3sli6Dirs6oXVti6HH1Yqo4XWDtw6yZDifbYsMHColr_qjZDJtY6_6rX3Yx"
        ).body()!!

}

