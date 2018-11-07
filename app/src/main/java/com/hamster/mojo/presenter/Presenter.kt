package com.hamster.mojo.presenter

import android.content.Context
import android.content.Intent
import com.hamster.mojo.R
import com.hamster.mojo.model.Hamster


class Presenter {
    companion object {
        private val TAG = Presenter::class.java.simpleName
    }

    fun share(ctx: Context, item: Hamster) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, ctx.getString(R.string.share_hamster))
        i.putExtra(Intent.EXTRA_TEXT, item.image)
        ctx.startActivity(i)
    }
}