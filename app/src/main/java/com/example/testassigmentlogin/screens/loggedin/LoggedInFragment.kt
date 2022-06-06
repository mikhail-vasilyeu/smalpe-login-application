package com.example.testassigmentlogin.screens.loggedin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testassigmentlogin.R
import com.example.testassigmentlogin.databinding.FragmentLoggedInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoggedInFragment : Fragment() {

    private val viewModel: LoggedInViewModel by viewModels()

    private var _binding: FragmentLoggedInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoggedInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logOut -> {
                    viewModel.logOutClicked()
                    true
                }
                else -> false
            }
        }

        lifecycleScope.launch {
            viewModel.email.onEach {
                Timber.d(it)
                binding.welcomeTextView.text = resources.getString(R.string.logged_in_welcome, it)
            }.launchIn(this)
        }
    }
}