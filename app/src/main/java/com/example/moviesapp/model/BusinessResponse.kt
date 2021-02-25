package com.example.moviesapp.model

data class BusinessResponse(
    val businesses: List<Businesses>,
    val region: Region?,
    val total: Int?
)