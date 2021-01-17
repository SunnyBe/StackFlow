package com.buchi.question.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buchi.question.databinding.ItemQuestionBinding
import com.buchi.question.model.QuestionEntity
import com.buchi.question.utils.QuestionViewHolder

class QuestionListAdapter(
    private val itemList: MutableList<QuestionEntity.Question> = mutableListOf(),
    val onClickAction: (QuestionEntity.Question) -> Unit
) : RecyclerView.Adapter<QuestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemBinding =
            ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, onClickAction)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun submitList(newList: MutableList<QuestionEntity.Question>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }


}