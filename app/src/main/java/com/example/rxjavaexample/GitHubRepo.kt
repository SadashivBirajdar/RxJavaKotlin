package com.example.rxjavaexample

data class GitHubRepo (var id: Int,
    var name: String,
    var html_url: String,
    var description: String,
    var language: String,
    var stargazersCount: Int)