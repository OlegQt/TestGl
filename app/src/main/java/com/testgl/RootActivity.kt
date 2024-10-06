package com.testgl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.testgl.databinding.ActivityRootBinding
import kotlinx.coroutines.launch

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    private val viewModel: RootVm by viewModels()


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
        viewModel.pauseActivity()
    }

    override fun onResume() {
        super.onResume()

        Snackbar.make(binding.root, "Application was paused ".plus(viewModel.getPauseTime()), Snackbar.LENGTH_INDEFINITE)
            .setAction("Ok") {}
            .show()
    }

    private fun setUpUi() {
        supportActionBar?.apply {
            title = "Testing application"
            subtitle = "timer"

            show()
        }

    }

    private fun setUpBehaviour() {
        binding.btnAction.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("ApplicationGL")
                .setMessage("Action completed")
                .setNeutralButton("OK",null)
                .show()
        }
    }

    private fun setUpObservation() {
        lifecycleScope.launch {
            viewModel.date.observe(this@RootActivity) { date ->
                supportActionBar?.subtitle = "Application active $date"
            }
        }
    }


}