package com.hamster.mojo.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.hamster.mojo.R
import com.hamster.mojo.adapter.HamsterAdapter
import com.hamster.mojo.db.HamsterDB
import com.hamster.mojo.model.HamsterViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var db: HamsterDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvHamsters.adapter = HamsterAdapter(supportFragmentManager)
        db = Room.databaseBuilder(
            applicationContext,
            HamsterDB::class.java, "hamster_db"
        ).build()
        loadData()
    }

    @SuppressLint("CheckResult")
    private fun loadData() {
        if (!isNetworkConnected()) {
            loadFromDB().subscribe {
                Log.i(TAG, "onCreate=>$it")
                (rvHamsters.adapter as HamsterAdapter).submitList(it.toMutableList())
            }
        } else {
            loadFromNetwork()
        }
    }

    private fun loadFromDB() = Observable.fromCallable { db.hamsterDao().getAll() }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    private fun loadFromNetwork() {
        val model = ViewModelProviders.of(this).get(HamsterViewModel::class.java)
        model.getHamsterLiveData().observe(this, Observer {
            (rvHamsters.adapter as HamsterAdapter).submitList(it?.toMutableList())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        (menu?.findItem(R.id.action_search)?.actionView as SearchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = true

                override fun onQueryTextChange(newText: String): Boolean {
                    val hamsterAdapter = rvHamsters.adapter as HamsterAdapter
                    hamsterAdapter.submitList(if (newText.isBlank()) hamsterAdapter.list
                    else hamsterAdapter.list?.filter { it.title.contains(newText) }
                        ?.toMutableList())
                    return true
                }
            })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        saveToDB()
        super.onStop()
    }

    private fun saveToDB() = Observable.fromCallable {
        val hamsters = (rvHamsters.adapter as HamsterAdapter).list
        Log.i(TAG, "onStop=>$hamsters")
        db.hamsterDao().insertAll(hamsters)
    }.subscribeOn(Schedulers.io()).subscribe()

    private fun isNetworkConnected() =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null

}
