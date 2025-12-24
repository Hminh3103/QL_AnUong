package com.example.doanltdd

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.doanltdd.Data.Dao.MonAnDao
import com.example.doanltdd.Data.Database.db_MonAn
import com.example.doanltdd.Data.Entity.Monan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainDoAn : AppCompatActivity() {

    private lateinit var etMealName: EditText
    private lateinit var spinnerMealType: Spinner
    private lateinit var etCalories: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnDelete: Button
    private lateinit var btnEdit: Button
    private lateinit var btnExit: Button
    private lateinit var listViewMeals: ListView

    // Database & Dao
    private lateinit var databaseMonAn: db_MonAn
    private lateinit var monAnDao: MonAnDao

    // Danh sách để hiển thị lên giao diện
    private val mealList = mutableListOf<Monan>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_do_an)

        // Khởi tạo Database
        databaseMonAn = db_MonAn.getDatabase(this)
        monAnDao = databaseMonAn.monAnDao()

        initViews()
        setupSpinner()
        setupListView()
        setEvent()

        // Tải dữ liệu từ database ngay khi mở app
        loadDataFromDatabase()
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
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, mutableListOf())
        listViewMeals.adapter = adapter
        listViewMeals.choiceMode = ListView.CHOICE_MODE_SINGLE

        listViewMeals.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val meal = mealList[position]

            etMealName.setText(meal.tenmonan)
            etCalories.setText(meal.socalo.toString())

            val spinnerAdapter = spinnerMealType.adapter
            for (i in 0 until spinnerAdapter.count) {
                if (spinnerAdapter.getItem(i).toString() == meal.buaan) {
                    spinnerMealType.setSelection(i)
                    break
                }
            }
        }
    }

    private fun setEvent() {
        btnAdd.setOnClickListener { addMeal() }
        btnDelete.setOnClickListener { deleteMeal() }
        btnEdit.setOnClickListener { editMeal() }
        btnExit.setOnClickListener { finish() }
    }

    // --- CÁC HÀM XỬ LÝ DATABASE ---

    private fun loadDataFromDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = monAnDao.getAllMonAn()
            withContext(Dispatchers.Main) {
                mealList.clear()
                mealList.addAll(list)
                refreshListView()
            }
        }
    }

    private fun addMeal() {
        val name = etMealName.text.toString().trim()
        val type = spinnerMealType.selectedItem.toString()
        val caloriesStr = etCalories.text.toString().trim()

        if (name.isEmpty() || caloriesStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val calories = caloriesStr.toIntOrNull() ?: 0
        val newMonAn = Monan(tenmonan = name, buaan = type, socalo = calories)

        lifecycleScope.launch(Dispatchers.IO) {
            monAnDao.insertMonAn(newMonAn)
            withContext(Dispatchers.Main) {
                loadDataFromDatabase()
                clearInputs()
                Toast.makeText(this@MainDoAn, "Đã thêm vào database", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMeal() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn món để xóa", Toast.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa món này?")
            .setPositiveButton("Xóa") { _, _ ->
                val mealToDelete = mealList[selectedPosition]
                lifecycleScope.launch(Dispatchers.IO) {
                    monAnDao.deleteMonAn(mealToDelete)
                    withContext(Dispatchers.Main) {
                        loadDataFromDatabase()
                        clearInputs()
                    }
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun editMeal() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn món để sửa", Toast.LENGTH_SHORT).show()
            return
        }

        val name = etMealName.text.toString().trim()
        val type = spinnerMealType.selectedItem.toString()
        val calories = etCalories.text.toString().toIntOrNull() ?: 0

        // Lấy ID của món cũ để cập nhật đúng dòng đó
        val oldMeal = mealList[selectedPosition]
        val updatedMeal = Monan(id = oldMeal.id, tenmonan = name, buaan = type, socalo = calories)

        lifecycleScope.launch(Dispatchers.IO) {
            monAnDao.updateMonAn(updatedMeal)
            withContext(Dispatchers.Main) {
                loadDataFromDatabase()
                Toast.makeText(this@MainDoAn, "Đã cập nhật", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshListView() {
        adapter.clear()
        mealList.forEach { meal ->
            adapter.add("${meal.tenmonan} (${meal.buaan}) - ${meal.socalo} kcal")
        }
        adapter.notifyDataSetChanged()
    }

    private fun clearInputs() {
        etMealName.text.clear()
        etCalories.text.clear()
        spinnerMealType.setSelection(0)
        selectedPosition = -1
        listViewMeals.clearChoices()
    }
}