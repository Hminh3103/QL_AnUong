package com.example.doanltdd

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class mon_an_goi_y : AppCompatActivity() {
    private lateinit var edtTen: EditText
    private lateinit var edtCalo: EditText
    private lateinit var spBuaAn: Spinner
    private lateinit var spLoaiMonAn: Spinner
    private lateinit var btnThem: Button
    private lateinit var btnXoa: Button
    private lateinit var btnSua: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mon_an_goi_y)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}