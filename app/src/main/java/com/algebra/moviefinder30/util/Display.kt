package com.algebra.moviefinder30.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.FragmentActivity
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.presentation.ui.dialog.CustomDialog
import com.algebra.moviefinder30.presentation.ui.favorites.FavoriteFragment
import com.algebra.moviefinder30.presentation.viewmodel.FavoriteMoviesViewModel
import com.bumptech.glide.Glide

fun displayPic(view: View, pictureURL: String, imageView: ImageView){
    Glide.with(view)
        .load(pictureURL)
        .skipMemoryCache(false)
        .placeholder(R.drawable.no_image_available)
        .into(imageView)
}

fun displayMessage(message: String, context: Context) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun displayProgressBar(progressBar: ProgressBar){
    progressBar.visibility = View.VISIBLE
}

fun hideProgressBar(progressBar: ProgressBar){
    progressBar.visibility = View.GONE
}

fun showRemoveDialog(requireActivity: FragmentActivity, viewModel: FavoriteMoviesViewModel){
    val dialog = CustomDialog("Are you sure you want to remove all favorite movies?")
    dialog.show(requireActivity.supportFragmentManager, "RemoveFavoriteMovies")
    dialog.listener = object : CustomDialog.Listener {
        override fun okPress(isPress: Boolean) {
            viewModel.removeAllFavoriteMovies()
        }
    }
}

fun exitFromApp(activity: AppCompatActivity){
    activity.supportFragmentManager.findFragmentById(R.id.favoriteFragment)?.let {
        if (it is FavoriteFragment) {
            val dialog = CustomDialog("Are you sure you want to exit?")
            dialog.show(activity.supportFragmentManager, "Logout")
            dialog.listener = object : CustomDialog.Listener {
                override fun okPress(isPress: Boolean) {
                    finishAffinity(activity)
                }
            }
        }
    }
}