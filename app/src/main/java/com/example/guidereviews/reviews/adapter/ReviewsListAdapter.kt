package com.example.guidereviews.reviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guidereviews.R
import com.example.guidereviews.reviews.viewstate.ReviewItem

class ReviewsListAdapter : RecyclerView.Adapter<ReviewsListViewHolder>() {

    private var reviewsList: List<ReviewItem> = emptyList()
    private var listener: Listener = Listener.NO_OP

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.reviews_item_row, parent, false)
        return ReviewsListViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ReviewsListViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }

    override fun getItemCount(): Int = reviewsList.size

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun setItems(reviewsList: List<ReviewItem>) {
        this.reviewsList = reviewsList
        notifyDataSetChanged()
    }

    fun getItems() : List<ReviewItem>{
        return reviewsList
    }

    interface Listener : ReviewsListViewHolder.Listener {
        companion object {
            val NO_OP: Listener = object : Listener {
                override fun reviewItemSelected(reviewItem: ReviewItem) {
                    //NO_OP
                }
            }
        }
    }

}