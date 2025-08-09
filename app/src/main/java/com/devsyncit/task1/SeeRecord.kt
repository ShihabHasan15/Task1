package com.devsyncit.task1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class SeeRecord : AppCompatActivity() {

    var questionMapById: HashMap<String, RecordX> = HashMap()
    lateinit var dbInstance: RecordDatabase
    lateinit var record_list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_record)

        record_list = findViewById(R.id.record_list)


        val retroInstance = RetrofitInstance.getInstance().create(ApiService::class.java)

        dbInstance = RecordDatabase.getDbInstance(this@SeeRecord)

//
//
//        lifecycleScope.launch {
//            var record = retroInstance.getRecord().body()
//            record?.record?.forEach {
//                questionMapById[it.id] = it
//            }
//        }

        dbInstance.recordDao().getAllRecords().observe(this, Observer {
            var recordAdapter = RecordAdapter(this, it)
            record_list.adapter = recordAdapter
            record_list.layoutManager = LinearLayoutManager(this)
        })

    }
}