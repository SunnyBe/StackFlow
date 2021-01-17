package com.buchi.stackflow.auth.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.buchi.stackflow.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel.error.observe(
            this,
            { err ->
                showErrorDialog(err)
            }
        )

        authViewModel.showProgress.observe(
            this,
            { progress ->
                progress?.let { showProgress ->
                    showProgressDialog(showProgress)
                }
            }
        )
    }

    private fun showProgressDialog(showProgress: Boolean) {
        if (showProgress) {
            Toast.makeText(this, "Show progress", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Hide progress", Toast.LENGTH_LONG).show()
        }
    }

    private fun showErrorDialog(err: Throwable?) {
        err?.let {
            Toast.makeText(this, "${err.message}", Toast.LENGTH_LONG).show()
        }
    }
}
