package com.example.guidereviews.reviews

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.guidereviews.R
import com.example.guidereviews.base.BaseActivity
import kotlinx.android.synthetic.main.activity_reviews.*

class ReviewActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun initialize() {
        setContentView(R.layout.activity_reviews)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}