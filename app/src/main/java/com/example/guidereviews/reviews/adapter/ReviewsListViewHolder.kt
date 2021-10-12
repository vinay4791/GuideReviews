package com.example.guidereviews.reviews.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.guidereviews.R
import com.example.guidereviews.reviews.viewstate.ReviewItem
import kotlinx.android.synthetic.main.reviews_item_row.view.*

class ReviewsListViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    fun bind(reviewItem: ReviewItem) {
        itemView.review_date_tv.text = reviewItem.date
        itemView.review_message_tv.text = reviewItem.message
        itemView.review_author_name_country_tv.text = reviewItem.authorAndCountry
        itemView.review_rating.rating =  reviewItem.rating.toFloat()

        Glide.with(itemView.context)
            .load(reviewItem.authorImage)
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(itemView.review_author_iv)

        itemView.setOnClickListener {
            listener.reviewItemSelected(reviewItem)
        }
    }

    interface Listener {
        fun reviewItemSelected(reviewItem: ReviewItem)
    }

}