package com.devsyncit.task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton

class ChoiceListAdapter(var option_list: List<Option>): BaseAdapter() {
    override fun getCount(): Int {

        return option_list.size
    }

    override fun getItem(position: Int): Any {

        return 0
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        var optionView = LayoutInflater.from(parent?.context).inflate(R.layout.multiple_choice_question_design, parent)

        var optionRadioButton = optionView.findViewById<RadioButton>(R.id.option)


        return optionView
    }
}