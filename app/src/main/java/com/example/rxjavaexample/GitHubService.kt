package com.example.rxjavaexample

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by emb-sadabir on 15/3/18.
 */

interface GitHubService {

    @GET("users/{user}/repos")
    fun getStarredRepositories(@Path("user") userName: String): Observable<List<GitHubRepo>>

}
