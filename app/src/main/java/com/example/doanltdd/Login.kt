package com.example.doanltdd

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Login : AppCompatActivity() {
    private lateinit var edtTaiKhoan: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var btnDangNhap: Button
    private lateinit var btnDangKy: Button
    private lateinit var btnQuenMatKhau: Button
    private lateinit var btnTroLai: Button

    //    lateinit var dsUser: MutableList<User>
    private lateinit var builder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        setControl()
        setEvent()
    }

    fun setControl() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan)
        edtMatKhau = findViewById(R.id.edtMatKhau)
        btnDangNhap = findViewById(R.id.btnDangNhap)
        btnDangKy = findViewById(R.id.btnDangky)
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau)
        btnTroLai = findViewById(R.id.btnTroLai)
        builder = AlertDialog.Builder(this)
    }

    fun setEvent() {
        val u = User("1nhm00", "12345678")
//        var dsUser = mutableListOf<User>()
//        dsUser.add(u)
        btnDangKy.setOnClickListener {
            val intent= Intent(this, register::class.java)
            startActivity(intent)

        }
        btnQuenMatKhau.setOnClickListener {
            val intent= Intent(this, forgotpass::class.java)
            startActivity(intent)

        }
        btnDangNhap.setOnClickListener {

            if (u.getTK() == edtTaiKhoan.text.toString().trim()) {
                if (u.getMK() == edtMatKhau.text.toString().trim()) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    setContentView(R.layout.activity_main)
                } else {
                    Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(this, "Tài khoản không tồn tại ", Toast.LENGTH_SHORT).show()
            }

        }
        btnTroLai.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
