package com.testgl.presentation.viewmodels

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testgl.presentation.model.SoundType

class MainViewModel : ViewModel() {
    val dialogMsgString = MutableLiveData<String>()

    private var soundPool: SoundPool? = null

    private val soundMap = mutableMapOf<SoundType, Int>()

    val playSound: (SoundType) -> Unit = { playSound(it) }

    init {
        createSoundPool()
    }

    fun showMessageDlg(newMessage: String) {
        dialogMsgString.value = newMessage
    }

    private fun createSoundPool() {
        soundPool = SoundPool.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            .setMaxStreams(5)
            .build()
    }

    fun getSoundPool() = soundPool

    fun loadSound(type: SoundType, idSound: Int) {
        soundMap[type] = idSound
    }

    fun releaseSoundPool() {
        soundPool?.release()
    }

    private fun playSound(soundType: SoundType) {
        if (soundMap.isEmpty()) return

        soundPool?.let { player ->
            soundMap[soundType]?.let {
                player.stop(it)
                player.play(it, 1.0f, 1.0f, 0, 0, 0.0f)
            }
        }
    }
}