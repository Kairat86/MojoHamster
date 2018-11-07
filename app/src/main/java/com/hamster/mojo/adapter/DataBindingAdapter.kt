package com.hamster.mojo.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.FragmentManager
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hamster.mojo.BR
import com.hamster.mojo.fragment.HamsterDetailsFragment

abstract class DataBindingAdapter<T>(
    private val fm: FragmentManager,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    ListAdapter<T, DataBindingAdapter.DataBindingViewHolder<T>>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(fm, binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) = holder.bind(getItem(position))

    class DataBindingViewHolder<T>(
        private val fm: FragmentManager,
        private val binding: ViewDataBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.detailsFragment, HamsterDetailsFragment())
            binding.setVariable(BR.manager, fm)
            binding.executePendingBindings()
        }
    }
}