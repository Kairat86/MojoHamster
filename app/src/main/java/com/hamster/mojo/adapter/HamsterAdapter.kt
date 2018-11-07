package com.hamster.mojo.adapter

import android.support.v4.app.FragmentManager
import android.support.v7.util.DiffUtil
import com.hamster.mojo.R
import com.hamster.mojo.model.Hamster

class HamsterAdapter(supportFragmentManager: FragmentManager) :
    DataBindingAdapter<Hamster>(supportFragmentManager, DiffCallback()) {

    var list: MutableList<Hamster>? = null

    class DiffCallback : DiffUtil.ItemCallback<Hamster>() {

        override fun areItemsTheSame(p0: Hamster, p1: Hamster) = p0 == p1

        override fun areContentsTheSame(p0: Hamster, p1: Hamster) = areItemsTheSame(p0, p1)
    }

    override fun getItemViewType(position: Int) = R.layout.item_hamster

    override fun submitList(list: MutableList<Hamster>?) {
        if (this.list == null) this.list = list
        super.submitList(list)
    }
}