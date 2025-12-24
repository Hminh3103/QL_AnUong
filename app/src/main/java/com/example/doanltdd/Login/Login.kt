package com.example.doanltdd.Login

import android.app.AlertDialog
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
import com.example.doanltdd.MainActivity
import com.example.doanltdd.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.doanltdd.Data.Dao.UserDao
import com.example.doanltdd.Data.Database.AppDatabase
import com.example.doanltdd.Data.Entity.User
import com.example.doanltdd.MainDoAn
import com.example.doanltdd.User_SharedPreferences
import com.example.doanltdd.main_user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    private lateinit var edtTaiKhoan: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var btnDangNhap: Button
    private lateinit var tvDangKy: TextView
    private lateinit var btnTroLai: Button
    private lateinit var tvQuenMatKhau: TextView

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
        tvDangKy = findViewById(R.id.tvDangKy)
        btnTroLai = findViewById(R.id.btnTroLai)
        builder = AlertDialog.Builder(this)
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau)
    }

    fun setEvent() {
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        tvDangKy.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)

        }
        tvQuenMatKhau.setOnClickListener {
            val intent = Intent(this, forgotpass::class.java)
            startActivity(intent)

        }
        btnDangNhap.setOnClickListener {
            val taiKhoan = edtTaiKhoan.text.toString().trim()
            val matKhau = edtMatKhau.text.toString().trim()
            if (taiKhoan.isNotBlank() && matKhau.isNotBlank()) {
                GlobalScope.launch {
//                    val checkLogin = userDao.isLoginValid(taiKhoan, matKhau)
                    val user = userDao.login(taiKhoan, matKhau)
                    if (user != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login, "Đăng nhập thành công", Toast.LENGTH_SHORT)
                                .show()
                            val data = User_SharedPreferences(this@Login)
                            data.saveLogin(user)
                            if (user.role == "admin") {
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                            } else if (user.role == "user") {
                                val intent = Intent(this@Login, main_user::class.java)
                                startActivity(intent)
                            }

                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Login,
                                "Sai tài khoản hoặc mật khẩu",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Hãy nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        btnTroLai.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}