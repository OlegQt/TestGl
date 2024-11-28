package com.testgl.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.snackbar.Snackbar
import com.testgl.R
import com.testgl.databinding.ActivityMainBinding
import com.testgl.presentation.fragments.RootFragment
import com.testgl.presentation.vm.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw Exception("null binding NPE")

    private val viewModel: MainViewModel by viewModels()

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpUi()
        setUpObservers()
    }

    private fun changeAppBarColorBackground(level: Float, expandedBarColor: ColorDrawable) {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        val colorDrawable = typedValue.data

        val rColor = colorDrawable.red
        val gColor = colorDrawable.green
        val bColor = colorDrawable.blue

        if (level > 0) {
            val redDelta = (expandedBarColor.color.red - rColor) * level
            val greenDelta = (expandedBarColor.color.green - gColor) * level
            val blueDelta = (expandedBarColor.color.blue - bColor) * level

            val newColor = Color.rgb(
                (rColor + redDelta).toInt(),
                (gColor + greenDelta).toInt(),
                (bColor + blueDelta).toInt()
            )

            supportActionBar?.setBackgroundDrawable(ColorDrawable(newColor))
        } else {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(colorDrawable))
        }
    }

    private fun expandBottomSheet(): Boolean {
        bottomSheetBehavior?.let {
            if (it.state != STATE_HIDDEN) it.state = STATE_HIDDEN
            else it.state = STATE_HALF_EXPANDED

            return true
        }
        return false
    }

    private fun showSnack(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("ok") { }
            .setAnchorView(binding.rootNavView)
            .show()
    }

    private fun setUpUi() {
        supportActionBar?.let {
            it.title = "GUI test"
            it.subtitle = "application"
            it.setBackgroundDrawable(binding.rootNavView.background)
        }

        supportFragmentManager.commit {
            replace(binding.rootFragmentHolder.id, RootFragment())
        }

        binding.bottomSheetLayout.alpha = 0.8f
        binding.bottomSheetLayout.isClickable = true

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout).also {
            it.peekHeight = 0
            it.isHideable = true
            it.skipCollapsed = true
            it.state = STATE_HIDDEN

            it.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        STATE_HIDDEN -> {
                            //TODO настроить переход на предыдущий фрагмент
                            //binding.rootNavView.selectedItemId = R.id.root_page
                        }
                        else -> {}
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.sliderOffset.text = slideOffset.toString()
                    changeAppBarColorBackground(slideOffset, ColorDrawable(Color.RED))
                }
            })
        }

        binding.rootNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.root_page -> {}
                R.id.info_page -> viewModel.changeData("InfoPage taped")
                R.id.console_page -> expandBottomSheet()
            }
            true
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.msgStr.observe(this@MainActivity) {
                    showSnack(it)
                }

                viewModel.elapsedTime.observe(this@MainActivity) {
                    supportActionBar?.subtitle = "Elapsed time = ${it.toString()}"
                }
            }
        }
    }
}