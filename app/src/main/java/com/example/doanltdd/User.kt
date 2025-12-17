package com.example.doanltdd

import android.content.Context
import android.widget.Toast

class User() {
    private lateinit var taiKhoan: String
    private lateinit var matKhau: String
    private lateinit var email: String

    //Constructor
    constructor(tk: String, mk: String, e: String) : this() {
        this.taiKhoan = tk
        this.matKhau = mk
        this.email = e
    }
    constructor(tk: String,mk: String) : this() {
        this.taiKhoan=tk
        this.matKhau=mk
    }

    //Properties
    fun setTK(tk: String) {
        this.taiKhoan = tk
    }

    fun setMK(mk: String) {
        this.taiKhoan = mk
    }

    fun setEmail(e: String) {
        this.email = e
    }

    fun getTK(): String {
        return this.taiKhoan.toString()
    }

    fun getMK(): String {
        return this.matKhau.toString()
    }

    fun getEmail(): String {
        return this.email.toString()
    }

    //Method
//    fun dangNhap(context: Context, tk: String, mk: String) {
//        if (taiKhoan == tk) {
//            if (matKhau == mk) {
//                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(context, "Tài khoản không tồn tại ", Toast.LENGTH_SHORT).show()
//        }
//    }

}
