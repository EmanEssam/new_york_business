package com.example.moviesapp.model

data class BusinessDetailsResponse(
    val alias: String,
    val categories: List<CategoryX>,
    val coordinates: CoordinatesX,
    val display_phone: String,
    val hours: List<Hour>,
    val id: String,
    val image_url: String,
    val is_claimed: Boolean,
    val is_closed: Boolean,
    val location: LocationX,
    val name: String,
    val phone: String,
    val photos: List<String>,
    val rating: Double,
    val review_count: Int,
    val transactions: List<Any>,
    val url: String
)