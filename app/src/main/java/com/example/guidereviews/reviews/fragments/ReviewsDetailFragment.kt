package com.example.guidereviews.reviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.guidereviews.R
import com.example.guidereviews.base.BaseFragment
import com.example.guidereviews.reviews.ReviewActivity
import com.example.guidereviews.reviews.ReviewsListRepository
import com.example.guidereviews.reviews.viewmodel.ReviewsViewModel
import com.example.guidereviews.reviews.viewmodel.ReviewsViewModelFactory
import com.example.guidereviews.reviews.viewstate.ReviewItem
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.activity_reviews.view.*
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.ext.android.inject

class ReviewsDetailFragment : BaseFragment() {

    private lateinit var rootView: View
    private val repository: ReviewsListRepository by inject()
    private val viewModel: ReviewsViewModel by activityViewModels() {
        ReviewsViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_details, container, false)
        return rootView

    }

    override fun initialize() {
        observeReviewItemData()
        (activity as ReviewActivity).toolbar?.let {
            it.toolbar_title.text = (activity as ReviewActivity).resources.getString(R.string.reviews_details_text)
        }
        (activity as ReviewActivity).supportActionBar?.let {
            Navigation.findNavController(rootView).addOnDestinationChangedListener { _, destination, _ ->
                if(destination.id == R.id.detailFragment) {
                    it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
                }
            }
        }
    }

    private fun observeReviewItemData() {
        viewModel.getSelectedReviewItem()
            .observe(viewLifecycleOwner, Observer { reviewItem ->
                populateReviewDetails(reviewItem)
            })
    }

    private fun populateReviewDetails(reviewItem: ReviewItem) {
        review_detail_rating.rating = reviewItem.rating.toFloat()
        Glide.with(this)
            .load(reviewItem.authorImage)
            .placeholder(R.drawable.ic_baseline_person_120)
            .into(review_detail_author_iv)
        review_detail_author_name_tv.text = reviewItem.authorName
        review_detail_author_country_tv.text = reviewItem.authorCountry
        review_detail_date_tv.text = reviewItem.date
        review_detail_message_tv.text = reviewItem.message
    }

}