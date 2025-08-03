package com.devsyncit.task1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retroInstance = RetrofitInstance.getInstance().create(ApiService::class.java)

        GlobalScope.launch {
            val records = retroInstance.getRecord().body()
            val recordX = records?.record

            for (record in recordX!!){
                val id = record.id
                val type = record.type
                val question = record.question

                if(type.equals("multipleChoice")){
                    val options = record.options

                    for (option in options){
                        val value = option.value
                    }

                }

            }

            Log.d("records", ""+records?.record)
        }

    }
}