package com.buchi.question.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.buchi.question.databinding.ActivityQuestionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityQuestionBinding
    private val queViewModel: QuestionViewModel by viewModels { viewModelFactory }

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