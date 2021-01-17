package com.buchi.question.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buchi.core.utils.ViewModelFactory
import com.buchi.question.R
import com.buchi.question.databinding.QuestionDashboardFragmentBinding
import com.buchi.question.model.QuestionEntity
import com.buchi.question.presentation.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionDashboardFragment : Fragment() {
    private var _binding: QuestionDashboardFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: QuestionDashboardViewModel by viewModels { viewModelFactory }
    private val activityViewModel: QuestionViewModel by activityViewModels { viewModelFactory }

    private val listAdapter = QuestionListAdapter { clickedItem ->
        navigateToItemDetail(clickedItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuestionDashboardFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAllQuestions()
        binding.questionsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        observeLiveDatas()
    }

    private fun observeLiveDatas() {
        viewModel.dataState.observe(
            viewLifecycleOwner,
            { viewState ->
                viewState.loading?.let {
                    // show loading if loading state is true
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
                viewState.allQuestions?.let { response ->
                    Log.d(javaClass.simpleName, "Unprocessed Response: $response")
                    if (response.error == true) {
                        activityViewModel.processError(Throwable(response.message))
                    } else {
                        Log.d(
                            javaClass.simpleName,
                            "Updating list with questions: ${response.data}"
                        )
                        listAdapter.submitList(response.data as MutableList<QuestionEntity.Question>)
                    }
                }
            }
        )
    }

    private fun navigateToItemDetail(clickedItem: QuestionEntity.Question) {
        val navBundle = Bundle()
        navBundle.putParcelable("question_item", clickedItem)
        findNavController().navigate(
            R.id.action_questionDashboardFragment_to_questionDetailFragment,
            navBundle
        )
    }

}