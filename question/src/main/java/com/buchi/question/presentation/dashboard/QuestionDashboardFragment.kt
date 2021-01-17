package com.buchi.question.presentation.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buchi.question.R

class QuestionDashboardFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionDashboardFragment()
    }

    private lateinit var viewModel: QuestionDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_dashboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionDashboardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}