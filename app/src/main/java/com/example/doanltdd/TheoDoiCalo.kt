package com.example.doanltdd

import android.os.Bundle
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TheoDoiCalo : AppCompatActivity() {
    lateinit var calorieEditText: EditText
    lateinit var mealNameSpinner: Spinner
    lateinit var addButton: Button
    lateinit var suaButton: Button
    lateinit var xoaButton: Button
    lateinit var totalCaloriesButton: Button
    lateinit var mealsListView: ListView
    lateinit var totalCaloriesTextView: TextView

    lateinit var meals: ArrayList<String>
    lateinit var mealsAdapter: ArrayAdapter<String>

    var selectedPosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_theo_doi_calo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setControl()
        setEvent()
    }






        private fun setControl() {
            title = "Theo dõi lượng calo và dinh dưỡng nạp vào cơ thể"

            calorieEditText = findViewById(R.id.calorieEditText)
            mealNameSpinner = findViewById(R.id.mealNameSpinner)
            addButton = findViewById(R.id.addButton)
            suaButton = findViewById(R.id.deleteButton) // Sửa
            xoaButton = findViewById(R.id.editButton)   // Xóa
            totalCaloriesButton = findViewById(R.id.totalCaloriesButton)
            mealsListView = findViewById(R.id.mealsListView)
            totalCaloriesTextView = findViewById(R.id.totalCaloriesTextView)

            // Spinner
            val mealOptions = arrayListOf(
                "Bữa sáng",
                "Bữa trưa",
                "Bữa chiều",
                "Bữa tối"
            )

            val spinnerAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                mealOptions
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mealNameSpinner.adapter = spinnerAdapter

            // ListView
            meals = ArrayList()
            mealsAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                meals
            )
            mealsListView.adapter = mealsAdapter
        }

        private fun setEvent() {

            // Add
            addButton.setOnClickListener {
                val calories = calorieEditText.text.toString()
                val mealType = mealNameSpinner.selectedItem.toString()

                if (calories.isNotEmpty()) {
                    val mealInfo = "$mealType: $calories calo"
                    meals.add(mealInfo)
                    mealsAdapter.notifyDataSetChanged()
                    calorieEditText.setText("")
                } else {
                    Toast.makeText(this, "Vui lòng nhập số calo", Toast.LENGTH_SHORT).show()
                }
            }

            // Click item ListView
            mealsListView.setOnItemClickListener { _, _, position, _ ->
                selectedPosition = position
                val selectedMeal = meals[position]

                try {
                    val parts = selectedMeal.split(": ")
                    val mealType = parts[0]
                    val calories = parts[1].split(" ")[0]

                    calorieEditText.setText(calories)

                    val adapter = mealNameSpinner.adapter as ArrayAdapter<String>
                    val spinnerPosition = adapter.getPosition(mealType)
                    mealNameSpinner.setSelection(spinnerPosition)
                } catch (e: Exception) {
                    Toast.makeText(this, "Lỗi khi chọn bữa ăn", Toast.LENGTH_SHORT).show()
                }
            }

            // Sửa
            suaButton.setOnClickListener {
                if (selectedPosition != -1) {
                    val calories = calorieEditText.text.toString()
                    val mealType = mealNameSpinner.selectedItem.toString()

                    if (calories.isNotEmpty()) {
                        val mealInfo = "$mealType: $calories calo"
                        meals[selectedPosition] = mealInfo
                        mealsAdapter.notifyDataSetChanged()
                        calorieEditText.setText("")
                        selectedPosition = -1
                    } else {
                        Toast.makeText(this, "Vui lòng nhập số calo", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Vui lòng chọn một bữa ăn để sửa", Toast.LENGTH_SHORT).show()
                }
            }

            // Xóa
            xoaButton.setOnClickListener {
                if (selectedPosition != -1) {
                    meals.removeAt(selectedPosition)
                    mealsAdapter.notifyDataSetChanged()
                    calorieEditText.setText("")
                    selectedPosition = -1
                } else {
                    Toast.makeText(this, "Vui lòng chọn một bữa ăn để xóa", Toast.LENGTH_SHORT).show()
                }
            }

            // Tổng calo
            totalCaloriesButton.setOnClickListener {
                var totalCalories = 0

                for (meal in meals) {
                    try {
                        val caloriesStr = meal.split(": ")[1].split(" ")[0]
                        totalCalories += caloriesStr.toInt()
                    } catch (_: Exception) {
                    }
                }

                totalCaloriesTextView.text = "$totalCalories calo"
            }
        }
    }

