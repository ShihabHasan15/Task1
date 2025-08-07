package com.devsyncit.task1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(var recordList: List<HashMap<String, Any>>, var context: Context): RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var questionTxt = itemView.findViewById<TextView>(R.id.question_txt)
        var txt_layout = itemView.findViewById<LinearLayout>(R.id.txt_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {

        val recordView = LayoutInflater.from(context).inflate(R.layout.record_item_design, parent, false)

        return RecordViewHolder(recordView)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        var record = recordList[position]

        var question = record.get("question") as String

        var answers = record.get("answer") as MutableList<String>

        holder.questionTxt.text = question

        for (answer in answers){
            var answerTextView = TextView(context)
            answerTextView.text = answer
            answerTextView.setTextColor(Color.LTGRAY)
            answerTextView.setTextSize(14f)
            holder.txt_layout.addView(answerTextView)
        }


    }

    override fun getItemCount(): Int {

        return recordList.size
    }

}