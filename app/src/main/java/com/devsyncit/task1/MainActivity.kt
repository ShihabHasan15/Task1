package com.devsyncit.task1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var optionMap: HashMap<String, String>
    lateinit var questionMap: HashMap<String, Any?>
    var questionList: MutableList<HashMap<String, Any?>> = mutableListOf()
    lateinit var numberInputMap: HashMap<String, String>
    var numberInputList: MutableList<HashMap<String, String>> = mutableListOf()

    lateinit var item_list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item_list = findViewById(R.id.item_list)


        val retroInstance = RetrofitInstance.getInstance().create(ApiService::class.java)

        //multiple choice question adapter set up

        var multipleChoiceAdapter = MultipleChoiceAdapter(questionList, numberInputList, this)
        item_list.adapter = multipleChoiceAdapter
        item_list.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val records = retroInstance.getRecord().body()
            val recordX = records?.record

            for (record in recordX!!){
                val id = record.id
                val type = record.type
                val question = record.question

                questionMap = HashMap()
                questionMap.put("id", id)
                questionMap.put("type", type)
                questionMap.put("question", question.slug)

                if(type.equals("multipleChoice")){
                    val options = record.options

                    var optionList: MutableList<HashMap<String, String>> = mutableListOf()

                    for (option in options){
                        optionMap = HashMap()
                        val value = option.value
                        val referToX = option.referTo
                        val referTo = referToX.id
                        optionMap.put("value", value)
                        optionMap.put("referTo", referTo)
                        optionList.add(optionMap)
                    }

                    questionMap.put("options", optionList)

                    questionList.add(questionMap)

                    multipleChoiceAdapter.notifyDataSetChanged()

                }

                if (type.equals("numberInput")){


                    val validations = record.validations
                    val regex = validations.regex
                    val referToX = record.referTo
                    val referTo = referToX.id

                    numberInputMap = HashMap()
                    numberInputMap.put("regex", regex)
                    numberInputMap.put("referTo", referTo)
                    numberInputList.add(numberInputMap)

                    multipleChoiceAdapter.notifyDataSetChanged()

                }

            }

            Log.d("records", ""+records?.record)
        }


    }
}