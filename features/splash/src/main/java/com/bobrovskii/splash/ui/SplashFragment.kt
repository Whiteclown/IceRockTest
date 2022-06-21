package com.bobrovskii.splash.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bobrovskii.splash.R
import com.bobrovskii.splash.databinding.FragmentSplashBinding
import com.bobrovskii.splash.presentation.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

	private var _binding: FragmentSplashBinding? = null
	private val binding get() = _binding!!

	private val viewModel: SplashViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentSplashBinding.bind(view)
		with(binding.icCompany) {
			alpha = 0f
			animate().setDuration(500).alpha(1f).withEndAction {
				viewModel.routeFromSplash()
			}
		}
	}
}