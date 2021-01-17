package com.buchi.question.presentation.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buchi.question.R

class QuestionDetailFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionDetailFragment()
    }

    private lateinit var viewModel: QuestionDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}