package com.example.doanltdd

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doanltdd.Data.Database.db_MonAnGoiY
import com.example.doanltdd.Data.Dao.dao_MonAnGoiY
import com.example.doanltdd.Data.Entity.Monan
import com.example.doanltdd.Data.Entity.entity_MonAnGoiY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class mon_an_goi_y : AppCompatActivity() {
    private lateinit var edtTen: EditText
    private lateinit var edtCalo: EditText
    private lateinit var spBuaAn: Spinner
    private lateinit var spLoaiMonAn: Spinner
    private lateinit var btnThem: Button
    private lateinit var btnXoa: Button
    private lateinit var btnSua: Button
    private lateinit var gvMonAn: GridView

    private lateinit var db: db_MonAnGoiY
    private lateinit var dao_MonAnGoiY: dao_MonAnGoiY

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mon_an_goi_y)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setControl()
        setEvent()
        setSPBuaAn()
        setSPLoaiMonAn()
    }

    fun setControl() {
        edtTen = findViewById(R.id.edtTenMonAn)
        edtCalo = findViewById(R.id.edtCalo)
        btnThem = findViewById(R.id.btnThem)
        btnSua = findViewById(R.id.btnSua)
        btnXoa = findViewById(R.id.btnXoa)
        // gvMonAn = findViewById(R.id.gvMonAnGoiY)
        spLoaiMonAn = findViewById(R.id.spLoaiMonAn)
        spBuaAn = findViewById(R.id.spBuaAn)
    }

    fun setEvent() {

        db = db_MonAnGoiY.getDatabase(this)
        val dao_MonAnGoiY = db.daoMonAnGoiY()
        btnThem.setOnClickListener {
            val ten = edtTen.text.toString().trim()
            val calo = edtCalo.text.toString().trim().toIntOrNull()?:0
            val buaAn = spBuaAn.selectedItem.toString()
            val loai = spLoaiMonAn.selectedItem.toString()
            val monAn =entity_MonAnGoiY(tenMonAnGoiY = ten, buaAn = buaAn, loaiMonAn = loai, calories = calo)
            GlobalScope.launch(Dispatchers.IO) {
                dao_MonAnGoiY.insert(monAn)
            }
        }


    }

    private fun loadMonAn() {

        GlobalScope.launch(Dispatchers.IO) {
            val monAn = dao_MonAnGoiY.dsMonAnGoiY()
            withContext(Dispatchers.Main) {

            }
        }
    }

    private fun setSPBuaAn() {
        val mealTypes = arrayOf("Bữa sáng", "Bữa trưa", "Bữa tối", "Bữa phụ")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mealTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spBuaAn.adapter = adapter
    }

    private fun setSPLoaiMonAn() {
        val mealTypes = arrayOf("Giảm cân", "Tăng cân", "Giữ cân")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mealTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spLoaiMonAn.adapter = adapter
    }


}