package com.devsyncit.task1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MultipleChoiceAdapter(var question: List<HashMap<String, String>>,
                            var options: List<HashMap<String, String>>,
                            var numberInput: List<HashMap<String, String>>,
    var context: Context): RecyclerView.Adapter<MultipleChoiceAdapter.MultipleChoiceViewHolder>(){

    inner class MultipleChoiceViewHolder(itemView: View) : ViewHolder(itemView){

        var questionTxt: TextView = itemView.findViewById(R.id.question)
//        var choice_list: ListView = itemView.findViewById(R.id.choice_list)
        var radioGroup: RadioGroup = itemView.findViewById(R.id.optionRadioGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleChoiceViewHolder {

        var multiple_choice_question_view = LayoutInflater.from(context).inflate(R.layout.multiple_choice_question_design,
            parent, false)

        return MultipleChoiceViewHolder(multiple_choice_question_view)
    }

    override fun getItemCount(): Int {

        return question.size
    }

    override fun onBindViewHolder(holder: MultipleChoiceViewHolder, position: Int) {

        var question = question[position]
        var slug = question["question"]
        holder.questionTxt.text = slug


        for (option in options){

            var optionValue = option.get("value")
            var radioButton = RadioButton(context)
            radioButton.text = optionValue
            radioButton.id = View.generateViewId()

            var params = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            )

            radioButton.layoutParams = params

            holder.radioGroup.addView(radioButton)
        }

        holder.radioGroup.setOnCheckedChangeListener { radioGroup, i ->

            var selectedId = holder.radioGroup.checkedRadioButtonId

            Log.d("selectedId", selectedId.toString())

            var radioButton: View = radioGroup.findViewById(i)
            var index = radioGroup.indexOfChild(radioButton)



        }

    }

}