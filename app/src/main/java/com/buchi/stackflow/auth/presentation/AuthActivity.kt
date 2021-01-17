package com.buchi.stackflow.auth.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.buchi.stackflow.databinding.ActivityAuthBinding
import com.buchi.stackflow.databinding.DialogProcessFailedBinding
import com.buchi.stackflow.databinding.ProgressDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@FlowPreview
class AuthActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityAuthBinding

    var progressDialog: AlertDialog? = null

    private val authViewModel: AuthViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeActionCommand()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun observeActionCommand() {
        lifecycleScope.launchWhenCreated {
            authViewModel.error.onEach { err ->
                showErrorDialog(err)
            }.launchIn(lifecycleScope)

            authViewModel.showProgress.onEach { progress ->
                showProgressDialog(progress)
            }.launchIn(lifecycleScope)
        }
    }

    private fun showProgressDialog(showProgress: Boolean) {
        if (showProgress) {
            progressDialog = progressDialog().show()
        } else {
            if (progressDialog?.isShowing == true) progressDialog?.dismiss()
        }
    }

    private fun showErrorDialog(err: Throwable?) {
        err?.let {
            inflateErrorDialog(err.message)
        }
    }

    // Inflate the progress dialog
    private fun progressDialog(): AlertDialog.Builder {
        val dialogView = ProgressDialogBinding.inflate(layoutInflater).root
        return MaterialAlertDialogBuilder(this)
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .setView(dialogView)
            .setCancelable(false)
    }

    private fun inflateErrorDialog(msg: String?) {
        val dialogBinding = DialogProcessFailedBinding.inflate(layoutInflater)
        val view = dialogBinding.root
        dialogBinding.failureMsg.text = msg
        val errorDialog = MaterialAlertDialogBuilder(this)
            .setView(view)
            .setCancelable(false)
            .show()
        dialogBinding.okButton.setOnClickListener {
            if (errorDialog.isShowing) errorDialog.dismiss()
        }
    }
}
