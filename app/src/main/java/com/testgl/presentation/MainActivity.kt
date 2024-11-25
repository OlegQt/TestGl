package com.testgl.presentation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.testgl.databinding.ActivityMainBinding
import com.testgl.presentation.fragments.RootFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw Exception("null binding NPE")

    private var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpUi()
    }

    private fun setUpUi() {
        supportActionBar?.let {
            it.title = "GUI test"
            it.subtitle = "application"
        }

        supportFragmentManager.commit {
            replace(binding.rootFragmentHolder.id, RootFragment() )
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetLayout).also {
            it.peekHeight = 200
            it.isHideable = true
            binding.bottomSheetLayout.alpha = 0.5f
            it.state =BottomSheetBehavior.STATE_HIDDEN
        }


    }
}