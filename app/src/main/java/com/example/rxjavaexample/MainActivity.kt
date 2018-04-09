package com.example.rxjavaexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by emb-sadabir on 15/3/18.
 */

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName!!
    }

    private val adapter = GitHubRepoAdapter()

    @BindView(R.id.edit_text_username)
    private var editTextUsername: EditText? = null
    @BindView(R.id.button_search)
    private var buttonSearch: Button? = null
    @BindView(R.id.list_view_repos)
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter

        buttonSearch?.setOnClickListener {
            val username = editTextUsername?.text.toString()
            if (!TextUtils.isEmpty(username)) {
                getStarredRepos(username)
            }
        }
    }

    private fun getStarredRepos(username: String) {
        RestClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubRepo>> {

                    override fun onError(e: Throwable) {
                        adapter.setGitHubRepos(null)
                        e.printStackTrace()
                        Log.d(TAG, "In onError()")
                    }


                    override fun onComplete() {
                        Log.d(TAG, "In onCompleted()")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(gitHubRepos: List<GitHubRepo>) {
                        Log.d(TAG, "In onNext()")
                        adapter.setGitHubRepos(gitHubRepos)
                    }
                })
    }
}
