package com.example.doanltdd

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TheoDoiCalo : AppCompatActivity() {
    private lateinit var dishNameEditText: EditText
    private lateinit var mealNameSpinner: Spinner
    private lateinit var calorieEditText: EditText
    private lateinit var addButton: Button
    private lateinit var suaButton: Button
    private lateinit var xoaButton: Button
    private lateinit var thoatButton: Button
    private lateinit var mealsListView: ListView

    private val meals = ArrayList<String>()
    private lateinit var mealsAdapter: ArrayAdapter<String>
    private val mealOptions = arrayListOf("Bữa sáng", "Bữa trưa", "Bữa chiều", "Bữa tối")

    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_theo_doi_calo)

        val mainView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setControl()
        setEvent()
    }

    private fun setControl() {
        dishNameEditText = findViewById(R.id.dishNameEditText)
        mealNameSpinner = findViewById(R.id.mealNameSpinner)
        calorieEditText = findViewById(R.id.calorieEditText)
        addButton = findViewById(R.id.addButton)
        suaButton = findViewById(R.id.suaButton)
        xoaButton = findViewById(R.id.xoaButton)
        thoatButton = findViewById(R.id.thoatButton)
        mealsListView = findViewById(R.id.mealsListView)

        // Setup Spinner
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mealOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mealNameSpinner.adapter = spinnerAdapter

        // Setup ListView
        mealsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, meals)
        mealsListView.adapter = mealsAdapter
    }

    private fun setEvent() {
        addButton.setOnClickListener {
            val dishName = dishNameEditText.text.toString().trim()
            val calories = calorieEditText.text.toString().trim()
            val mealType = mealNameSpinner.selectedItem.toString()

            if (dishName.isNotEmpty() && calories.isNotEmpty()) {
                val mealInfo = "[$mealType] $dishName: $calories calo"
                meals.add(mealInfo)
                mealsAdapter.notifyDataSetChanged()
                clearInputs()
                Toast.makeText(this, "Đã thêm món ăn", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        mealsListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val selectedMeal = meals[position]
            try {
                // Ví dụ chuỗi: "[Bữa sáng] Phở: 500 calo"
                val regex = Regex("\\[(.*?)\\] (.*?): (.*?) calo")
                val matchResult = regex.find(selectedMeal)

                if (matchResult != null) {
                    val (mealType, dishName, calories) = matchResult.destructured

                    dishNameEditText.setText(dishName)
                    calorieEditText.setText(calories)

                    val spinnerPosition = mealOptions.indexOf(mealType)
                    if (spinnerPosition != -1) {
                        mealNameSpinner.setSelection(spinnerPosition)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Lỗi khi lấy dữ liệu món ăn", Toast.LENGTH_SHORT).show()
            }
        }

        suaButton.setOnClickListener {
            if (selectedPosition != -1) {
                val dishName = dishNameEditText.text.toString().trim()
                val calories = calorieEditText.text.toString().trim()
                val mealType = mealNameSpinner.selectedItem.toString()

                if (dishName.isNotEmpty() && calories.isNotEmpty()) {
                    val mealInfo = "[$mealType] $dishName: $calories calo"
                    meals[selectedPosition] = mealInfo
                    mealsAdapter.notifyDataSetChanged()
                    clearInputs()
                    Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng chọn món cần sửa từ danh sách", Toast.LENGTH_SHORT).show()
            }
        }

        xoaButton.setOnClickListener {
            if (selectedPosition != -1) {
                meals.removeAt(selectedPosition)
                mealsAdapter.notifyDataSetChanged()
                clearInputs()
                Toast.makeText(this, " Đã xóa món ăn", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng chọn món cần xóa", Toast.LENGTH_SHORT).show()
            }
        }

        thoatButton.setOnClickListener {
            finish()
        }
    }

    private fun clearInputs() {
        dishNameEditText.setText("")
        calorieEditText.setText("")
        mealNameSpinner.setSelection(0)
        selectedPosition = -1
    }
}