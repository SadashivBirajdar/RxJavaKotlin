package com.example.rxjavaexample

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName!!
    }

    private val gitHubRepoAdapter = GitHubRepoAdapter()
    private lateinit var dialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = ProgressDialog(this)
        dialog.setMessage("Please wait")
        dialog.setTitle("Loading")
        dialog.setCancelable(false)

        list_view_repos.layoutManager = LinearLayoutManager(this)
        list_view_repos.adapter = gitHubRepoAdapter

        button_search.setOnClickListener{
            val username = edit_text_username.text.toString()
            if (!TextUtils.isEmpty(username)) {
                dialog.show()
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
                        dialog.dismiss()
                        gitHubRepoAdapter.setGitHubRepos(null)
                        e.printStackTrace()
                        Log.d(TAG, "In onError()")
                        Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }


                    override fun onComplete() {
                        Log.d(TAG, "In onCompleted()")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(gitHubRepos: List<GitHubRepo>) {
                        dialog.dismiss()
                        Log.d(TAG, "In onNext()")
                        gitHubRepoAdapter.setGitHubRepos(gitHubRepos)
                    }
                })
    }
}
