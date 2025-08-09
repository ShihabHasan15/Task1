package com.devsyncit.task1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(var context: Context, var recordList: List<AnswerEntity>): RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var questionTxt = itemView.findViewById<TextView>(R.id.question_txt)
        var answerTxt = itemView.findViewById<TextView>(R.id.answer_txt)
        var txt_layout = itemView.findViewById<LinearLayout>(R.id.txt_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {

        val recordView = LayoutInflater.from(context).inflate(R.layout.record_item_design, parent, false)

        return RecordViewHolder(recordView)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        var record = recordList[position]

        var question = record.question

        var answer = record.answer

        holder.questionTxt.text = question

        holder.answerTxt.text = answer

    }

    override fun getItemCount(): Int {

        return recordList.size
    }

    override fun getItemViewType(position: Int): Int {

        val record = recordList.get(position)


        return 0
    }

}