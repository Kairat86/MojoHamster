package com.hamster.mojo.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.hamster.mojo.manager.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HamsterViewModel : ViewModel() {

    companion object {
        private val TAG = HamsterViewModel::class.java.simpleName
    }

    private lateinit var hamsterLiveData: MutableLiveData<List<Hamster>>

    fun getHamsterLiveData(): MutableLiveData<List<Hamster>> {
        if (!::hamsterLiveData.isInitialized) {
            hamsterLiveData = MutableLiveData()
            loadHamsters()
        }
        return hamsterLiveData
    }

    private fun loadHamsters() {
        ApiManager().getHamsters(object : Callback<List<Hamster>> {
            override fun onResponse(call: Call<List<Hamster>>, response: Response<List<Hamster>>) {
                Log.i(TAG, "response=>" + response.body())
                hamsterLiveData.value = response.body()
            }

            override fun onFailure(call: Call<List<Hamster>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}