package org.mathieu.cleanrmapi.common

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.R
import org.mathieu.cleanrmapi.common.Interfaces.SoundPlayer

/**
 * This is the Android-specific implementation of the SoundPlayer interface.
 *
 * I’m using Android’s MediaPlayer to play a short MP3 file located in res/raw.
 * I made sure to release the previous player before creating a new one, to avoid overlapping sounds.
 *
 * I decided not to use `expect/actual` here because I wanted to keep the implementation injectable.
 * This way, I can register it in a Koin module and let my shared ViewModels use it
 * without being tightly coupled to the platform code.
 */
class AndroidSoundPlayer(private val context: Context) : SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    /**
     * Plays the sound effect using MediaPlayer.
     * If a sound was already playing, I release it first to avoid issues.
     */
    override fun playSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, R.raw.sfx_jump_07_80241)
        mediaPlayer?.start()
    }
}