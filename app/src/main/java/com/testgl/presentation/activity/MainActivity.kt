package com.testgl.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.testgl.R
import com.testgl.presentation.model.SoundType
import com.testgl.presentation.screens.ShowScreenContent
import com.testgl.presentation.theme.AppTheme
import com.testgl.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadSounds()

        setContent {
            AppTheme(dynamicColor = false) {
                ShowScreenContent(viewModel.playSound)
            }
        }

        setUpObservation()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releaseSoundPool()
    }

    private fun loadSounds() {
        // Strong BIP sound
        viewModel.getSoundPool()?.load(this, R.raw.bip_sound, 0)?.let {
            viewModel.loadSound(SoundType.Bip, it)
        }


    }

    private fun setUpObservation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dialogMsgString.observe(this@MainActivity) {
                    showDialogWnd(it)
                }
            }
        }
    }

    private fun showDialogWnd(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(message)
            .setPositiveButton("OK", null)
            .show()
    }
}


