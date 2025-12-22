package com.example.doanltdd.Login

import android.annotation.SuppressLint
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
import com.example.doanltdd.R
import com.example.doanltdd.User

class forgotpass : AppCompatActivity() {
    private lateinit var edtTaiKhoan: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtMatKhauMoi: EditText
    private lateinit var edtOTP: EditText
    private lateinit var btnDoiMatKhau: Button
    private lateinit var btnTroLai: Button
    private lateinit var tvDangNhap: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgotpass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotpass)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setControl()
        setEvent()
    }

    fun setControl() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan)
        edtEmail = findViewById(R.id.edtEmail)
        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi)
        edtOTP = findViewById(R.id.edtOTP)
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau)
        btnTroLai = findViewById(R.id.btnTroLai)
        tvDangNhap = findViewById(R.id.tvDangNhap)
    }

    fun setEvent() {
        btnDoiMatKhau.setOnClickListener {
            val u = User("1nhm00", "12345678", "1nhm00@gmail.com")
//            var dsUser = mutableListOf<User>()
//            dsUser.add(u)

            if (u.getTK() == edtTaiKhoan.text.toString().trim()) {
                if (u.getEmail() == edtEmail.text.toString().trim()) {
                    if (edtMatKhauMoi.text.length >= 8 && edtMatKhauMoi.text.length <= 12) {
                        if (edtOTP.text.toString().trim() == "123") {
                            u.setMK(edtMatKhauMoi.text.toString().trim())
                            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "Mã OTP không chính xác", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Mật khẩu từ 8 đến 12 kí tự", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Email không chính xác", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show()
            }
        }
        btnTroLai.setOnClickListener {
            val intent = Intent(this, com.example.doanltdd.Login.Login::class.java)
            startActivity(intent)
        }
        tvDangNhap . setOnClickListener {
            val intent = Intent(this, com.example.doanltdd.Login.Login::class.java)
            startActivity(intent)
        }

    }
}