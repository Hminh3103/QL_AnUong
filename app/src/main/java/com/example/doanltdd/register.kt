package com.example.doanltdd

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class register : AppCompatActivity() {
    private lateinit var edtTaiKhoan: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnDangKy: Button
    private lateinit var btnTroLai: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setConTrol()
        setEvent()
    }

    fun setConTrol() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan)
        edtMatKhau = findViewById(R.id.edtMatKhau)
        edtEmail = findViewById(R.id.edtEmail)
        btnTroLai = findViewById(R.id.btnTroLai)
        btnDangKy = findViewById(R.id.btnDangky)
    }

    fun setEvent() {
        btnDangKy.setOnClickListener {
            val e = edtEmail.text.toString().trim()
            if (edtTaiKhoan.text.length == 6) {
                if (edtMatKhau.text.length >= 8 && edtMatKhau.text.length <= 12) {
                    if (e.contains("@gmail.com", ignoreCase = false)) {
                        Toast.makeText(this, "Tạo tài khoản thành công", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu không hợp lệ", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Tài khoản không hợp lệ", Toast.LENGTH_LONG).show()
            }
        }
        btnTroLai.setOnClickListener {
            setContentView(R.layout.activity_login)

        }
    }


}
