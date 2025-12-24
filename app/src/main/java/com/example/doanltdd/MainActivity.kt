package com.example.doanltdd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doanltdd.Login.Login

class MainActivity : AppCompatActivity() {
    private lateinit var btnListBuaAn: Button
    private lateinit var btnQuanLiThoiQuen: Button
    private lateinit var btnCalo: Button
    private lateinit var btnDangXuat: Button
    private lateinit var tvRole: TextView

    // private lateinit var tvRole1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setControl()
        setEvent()
    }

    private fun setControl() {
        btnListBuaAn = findViewById(R.id.btnListBuaAn)
        btnQuanLiThoiQuen = findViewById(R.id.btnQuanLiThoiQuen)
        btnCalo = findViewById(R.id.btnCalo)
        btnDangXuat = findViewById(R.id.btnDangXuat)
        tvRole = findViewById(R.id.tvRole)
        //  tvRole1=findViewById(R.id.textView16)
    }

    private fun setEvent() {
        val data = User_SharedPreferences(this@MainActivity)
        tvRole.setText(data.getRole())
        // tvRole1.setText(data.getUserId().toString())
        btnListBuaAn.setOnClickListener {
            val intent = Intent(this, MainDoAn::class.java)
            startActivity(intent)

        }
        btnQuanLiThoiQuen.setOnClickListener {
            val intent = Intent(this, Main_GiuaKy::class.java)
            startActivity(intent)
        }
        btnCalo.setOnClickListener {
            val intent = Intent(this, TheoDoiCalo::class.java)
            startActivity(intent)
        }
        btnDangXuat.setOnClickListener {
            data.logout()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}