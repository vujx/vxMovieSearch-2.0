package com.algebra.moviefinder30.data.di

import com.algebra.moviefinder30.presentation.ui.favorites.FavoriteAdapterListener
import com.algebra.moviefinder30.presentation.ui.favorites.FavoriteFragment
import com.algebra.moviefinder30.presentation.ui.search.SearchAdapterListener
import com.algebra.moviefinder30.presentation.ui.search.SearchFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
object AdapterModule {

    @Provides
    fun provideFavoriteAdapterListener(impl: FavoriteFragment): FavoriteAdapterListener = impl

    @Provides
    fun provideSearchAdapterListener(impl: SearchFragment): SearchAdapterListener = impl
}
