package org.mathieu.cleanrmapi.common

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.R
import org.mathieu.cleanrmapi.common.Interfaces.SoundPlayer

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    override fun playSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, R.raw.sfx_jump_07_80241)
        mediaPlayer?.start()
    }
}