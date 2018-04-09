package com.example.rxjavaexample

/**
 * Created by emb-sadabir on 15/3/18.
 */

data class GitHubRepo (var id: Int,
    var name: String,
    var html_url: String,
    var description: String,
    var language: String,
    var stargazersCount: Int)