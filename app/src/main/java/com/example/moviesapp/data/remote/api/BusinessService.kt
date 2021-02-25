package com.example.moviesapp.data.remote.api

import com.example.moviesapp.model.BusinessDetailsResponse
import com.example.moviesapp.model.BusinessResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Service to fetch local business using Yelp end point .
 */
interface BusinessService {

    @GET("businesses/search?location=NYC&limit=50&offset=0")
    suspend fun getNewYorkCityBusinesses(
        @Header("Authorization") apiKey: String
    ): Response<BusinessResponse>


    @GET("https://api.yelp.com/v3/businesses/{id}")
    suspend fun getBusinessById(@Path("id") id: String, @Header("Authorization") apiKey: String)
            : Response<BusinessDetailsResponse>


    companion object {
        const val YELP_API_URL = "https://api.yelp.com/v3/"
    }
}
