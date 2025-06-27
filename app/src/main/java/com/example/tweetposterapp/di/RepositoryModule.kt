package com.example.tweetposterapp.di

import com.example.data.repository.TweetRepositoryImp
import com.example.domain.repository.TweetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTweetRepository(repository: TweetRepositoryImp): TweetRepository{
        return repository
    }
}