package com.bobrovskii.authorization.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.bobrovskii.authorization.R
import com.bobrovskii.authorization.databinding.FragmentAuthBinding
import com.bobrovskii.authorization.presentation.AuthAction
import com.bobrovskii.authorization.presentation.AuthState
import com.bobrovskii.authorization.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

	private var _binding: FragmentAuthBinding? = null
	private val binding get() = _binding!!

	private val viewModel: AuthViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentAuthBinding.bind(view)
		initListeners()
		initObservers()
	}

	private fun initListeners() {
		with(binding) {
			signIn.setOnClickListener {
				viewModel.onSignButtonPressed(tokenEditText.text.toString())
			}
			tokenEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

				override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
					viewModel.onTextChanged()
				}

				override fun afterTextChanged(p0: Editable?) = Unit
			})
		}
	}

	private fun initObservers() {
		lifecycleScope.launch {
			viewModel.actions.collect { handleAction(it) }
		}
		viewModel.state.onEach { state ->
			with(binding) {
				signInText.visibility = if (state is AuthState.Loading) View.GONE else View.VISIBLE
				progressBar.visibility = if (state is AuthState.Loading) View.VISIBLE else View.GONE
				personalToken.error = if (state is AuthState.InvalidInput) state.reason else ""
			}
		}.launchIn(viewModel.viewModelScope)
	}

	private fun handleAction(action: AuthAction) {
		when (action) {
			is AuthAction.RouteToMain -> viewModel.routeToMain()

			is AuthAction.ShowError   -> {
				val message = SpannableString(action.message)
				message.setSpan(
					ForegroundColorSpan(Color.WHITE),
					0,
					message.length,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
				)
				context?.let {
					AlertDialog
						.Builder(it)
						.setTitle(getString(R.string.error_dialog_title))
						.setMessage(message)
						.setNeutralButton("Ok") { _, _ -> }
						.show()
				}
			}
		}
	}
}