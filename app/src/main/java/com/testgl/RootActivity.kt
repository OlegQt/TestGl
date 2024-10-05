package com.testgl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.testgl.databinding.ActivityRootBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    private var globalCount: Int = 100
    private var countingEnabled = true
    private var countJob: Job? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()

    }

    override fun onPause() {
        super.onPause()
        countJob?.cancel()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    private fun setUpUi() {
        supportActionBar?.apply {
            title = "Testing application"
            subtitle = "timer"

            show()
        }

        startTimer()
    }

    private fun startTimer() {
        val exceptionHandler =
            CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->
                binding.txt.text = throwable.message.toString()
            }
        countJob = MainScope().launch(exceptionHandler) {
            while (true) {
                if (countingEnabled) {
                    delay(10)
                    globalCount++
                    supportActionBar?.subtitle = globalCount.toString()
                }
            }
        }
    }
}