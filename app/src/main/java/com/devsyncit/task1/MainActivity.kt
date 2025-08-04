package com.devsyncit.task1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

class MainActivity : AppCompatActivity() {

    lateinit var optionMap: HashMap<String, String>
    var questionMapById: HashMap<String, RecordX> = HashMap()
    var questionList: MutableList<HashMap<String, Any?>> = mutableListOf()
    lateinit var numberInputMap: HashMap<String, String>
    var numberInputList: MutableList<HashMap<String, String>> = mutableListOf()

    lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val retroInstance = RetrofitInstance.getInstance().create(ApiService::class.java)

        linearLayout = findViewById(R.id.rootLayout)

        //multiple choice question adapter set up

//        var multipleChoiceAdapter = MultipleChoiceAdapter(questionList, numberInputList, this)
//        item_list.adapter = multipleChoiceAdapter
//        item_list.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val records = retroInstance.getRecord().body()
            val record = records?.record ?: return@launch

            record.forEach {
                questionMapById[it.id] = it
            }

            processQuestion("1")
//            for (record in recordX!!){
//                val id = record.id
//                val type = record.type
//                val question = record.question
//
////                questionMap = HashMap()
////                questionMap.put("id", id)
////                questionMap.put("type", type)
////                questionMap.put("question", question.slug)
//
//                if(type.equals("multipleChoice")){
//                    val options = record.options
//
//                    var optionList: MutableList<HashMap<String, String>> = mutableListOf()
//
//                    for (option in options){
//                        optionMap = HashMap()
//                        val value = option.value
//                        val referToX = option.referTo
//                        val referTo = referToX.id
//                        optionMap.put("value", value)
//                        optionMap.put("referTo", referTo)
//                        optionList.add(optionMap)
//                    }
//
////                    questionMap.put("options", optionList)
////
////                    questionList.add(questionMap)
//
//                    multipleChoiceAdapter.notifyDataSetChanged()
//
//                }
//
//                if (type.equals("numberInput")){
//
//
//                    val validations = record.validations
//                    val regex = validations.regex
//                    val referToX = record.referTo
//                    val referTo = referToX.id
//
//                    numberInputMap = HashMap()
//                    numberInputMap.put("regex", regex)
//                    numberInputMap.put("referTo", referTo)
//                    numberInputList.add(numberInputMap)
//
//                    multipleChoiceAdapter.notifyDataSetChanged()
//
//                }
//
//            }
//
//            Log.d("records", ""+records?.record)
        }


    }


    fun processQuestion(id: String) {

        if (id == null || id == "submit") return

        val record = questionMapById[id] ?: return

        when (record.type) {
            "multipleChoice" -> {
                val options = record.options
                val view = createMultipleChoiceView(record.question.slug, options)
                linearLayout.addView(view)
            }

            "numberInput" -> {
                val view = createNumberInputView(record.question.slug, record.validations.regex)
                linearLayout.addView(view)
            }

            "dropdown" -> {
                val view = createDropdownView(record.question.slug, record.options)
                linearLayout.addView(view)
            }

            "checkbox" -> {
                val view = createCheckboxView(record.question.slug, record.options)
                linearLayout.addView(view)
            }

            "camera" -> {
                val view = createCameraView(record.question.slug)
                linearLayout.addView(view)
            }

            "textInput" -> {
                val view = createTextInputView(record.question.slug, record.validations?.regex)
                linearLayout.addView(view)
            }
        }

        if(record == null){
            Log.d("nextQuestionId", "Message: recor is null")
        }else{
            Log.d("nextQuestionId", "Message: recor is null")
        }



//        processQuestion(record.referTo.id)

    }


    fun createMultipleChoiceView(question: String, options: List<Option>): View {

        val multipleChoiceView =
            layoutInflater.inflate(R.layout.multiple_choice_question_design, null)

        var questionTxt = multipleChoiceView.findViewById<TextView>(R.id.question)
        var radioGroup = multipleChoiceView.findViewById<RadioGroup>(R.id.optionRadioGroup)

        questionTxt.text = question

        for (option in options) {
            var radioButton = RadioButton(this)
            radioButton.text = option.value
            radioButton.id = View.generateViewId()

            var params = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            )

            radioButton.layoutParams = params
            radioGroup.addView(radioButton)
        }

        return multipleChoiceView
    }

    fun createNumberInputView(question: String, regex: String): View {
        var numberInputView = layoutInflater.inflate(R.layout.number_input_design, null)

        var questionTxt = numberInputView.findViewById<TextView>(R.id.question)
        var numberInput = numberInputView.findViewById<TextInputLayout>(R.id.numberInput)
        var numberEdittext = numberInputView.findViewById<TextInputEditText>(R.id.numberEdittext)
        var submitBtn = numberInputView.findViewById<MaterialButton>(R.id.submit_btn)

        submitBtn.setOnClickListener {

        }

        return numberInputView
    }


    private fun createTextInputView(slug: String, regex: String?): View {

        val textInputView = layoutInflater.inflate(R.layout.text_input_design, null)

        return textInputView
    }

    private fun createCameraView(slug: String): View {

        val cameraView = layoutInflater.inflate(R.layout.camera_design, null)

        return cameraView
    }

    private fun createCheckboxView(slug: String, options: List<Option>): View {

        val checkBoxView = layoutInflater.inflate(R.layout.check_box_design, null)

        return checkBoxView
    }

    private fun createDropdownView(slug: String, options: List<Option>): View {

        val dropDownView = layoutInflater.inflate(R.layout.drop_down_design, null)

        var spinner = dropDownView.findViewById<AppCompatSpinner>(R.id.drop_down)
        val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_item_design, options)

        spinner.adapter = spinnerAdapter

        return dropDownView
    }


}