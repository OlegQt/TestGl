package com.testgl.presentation

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.snackbar.Snackbar
import com.testgl.R
import com.testgl.databinding.ActivityMainBinding
import com.testgl.presentation.fragments.RootFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw Exception("null binding NPE")

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    private var countPush = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpUi()
    }

    private fun expandBottomSheet(): Boolean {
        bottomSheetBehavior?.let {
            it.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            return true
        }
        return false
    }

    private fun showSnack(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("ok") { }
            .show()
    }

    private fun setUpUi() {
        supportActionBar?.let {
            it.title = "GUI test"
            it.subtitle = "application"
        }

        supportFragmentManager.commit {
            replace(binding.rootFragmentHolder.id, RootFragment())
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout).also {
            it.peekHeight = 200
            it.isHideable = true
            binding.bottomSheetLayout.alpha = 0.8f
            binding.bottomSheetLayout.isClickable = true
            it.state = BottomSheetBehavior.STATE_HIDDEN

            it.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> showSnack("STATE_HIDDEN")
                        BottomSheetBehavior.STATE_COLLAPSED -> showSnack("STATE_COLLAPSED")
                        BottomSheetBehavior.STATE_EXPANDED -> showSnack("STATE_EXPANDED")
                        BottomSheetBehavior.STATE_DRAGGING -> showSnack("STATE_DRAGGING")
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> showSnack("STATE_DRAGGING")
                        else -> {}
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.sliderOffset.text = slideOffset.toString()
                }
            })
        }

        binding.rootNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.root_page -> {
                    countPush++
                    Toast.makeText(this, countPush.toString(), Toast.LENGTH_SHORT).show()
                }

                R.id.info_page -> {
                    countPush++
                    Toast.makeText(this, countPush.toString(), Toast.LENGTH_SHORT).show()
                }

                R.id.console_page -> expandBottomSheet()
            }
            true
        }
    }
}