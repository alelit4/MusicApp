package com.alexandra.musicapp.data.mediaplayer

import android.content.Context
import android.content.Intent
import android.widget.Toast

object ShareHandler {
    fun shareSong(trackName: String, context: Context) {
        val chooser = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, trackName)
            putExtra(Intent.EXTRA_TITLE, "I really love this track:")
            type = "text/plain"
        }, null)
        context.startActivity(chooser)
    }

    fun songReceived(intent: Intent, context: Context) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Toast.makeText(context, "Someone love -> $it", Toast.LENGTH_LONG).show()
        }
    }
}