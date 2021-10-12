package com.example.guidereviews.reviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guidereviews.R
import com.example.guidereviews.base.BaseFragment
import com.example.guidereviews.reviews.ReviewActivity
import com.example.guidereviews.reviews.ReviewsListRepository
import com.example.guidereviews.reviews.adapter.ReviewsListAdapter
import com.example.guidereviews.reviews.viewmodel.ReviewsViewModel
import com.example.guidereviews.reviews.viewmodel.ReviewsViewModelFactory
import com.example.guidereviews.reviews.viewstate.ReviewDetails
import com.example.guidereviews.reviews.viewstate.ReviewItem
import com.example.guidereviews.reviews.viewstate.ReviewListViewState
import com.example.guidereviews.util.AppConstants
import com.example.guidereviews.util.Utils
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.activity_reviews.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.ext.android.inject

class ReviewsListFragment : BaseFragment() {

    private lateinit var rootView: View
    private val repository: ReviewsListRepository by inject()
    private val viewModel: ReviewsViewModel by activityViewModels() {
        ReviewsViewModelFactory(repository)
    }

    private val utils: Utils by inject()
    private val adapter: ReviewsListAdapter by inject()
    private var isApiLoading = false
    private var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        return rootView
    }

    override fun initialize() {
        (activity as ReviewActivity).toolbar?.let {
            it.toolbar_title.text =
                (activity as ReviewActivity).resources.getString(R.string.reviews_text)
        }
        observeReviewListData()
        setUpReviewsRecyclerView()
        setUpSwipeRefreshing()

        /*This initial api loading call could have been moved to the ViewModel’s constructor  but,
          inorder to make the viewmodel properly tested I am doing the call here itself
          after checking if the adapter items are empty. If I don't make this check everytime the user
           navigates back from ReviewsDetailFragment this api will be called. */

        if (adapter.getItems().isEmpty()) {
            getReviewList()
        }
    }

    private fun setUpSwipeRefreshing() {
        list_swipe_layout.setOnRefreshListener {
            if (!isApiLoading) {
                getReviewList()
            } else {
                list_swipe_layout.isRefreshing = false
            }
        }
    }

    private fun getReviewList() {
        if (utils.hasInternet()) {
            viewModel.fetchReviewList()
        } else {
            handleApiLoadingError()
        }
    }

    private fun setUpReviewsRecyclerView() {

        reviews_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapter
            addOnScrollListener(this@ReviewsListFragment.scrollListener)
        }

        reviews_recycler_view.layoutManager = LinearLayoutManager(activity)
        reviews_recycler_view.adapter = adapter
        adapter.setListener(listener)
    }

    private fun observeReviewListData() {
        viewModel.reviewListData()
            .observe(viewLifecycleOwner, Observer { reviewListData ->
                when (reviewListData) {
                    is ReviewListViewState.Loading -> {
                        showApiLoadingIndicator()
                        isApiLoading = true
                    }
                    is ReviewListViewState.Success -> {
                        populateReviews(reviewListData.reviewDetails)
                        hideApiLoadingIndicator()
                        isApiLoading = false
                    }
                    is ReviewListViewState.Error -> {
                        handleApiLoadingError()
                        hideApiLoadingIndicator()
                        isApiLoading = false
                    }
                }
            })
    }

    private fun populateReviews(reviewDetails: ReviewDetails) {
        reviews_recycler_view.visibility = View.VISIBLE
        error_text.visibility = View.GONE
        adapter.setItems(reviewDetails.reviewItemList)
    }

    private fun showApiLoadingIndicator() {
        isApiLoading = true
        loadingView.showLoading(R.color.loader_bg_white_transparent)
    }

    private fun hideApiLoadingIndicator() {
        isApiLoading = false
        if (list_swipe_layout.isRefreshing) {
            list_swipe_layout.isRefreshing = false
        }
        loadingView.hideLoading()
    }

    private fun handleApiLoadingError() {
        if(viewModel.paginationOffset == AppConstants.INITIAL_OFFSET) {
            reviews_recycler_view.visibility = View.GONE
            error_text.visibility = View.VISIBLE
            list_swipe_layout.isRefreshing = false
        } else{
            Toast.makeText(activity,
                getString(R.string.error_string),
                Toast.LENGTH_LONG)
                .show()
            list_swipe_layout.isRefreshing = false
        }

    }

    private val listener = object : ReviewsListAdapter.Listener {
        override fun reviewItemSelected(reviewItem: ReviewItem) {
            viewModel.setSelectedReviewItem(reviewItem)
            Navigation.findNavController(rootView)
                .navigate(R.id.action_listFragment_to_detailFragment)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val findFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isApiLoading && viewModel.paginationOffset.toInt() < viewModel.totalCount
            val isAtLastItem = findFirstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = findFirstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= AppConstants.PAGE_LIMIT.toInt()
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (shouldPaginate ) {
                viewModel.fetchReviewList(offset = viewModel.paginationOffset)
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}