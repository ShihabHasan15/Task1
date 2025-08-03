package com.devsyncit.task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MultipleChoiceAdapter(var question: List<Question>, var options: List<Option>): RecyclerView.Adapter<MultipleChoiceAdapter.MultipleChoiceViewHolder>(){

    inner class MultipleChoiceViewHolder(itemView: View) : ViewHolder(itemView){

        var question: TextView = itemView.findViewById(R.id.question)
        var choice_list: ListView = itemView.findViewById(R.id.choice_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleChoiceViewHolder {

        var multiple_choice_question_view = LayoutInflater.from(parent.context).inflate(R.layout.multiple_choice_question_design,
            parent, false)

        return MultipleChoiceViewHolder(multiple_choice_question_view)
    }

    override fun getItemCount(): Int {

        return question.size
    }

    override fun onBindViewHolder(holder: MultipleChoiceViewHolder, position: Int) {

        var question = question[position]
        var choices = options[position]



    }

}