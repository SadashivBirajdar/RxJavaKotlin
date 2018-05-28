package com.example.rxjavaexample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import io.reactivex.annotations.Nullable

/**
 * Created by emb-sadabir on 28/5/18.
 */

class GitHubRepoAdapter : RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoViewHolder>() {

    private val gitHubRepos = ArrayList<GitHubRepo>()

    override fun getItemCount(): Int {
        return gitHubRepos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_github_repo, parent, false)
        return GitHubRepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GitHubRepoViewHolder, position: Int) {
        val gitHubRepo = gitHubRepos[position]
        holder.textRepoName.text = gitHubRepo.name
        holder.textRepoUrl.text = gitHubRepo.html_url
        holder.textLanguage.text = "Language: " + gitHubRepo.language
        if(position == gitHubRepos.size - 1){
            holder.divider.visibility = View.GONE
        } else {
            holder.divider.visibility = View.VISIBLE
        }
    }

    fun setGitHubRepos(@Nullable repos: List<GitHubRepo>?) {
        gitHubRepos.clear()
        if (repos == null) {
            notifyDataSetChanged()
            return
        }
        gitHubRepos.clear()
        gitHubRepos.addAll(repos)
        notifyDataSetChanged()
    }

    inner class GitHubRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textRepoName: TextView = view.findViewById(R.id.text_repo_name)
        val textRepoUrl: TextView = view.findViewById(R.id.text_repo_url)
        val textLanguage: TextView = view.findViewById(R.id.text_language)
        val divider : View = view.findViewById(R.id.divider)
    }
}

