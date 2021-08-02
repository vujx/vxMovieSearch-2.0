package com.algebra.moviefinder30.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.algebra.moviefinder30.R
import com.algebra.moviefinder30.presentation.ui.MainActivity
import com.algebra.moviefinder30.presentation.ui.dialog.CustomDialog
import com.algebra.moviefinder30.presentation.ui.favorites.FavoriteFragment
import com.bumptech.glide.Glide

fun displayPic(view: View, pictureURL: String, imageView: ImageView) {
    Glide.with(view)
        .load(pictureURL)
        .skipMemoryCache(false)
        .placeholder(R.drawable.imagenotavaliable)
        .error(R.drawable.imagenotavaliable)
        .into(imageView)
}

fun displayMessage(message: String, context: Context) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun displayProgressBar(progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
}

fun hideProgressBar(progressBar: ProgressBar) {
    progressBar.visibility = View.GONE
}

fun showRemoveDialog(requireActivity: FragmentActivity, fragment: FavoriteFragment) {
    val dialog = CustomDialog(requireActivity.getString(R.string.remove_fav), fragment)
    dialog.show(requireActivity.supportFragmentManager, "RemoveFavoriteMovies")
    fragment.okPress()
}

fun exitFromApp(activity: MainActivity) {
    activity.supportFragmentManager.findFragmentById(R.id.fragment)?.let {
        if (it.childFragmentManager.fragments[0].toString().contains("FavoriteFragment")) {
            val dialog = CustomDialog(activity.getString(R.string.exit_app), activity)
            dialog.show(activity.supportFragmentManager, "Logout")
            activity.okPress()
        } else activity.onBack()
    }
}

fun displayErrorMessage(throwable: Throwable, context: Context) {
    when {
        throwable.toString().contains("NoConnectivityException") || throwable.toString().contains("No address associated with hostname") ->
            displayMessage(context.getString(R.string.check_internet), context)
        throwable.toString().contains("HTTP 404 Not Found") -> displayMessage(context.getString(R.string.movie_not_found), context)
        else -> displayMessage(context.getString(R.string.error_message), context)
    }
}
