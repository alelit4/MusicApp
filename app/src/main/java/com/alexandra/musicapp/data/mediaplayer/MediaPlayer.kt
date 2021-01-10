package com.alexandra.musicapp.data.mediaplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri

object MediaPlayer {
    private var mediaPlayer: MediaPlayer = MediaPlayer()

    fun play(context: Context, songUrl: String) {
        stop()
        val uri: Uri = Uri.parse(songUrl)
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(context, uri)
            prepare()
            start()
        }
    }

    fun stop(){
        if (mediaPlayer.isPlaying)
            mediaPlayer.stop()
    }
}

