package com.testgl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.testgl.databinding.ActivityRootBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    private val viewModel:RootVm by viewModels()

    private var globalCount: Int = 0
    private var countingEnabled = true
    private var countJob: Job? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setUpBehaviour()

        setUpObservation()
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

    private fun setUpBehaviour(){
        binding.btnAction.setOnClickListener {
            countingEnabled=!countingEnabled
        }
    }

    private fun setUpObservation(){
        lifecycleScope.launch {
            viewModel.date.observe(this@RootActivity){
                binding.txt.text = it
            }
        }
    }

    private fun startTimer() {
        val exceptionHandler =
            CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->
                binding.txt.text = throwable.message.toString()
            }
        countJob = MainScope().launch(exceptionHandler) {
            while (true) {
                delay(COUNTING_DELAY_MILLS)
                if (countingEnabled) {
                    globalCount++
                    supportActionBar?.subtitle = globalCount.toString()
                }
            }
        }
    }

    companion object{
        const val COUNTING_DELAY_MILLS = 10L
    }
}