package com.example.moviesapp.ui.main.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ItemBusinessBinding
import com.example.moviesapp.model.Businesses

/**
 * [RecyclerView.ViewHolder] implementation to inflate View for RecyclerView.
 */
class BusinessViewHolder(private val binding: ItemBusinessBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(business: Businesses, onItemClicked: (Businesses, ImageView) -> Unit) {
        binding.businessName.text = business.name
        binding.businessCategory.text = business.categories.first().title
        binding.businessCategoryPrice.text = business.categories.first().alias
        binding.businessRating.text = business.rating.toString()
        binding.businessReview.text = business.review_count.toString()
        binding.imageView.load(business.image_url) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
        binding.root.setOnClickListener {
            onItemClicked(business, binding.imageView)
        }
    }
}
