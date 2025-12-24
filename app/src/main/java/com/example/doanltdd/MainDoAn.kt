package com.example.doanltdd

import com.example.doanltdd.Data.Database.db_MonAn
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.doanltdd.Data.Dao.MonAnDao

class MainDoAn : AppCompatActivity() {

    private lateinit var etMealName: EditText
    private lateinit var spinnerMealType: Spinner
    private lateinit var etCalories: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnDelete: Button
    private lateinit var btnEdit: Button
    private lateinit var btnExit: Button
    private lateinit var listViewMeals: ListView
    private lateinit var databaseMonAn: db_MonAn
    private lateinit var monAnDao: MonAnDao



    private val mealList = mutableListOf<Meal>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_do_an)

        databaseMonAn = db_MonAn.getDatabase(this)
        monAnDao = databaseMonAn.monAnDao()

        initViews()
        setupSpinner()
        setupListView()
        setEvent()
        //loadSampleData()
    }

    private fun initViews() {
        etMealName = findViewById(R.id.etMealName)
        spinnerMealType = findViewById(R.id.spinnerMealType)
        etCalories = findViewById(R.id.etCalories)
        btnAdd = findViewById(R.id.btnAdd)
        btnDelete = findViewById(R.id.btnDelete)
        btnEdit = findViewById(R.id.btnEdit)
        btnExit = findViewById(R.id.btnExit)
        listViewMeals = findViewById(R.id.listViewMeals)
    }

    private fun setupSpinner() {
        val mealTypes = arrayOf("Bữa sáng", "Bữa trưa", "Bữa tối", "Bữa phụ")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mealTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMealType.adapter = adapter
    }

    private fun setupListView() {
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            mutableListOf()
        )
        listViewMeals.adapter = adapter
        listViewMeals.choiceMode = ListView.CHOICE_MODE_SINGLE

        listViewMeals.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val meal = mealList[position]

            etMealName.setText(meal.name)
            etCalories.setText(meal.calories.toString())

            // Set spinner selection
            val spinnerAdapter = spinnerMealType.adapter
            for (i in 0 until spinnerAdapter.count) {
                if (spinnerAdapter.getItem(i).toString() == meal.type) {
                    spinnerMealType.setSelection(i)
                    break
                }
            }
        }
    }

    private fun setEvent() {
        val data = User_SharedPreferences(this@MainDoAn)
        val id = data.getUserId()
        btnAdd.setOnClickListener { addMeal() }
        btnDelete.setOnClickListener { deleteMeal() }
        btnEdit.setOnClickListener { editMeal() }
        btnExit.setOnClickListener { finish() }
    }

    private fun loadSampleData() {
        mealList.add(Meal("Oatmeal", "Bữa sáng", 200))
        mealList.add(Meal("Lentil Soup", "Bữa trưa", 500))
        refreshListView()
    }

    private fun addMeal() {
        val name = etMealName.text.toString().trim()
        val type = spinnerMealType.selectedItem.toString()
        val caloriesStr = etCalories.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show()
            return
        }

        if (caloriesStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập calories", Toast.LENGTH_SHORT).show()
            return
        }

        val calories = caloriesStr.toIntOrNull()
        if (calories == null || calories <= 0) {
            Toast.makeText(this, "Calories phải là số dương", Toast.LENGTH_SHORT).show()
            return
        }

        val meal = Meal(name, type, calories)
        mealList.add(meal)
        refreshListView()

        // Tự động chọn món vừa thêm
        selectedPosition = mealList.size - 1
        listViewMeals.setItemChecked(selectedPosition, true)

        Toast.makeText(this, "Đã thêm món ăn", Toast.LENGTH_SHORT).show()
    }

    private fun deleteMeal() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn món ăn để xóa", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa món ăn này?")
            .setPositiveButton("Xóa") { _, _ ->
                mealList.removeAt(selectedPosition)
                selectedPosition = -1
                refreshListView()
                clearInputs()
                Toast.makeText(this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun editMeal() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn món ăn để sửa", Toast.LENGTH_SHORT).show()
            return
        }

        val name = etMealName.text.toString().trim()
        val type = spinnerMealType.selectedItem.toString()
        val caloriesStr = etCalories.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show()
            return
        }

        if (caloriesStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập calories", Toast.LENGTH_SHORT).show()
            return
        }

        val calories = caloriesStr.toIntOrNull()
        if (calories == null || calories <= 0) {
            Toast.makeText(this, "Calories phải là số dương", Toast.LENGTH_SHORT).show()
            return
        }

        mealList[selectedPosition] = Meal(name, type, calories)
        refreshListView()
        listViewMeals.setItemChecked(selectedPosition, true)

        Toast.makeText(this, "Đã cập nhật món ăn", Toast.LENGTH_SHORT).show()
    }

    private fun refreshListView() {
        adapter.clear()
        mealList.forEach { meal ->
            adapter.add("${meal.name} (${meal.type}) - ${meal.calories} kcal")
        }
        adapter.notifyDataSetChanged()
    }

    private fun clearInputs() {
        etMealName.text.clear()
        spinnerMealType.setSelection(0)
        etCalories.text.clear()
        selectedPosition = -1
        listViewMeals.clearChoices()
        adapter.notifyDataSetChanged()
    }

    data class Meal(
        val name: String,
        val type: String,
        val calories: Int
    )
}
