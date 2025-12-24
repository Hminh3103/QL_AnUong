package com.example.doanltdd.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doanltdd.Data.Database.AppDatabase
import com.example.doanltdd.Data.Entity.User
import com.example.doanltdd.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class register : AppCompatActivity() {
    private lateinit var edtTaiKhoan: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnDangKy: Button
    private lateinit var btnTroLai: Button
    private lateinit var tvDangNhap: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
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
        tvDangNhap = findViewById(R.id.tvDangNhap)
    }

    fun setEvent() {
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()
        btnDangKy.setOnClickListener {
            val e = edtEmail.text.toString().trim()
            if (edtTaiKhoan.text.length == 6) {
                if (edtMatKhau.text.length >= 8 && edtMatKhau.text.length <= 12) {
                    if (e.contains("@gmail.com", ignoreCase = false)) {
                        GlobalScope.launch {
                            val user = User(
                                username = edtTaiKhoan.text.toString(),
                                password = edtMatKhau.text.toString(),
                                email = edtEmail.text.toString(),
                                role = "user"
                            )
                            userDao.insertUser(user)
                            withContext(Dispatchers.Main){
                                Toast.makeText(this@register, "Tạo tài khoản thành công", Toast.LENGTH_LONG).show()
                            }
                        }

                    } else {
                        Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu từ 8 đến 12 kí tự", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Tài khoản gồm 6 kí tự", Toast.LENGTH_LONG).show()
            }
        }
        btnTroLai.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
        tvDangNhap.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


}