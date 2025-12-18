package com.example.doanltdd

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Main_GiuaKy : AppCompatActivity() {
    private var totalCalories = 0
    private var currentTarget = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main_giua_ky)

        // 1. Ánh xạ View (Kotlin dùng findViewById ngắn gọn hơn)
        val etCalo = findViewById<EditText>(R.id.etCalo)
        val etTarget = findViewById<EditText>(R.id.etTarget)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Hàm cập nhật giao diện (viết cục bộ hoặc tách hàm đều được)
        fun updateResultUI() {
            tvResult.text = "Tổng cộng calo trong tuần: $totalCalories\n\n\nMục tiêu cá nhân: $currentTarget"
        }

        // 2. Nút THÊM
        btnAdd.setOnClickListener {
            val caloString = etCalo.text.toString()
            val targetString = etTarget.text.toString()

            if (caloString.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số calo!", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    // Cộng dồn calo
                    totalCalories += caloString.toInt()
                    currentTarget = targetString

                    updateResultUI()

                    Toast.makeText(this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show()
                    etCalo.setText("") // Xóa ô nhập sau khi thêm

                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Số calo không hợp lệ!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 3. Nút XÓA
        btnDelete.setOnClickListener {
            totalCalories = 0
            currentTarget = ""
            etCalo.setText("")
            etTarget.setText("")

            updateResultUI()

            Toast.makeText(this, "Đã xóa dữ liệu!", Toast.LENGTH_SHORT).show()
        }

        // 4. Nút SỬA
        btnEdit.setOnClickListener {
            val newTarget = etTarget.text.toString()
            if (newTarget.isEmpty()) {
                Toast.makeText(this, "Hãy nhập mục tiêu mới!", Toast.LENGTH_SHORT).show()
            } else {
                currentTarget = newTarget
                updateResultUI()
                Toast.makeText(this, "Đã cập nhật mục tiêu!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
