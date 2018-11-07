package com.hamster.mojo.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamster.mojo.BR
import com.hamster.mojo.R
import com.hamster.mojo.model.Hamster
import com.hamster.mojo.presenter.Presenter

class HamsterDetailsFragment : DialogFragment() {
    companion object {
        private val TAG = HamsterDetailsFragment::class.java.simpleName
        const val HAMSTER = "hamster"
    }

    fun newInstance(hamster: Hamster): HamsterDetailsFragment {
        val frag = HamsterDetailsFragment()
        val args = Bundle()
        args.putSerializable(HAMSTER, hamster)
        frag.arguments = args
        Log.i(TAG, "newInstance")
        return frag
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.fragment_details_hamster, container, false)
        binding.setVariable(BR.item, arguments!!.getSerializable(HAMSTER))
        binding.setVariable(BR.presenter, Presenter())
        Log.i(TAG, "onCreateView")
        return binding.root
    }
}