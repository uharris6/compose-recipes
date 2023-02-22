package com.uharris.recipes.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uharris.recipes.utils.AppExecutor
import com.uharris.recipes.utils.Executor
import com.uharris.recipes.data.RecipeRepository
import com.uharris.recipes.data.RecipeRepositoryImpl
import com.uharris.recipes.data.services.RecipesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun providesHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://demo2169068.mockable.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesRecipesService(retrofit: Retrofit): RecipesService {
        return retrofit.create(RecipesService::class.java)
    }

    @Provides
    @Singleton
    fun providesExecutor(): Executor {
        return AppExecutor()
    }

    @Provides
    @Singleton
    fun providesRecipesRepository(service: RecipesService, executor: Executor): RecipeRepository {
        return RecipeRepositoryImpl(service, executor)
    }
}