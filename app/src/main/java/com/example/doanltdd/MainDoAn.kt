package com.example.doanltdd

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.doanltdd.R

class MainDoAn : AppCompatActivity() {

    private lateinit var edtTenMon: EditText
    private lateinit var spBuaAn: Spinner
    private lateinit var edtCalo: EditText
    private lateinit var btnThem: Button
    private lateinit var btnThoat: Button
    private lateinit var lvDanhSach: ListView

    private lateinit var adapterList: ArrayAdapter<String>
    private val ds = mutableListOf<String>()

    private var viTriDangSua = -1  // -1 nghĩa là không đang sửa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_do_an)

        setControl()
        setEvent()
    }

    private fun setControl() {
        edtTenMon = findViewById(R.id.edtTenMon)
        spBuaAn = findViewById(R.id.spBuaAn)
        edtCalo = findViewById(R.id.edtCalo)
        btnThem = findViewById(R.id.btnThem)
        btnThoat = findViewById(R.id.btnThoat)
        lvDanhSach = findViewById(R.id.lvDanhSach)
    }

    private fun setEvent() {
        // 1. Spinner dữ liệu
        val dsBuaAn = listOf("Sáng", "Trưa", "Tối", "Ăn nhẹ")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, dsBuaAn)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spBuaAn.adapter = adapterSpinner  // BẮT BUỘC phải set adapter

        // 2. ListView
        adapterList = ArrayAdapter(this, android.R.layout.simple_list_item_1, ds)
        lvDanhSach.adapter = adapterList

        // 3. Nút Thêm/Sửa
        btnThem.setOnClickListener {
            themHoacSuaMonAn()
        }

        // 4. Click item → Sửa
        lvDanhSach.setOnItemClickListener { _, _, position, _ ->
            suaMonAn(position)
        }

        // 5. Long click → Xóa
        lvDanhSach.setOnItemLongClickListener { _, _, position, _ ->
            xoaMonAn(position)
            true
        }
        btnThoat.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Thêm món hoặc lưu món đang sửa
    private fun themHoacSuaMonAn() {
        val ten = edtTenMon.text.toString().trim()
        val buaAn = spBuaAn.selectedItem.toString()
        val calo = edtCalo.text.toString().trim()

        if (ten.isEmpty() || calo.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show()
            return
        }

        val text = "$ten - $buaAn - $calo calo"

        if (viTriDangSua >= 0) {
            // Lưu món đang sửa
            ds[viTriDangSua] = text
            adapterList.notifyDataSetChanged()
            viTriDangSua = -1
            btnThem.text = "Thêm"
            Toast.makeText(this, "Đã sửa món ăn!", Toast.LENGTH_SHORT).show()
        } else {
            // Thêm mới
            ds.add(text)
            adapterList.notifyDataSetChanged()
            Toast.makeText(this, "Đã thêm món ăn!", Toast.LENGTH_SHORT).show()
        }

        edtTenMon.setText("")
        edtCalo.setText("")
    }

    // Sửa món: điền thông tin vào form
    private fun suaMonAn(position: Int) {
        val text = ds[position]
        val parts = text.split(" - ")
        if (parts.size == 3) {
            edtTenMon.setText(parts[0])
            edtCalo.setText(parts[2].replace(" calo", ""))
            val buaAn = parts[1]
            val index = (spBuaAn.adapter as ArrayAdapter<String>).getPosition(buaAn)
            spBuaAn.setSelection(index)
        }
        viTriDangSua = position
        btnThem.text = "Lưu"
    }

    // Xóa món ăn
    private fun xoaMonAn(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận xóa")
        builder.setMessage("Bạn có chắc muốn xóa món này không?")
        builder.setPositiveButton("Có") { _, _ ->
            ds.removeAt(position)
            adapterList.notifyDataSetChanged()
            Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Không", null)
        builder.show()
    }
}
