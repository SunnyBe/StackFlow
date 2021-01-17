package com.buchi.question.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.buchi.question.databinding.ActivityQuestionBinding
import com.buchi.question.databinding.DialogProcessFailedBinding
import com.buchi.question.databinding.ProgressDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityQuestionBinding
    private val queViewModel: QuestionViewModel by viewModels { viewModelFactory }

    var progressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        queViewModel.error.observe(
            this,
            { err ->
                showErrorDialog(err)
            }
        )

        queViewModel.showProgress.observe(
            this,
            { progress ->
                progress?.let { showProgress ->
                    showProgressDialog(showProgress)
                }
            }
        )
    }


//    private fun showProgressDialog(showProgress: Boolean) {
//        if (showProgress) {
//            Toast.makeText(this, "Show progress", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "Hide progress", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun showErrorDialog(err: Throwable?) {
//        err?.let {
//            Toast.makeText(this, "${err.message}", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun showProgressDialog(showProgress: Boolean) {
//        if (showProgress) {
//            progressDialog = progressDialog().show()
//        } else {
//            if (progressDialog?.isShowing == true) progressDialog?.dismiss()
//        }
//    }

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