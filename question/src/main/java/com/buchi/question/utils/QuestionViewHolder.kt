package com.buchi.question.utils

import androidx.recyclerview.widget.RecyclerView
import com.buchi.question.databinding.ItemQuestionBinding
import com.buchi.question.model.QuestionEntity

class QuestionViewHolder(private val itemBinding: ItemQuestionBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: QuestionEntity.Question, onClickAction: (QuestionEntity.Question)-> Unit) {
        itemBinding.itemQuestionTitle.text = item.user
        itemBinding.itemQuestionContent.text = item.question

        itemBinding.root.setOnClickListener {
            onClickAction(item)
        }
    }
}