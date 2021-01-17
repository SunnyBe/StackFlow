package com.buchi.question.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buchi.core.utils.ViewModelFactory
import com.buchi.question.databinding.DialogAnswerBinding
import com.buchi.question.databinding.QuestionDetailFragmentBinding
import com.buchi.question.model.QuestionEntity
import com.buchi.question.presentation.QuestionViewModel
import com.buchi.question.presentation.dashboard.QuestionListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class QuestionDetailFragment : Fragment() {
    private var _binding: QuestionDetailFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: QuestionDetailViewModel by viewModels { viewModelFactory }
    private val activityViewModel: QuestionViewModel by activityViewModels { viewModelFactory }

    private var questionItem: QuestionEntity.Question? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QuestionDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionItem = arguments?.getParcelable("question_item")
        initViewForItem(questionItem)
        observeLiveDatas()
        Log.d(javaClass.simpleName, "Question item selected: $questionItem")

        binding.questionStats.upvoteValue.setOnClickListener {
            questionItem?.let {
                viewModel.upVoteQuestion(it)
            }
        }

        binding.textView2.setOnClickListener {
            Log.d(javaClass.simpleName, "Click to test answering question")
            takeAnswerInDialog(questionItem)
        }
    }

    private fun takeAnswerInDialog(questionItem: QuestionEntity.Question?) {
        questionItem?.let {
            val dialogView = DialogAnswerBinding.inflate(layoutInflater, binding.root, false)
            val dialogFragment = MaterialAlertDialogBuilder(requireContext()).apply {
                setView(dialogView.root)
//            setCancelable(false)
            }.show()
            // Dismiss when user clicks answer button
            dialogView.submitAnswer.setOnClickListener {
                val answer = dialogView.answerEntry.text.toString()
                viewModel.answerQuestion(questionItem, answer)
                dialogFragment.dismiss()
            }
        }
    }

    private fun initViewForItem(questionItem: QuestionEntity.Question?) {
        if (questionItem != null) {
            binding.questionContent.text = questionItem.question
            binding.questionStats.upvoteValue.text = questionItem.votes.toString()
        }
    }

    private fun observeLiveDatas() {
        viewModel.dataState.observe(
            viewLifecycleOwner,
            { viewState ->
                viewState.loading?.let {
                    // show loading if loading state is true
                    if (it) {
                        binding.answerFetchProgress.visibility = View.VISIBLE
                    } else {
                        binding.answerFetchProgress.visibility = View.GONE
                    }
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
                viewState.answeredQuestion?.let { response ->
                    Log.d(javaClass.simpleName, "Unprocessed Response: $response")
                    if (response.error == true) {
                        activityViewModel.processError(Throwable(response.message))
                    } else {
                        Log.d(
                            javaClass.simpleName,
                            "Updating list with questions: ${response.data}"
                        )
                        // Todo Update the answers list with the update
                    }
                }

                viewState.upVoted?.let { upvoted ->
                    Log.d(javaClass.simpleName, "Unprocessed Response: $upvoted")
                    if (upvoted.error == true) {
                        activityViewModel.processError(Throwable(upvoted.message))
                    } else {
                        Log.d(
                            javaClass.simpleName,
                            "Updating list with questions: ${upvoted.data}"
                        )
                        binding.answersList.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            // Todo create and adapter and upate the list view
                        }
                    }
                }
            }
        )
    }

}