package com.devsyncit.task1

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
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
import okhttp3.internal.connection.Exchange
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
//            val record = records?.record[0] ?: return@launch

            records?.record?.forEach {
                questionMapById[it.id] = it
            }

            Log.d("keys", questionMapById.keys.toString())

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

                Log.d("childCount", linearLayout.childCount.toString())

                val view = createMultipleChoiceView(record)
                linearLayout.addView(view)
            }

            "numberInput" -> {

                Log.d("childCount", linearLayout.childCount.toString())

                val view = createNumberInputView(record)
                linearLayout.addView(view)
            }

            "dropdown" -> {

                Log.d("childCount", linearLayout.childCount.toString())

                val view = createDropdownView(record)
                linearLayout.addView(view)
            }

            "checkbox" -> {

                Log.d("childCount", linearLayout.childCount.toString())

                val view = createCheckboxView(record)
                linearLayout.addView(view)
            }

            "camera" -> {

                Log.d("childCount", linearLayout.childCount.toString())

                val view = createCameraView(record)
                linearLayout.addView(view)
            }

            "textInput" -> {

                Log.d("childCount", linearLayout.childCount.toString())

                val view = createTextInputView(record)
                linearLayout.addView(view)
            }
        }


        if (record == null) {
            Log.d("nextQuestionId", "Message: record is null " + record.referTo)
        } else {
            Log.d("nextQuestionId", "Message: record is not null record: " + record)
            Log.d("nextQuestionId", "Message: record is not null, refer to: " + record.referTo)
        }


    }

    fun createMultipleChoiceView(record: RecordX): View {

        val multipleChoiceView =
            layoutInflater.inflate(R.layout.multiple_choice_question_design, null)

        val index = linearLayout.indexOfChild(multipleChoiceView)
        val childCount = linearLayout.childCount


        var questionTxt = multipleChoiceView.findViewById<TextView>(R.id.question)
        var radioGroup = multipleChoiceView.findViewById<RadioGroup>(R.id.optionRadioGroup)

        var question = record.question.slug
        var options = record.options

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

            radioButton.tag = option.referTo.id
        }

        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->

            linearLayout.removeAllViews()
            linearLayout.addView(multipleChoiceView)
            Log.d("childCount", linearLayout.childCount.toString())
            val selectedBtn = radioGroup.findViewById<RadioButton>(checkedId)
            val nextId = selectedBtn.tag as String
            processQuestion(nextId)
        }

        return multipleChoiceView
    }

    fun createNumberInputView(record: RecordX): View {
        var numberInputView = layoutInflater.inflate(R.layout.number_input_design, null)

        var questionTxt = numberInputView.findViewById<TextView>(R.id.question)
        var numberInput = numberInputView.findViewById<TextInputLayout>(R.id.numberInput)
        var numberEdittext = numberInputView.findViewById<TextInputEditText>(R.id.numberEdittext)
        var submitBtn = numberInputView.findViewById<MaterialButton>(R.id.submit_btn)

        questionTxt.text = record.question.slug

        submitBtn.setOnClickListener {

            for (i in linearLayout.childCount - 1 downTo linearLayout.indexOfChild(numberInputView) + 1) {
                linearLayout.removeViewAt(i)
            }

            Log.d("childCount", linearLayout.childCount.toString())
            processQuestion(record.referTo.id)
        }

        return numberInputView
    }


    private fun createTextInputView(record: RecordX): View {

        val textInputView = layoutInflater.inflate(R.layout.text_input_design, null)

        var question = textInputView.findViewById<TextView>(R.id.question)
        var next_btn = textInputView.findViewById<MaterialButton>(R.id.next_btn)


        next_btn.setOnClickListener {

            for (i in linearLayout.childCount - 1 downTo linearLayout.indexOfChild(textInputView) + 1) {
                linearLayout.removeViewAt(i)
            }

            Log.d("childCount", linearLayout.childCount.toString())
            processQuestion(record.referTo.id)
        }

        question.text = record.question.slug


        return textInputView
    }

    private fun createCameraView(record: RecordX): View {

        val cameraView = layoutInflater.inflate(R.layout.camera_design, null)

        var question = cameraView.findViewById<TextView>(R.id.question)
        var next_btn = cameraView.findViewById<MaterialButton>(R.id.next_btn)
        var camera_btn = cameraView.findViewById<ImageButton>(R.id.camera_btn)

        question.text = record.question.slug

        next_btn.setOnClickListener {

            for (i in linearLayout.childCount - 1 downTo linearLayout.indexOfChild(cameraView) + 1) {
                linearLayout.removeViewAt(i)
            }

            Log.d("childCount", linearLayout.childCount.toString())
            processQuestion(record.referTo.id)
        }

        camera_btn.setOnClickListener {
            val cameraPreviewView = layoutInflater.inflate(R.layout.camera_preview_layout, null)
            var previewView: PreviewView = cameraPreviewView.findViewById(R.id.camera_preview)
            var capture_btn: ImageButton = cameraPreviewView.findViewById(R.id.capture_btn)

            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                    val imageCapture = ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()

                    capture_btn.setOnClickListener {
                        imageCapture.takePicture(
                            ContextCompat.getMainExecutor(this),
                            object : ImageCapture.OnImageCapturedCallback(){
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    super.onCaptureSuccess(image)
                                    Log.d("captured", "image captured")
                                    val proxy = image.planes[0]
                                    val buffer = proxy.buffer
                                    val bytes = ByteArray(buffer.remaining())
                                    buffer.get(bytes)
                                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                    showCapturedImage(bitmap)
                                    image.close()
                                }
                            }
                        )
                    }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            this, cameraSelector, preview, imageCapture
                        )
                    } catch (e: Exception) {
                        Log.d("Exception", "Exception: " + e.toString())
                    }
                }, ContextCompat.getMainExecutor(this))
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    100
                )
            }

            var dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog.setContentView(cameraPreviewView)
            dialog.show()
        }

        return cameraView
    }

    private fun createCheckboxView(record: RecordX): View {

        val checkBoxView = layoutInflater.inflate(R.layout.check_box_design, null)

        var question = checkBoxView.findViewById<TextView>(R.id.question)
        var checkBox = checkBoxView.findViewById<LinearLayout>(R.id.checkbox_layout)
        var next_btn = checkBoxView.findViewById<Button>(R.id.next_btn)
        var skip_btn = checkBoxView.findViewById<Button>(R.id.skip_btn)

        var checkedOption = 0
        var checkBoxList: MutableList<CheckBox> = mutableListOf()

        //question set
        question.text = record.question.slug

        val options = record.options
        val skipValue = record.skip.id

        if (skipValue.equals("-1")) {
            skip_btn.visibility = View.INVISIBLE
        }

        for (option in options) {
            var box = CheckBox(this)
            box.text = option.value
            checkBox.addView(box)
            checkBoxList.add(box)
        }


        for (box in checkBoxList) {
            box.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(cmpButton: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        checkedOption++
                    } else if (!isChecked) {
                        checkedOption--
                    }
                }

            })
        }


        next_btn.setOnClickListener {

            Log.d("checkedOption", checkedOption.toString())

            if (checkedOption <= 0) {
                for (i in linearLayout.childCount - 1 downTo linearLayout.indexOfChild(checkBoxView) + 1) {
                    linearLayout.removeViewAt(i)
                }
                Toast.makeText(this, record.question.slug, Toast.LENGTH_SHORT).show()
            } else {
                for (i in linearLayout.childCount - 1 downTo linearLayout.indexOfChild(checkBoxView) + 1) {
                    linearLayout.removeViewAt(i)
                }

                Log.d("childCount", linearLayout.childCount.toString())
                processQuestion(record.referTo.id)
            }
        }


        return checkBoxView
    }

    private fun createDropdownView(record: RecordX): View {

        val dropDownView = layoutInflater.inflate(R.layout.drop_down_design, null)

        var options = record.options

        var items = mutableListOf<Option>()

        items.add(0, Option(value = "Select an option", referTo = ReferToX(id = "")))

        for ((index, option) in options.withIndex()) {
            items.add(option)
        }

        var called = 0

        var spinner = dropDownView.findViewById<AppCompatSpinner>(R.id.drop_down)
        var questionText = dropDownView.findViewById<TextView>(R.id.question)

        //question set
        questionText.text = record.question.slug

        spinner.setSelection(0, false)

        var spinnerAdapter = object : ArrayAdapter<Option>(
            this, R.layout.spinner_item_design, R.id.spinner_item_name,
            items
        ) {

            override fun isEnabled(position: Int): Boolean {

                return true
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View? {
                val view = super.getDropDownView(position, convertView, parent)

                var textview = view.findViewById<TextView>(R.id.spinner_item_name)


                var item = items.get(position)
                var value = item.value
                textview.text = value


                return view
            }

        }


        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                called++
                if (called > 1) {

                    for (i in linearLayout.childCount - 1 downTo linearLayout.indexOfChild(
                        dropDownView
                    ) + 1) {
                        linearLayout.removeViewAt(i)
                    }

                    Log.d("childCount", linearLayout.childCount.toString())

                    val selectedOption = items[position]
                    val nextId = selectedOption.referTo.id

                    processQuestion(nextId)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        return dropDownView
    }

    fun showCapturedImage(bitmap:Bitmap){

        val view = layoutInflater.inflate(R.layout.show_captured_image_layout, null)
        var preview_imageView = view.findViewById<ImageView>(R.id.preview_image)
        var forward_btn = view.findViewById<Button>(R.id.forward_btn)
        var retake_btn = view.findViewById<Button>(R.id.retake_btn)


        preview_imageView.setImageBitmap(bitmap)

        var dialog = Dialog(this)
        dialog.setContentView(view)
        dialog.show()


    }

}