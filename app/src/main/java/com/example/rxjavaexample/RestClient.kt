package com.example.rxjavaexample

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by emb-sadabir on 28/5/18.
 */

class RestClient private constructor() {

    private val gitHubService: GitHubService

    companion object {
        private val M_INSTANCE: RestClient = RestClient()

        @Synchronized
        fun getInstance(): RestClient {
            return M_INSTANCE
        }
    }

    init {
        val baseUrl = "https://api.github.com/"
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }

    fun getStarredRepos(@NonNull userName: String): Observable<List<GitHubRepo>> {
        return gitHubService.getStarredRepositories(userName)
    }
}
