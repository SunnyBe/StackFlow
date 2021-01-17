package com.buchi.stackflow.auth.presentation.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.buchi.core.utils.ViewModelFactory
import com.buchi.stackflow.auth.presentation.AuthViewModel
import com.buchi.stackflow.databinding.FragmentSigninBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SignInViewModel by viewModels { viewModelFactory }
    private val activityViewModel: AuthViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            val username = binding.usernameEntry.text.toString()
            val password = binding.passwordEntry.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                viewModel.signIn(username, password)
            } else {
                activityViewModel.processError(Throwable("Empty entries"))
            }
        }

        viewModel.dataState.observe(
            viewLifecycleOwner,
            { viewState ->
                viewState.loading?.let {
                    // show loading if loading state is true
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                    activityViewModel.toShowProgress(it)
                }

                viewState.error?.let {
                    activityViewModel.processError(it)
                }
                viewState.data?.let { eventView ->
                    eventView.getContentIfNotHandled()?.let {
                        viewModel.setViewState(viewState = it)
                    }
                }
            }
        )

        viewModel.viewState.observe(
            viewLifecycleOwner,
            { viewState ->
                viewState.signInResponse?.let { response ->
                    Log.d(javaClass.simpleName, "Unprocessed Response: $response")
                    if (response.error == true) {
                        activityViewModel.processError(Throwable(response.message))
                    } else {
                        Toast.makeText(requireContext(), "Response: ${response.message}", Toast.LENGTH_SHORT).show()
                        Log.d(javaClass.simpleName, "Fetched signed in object $response")
                        // Todo Proceed to list of questions
                    }
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
