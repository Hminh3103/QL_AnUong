package com.example.doanltdd.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.doanltdd.Data.Entity.Monan

@Dao
interface MonAnDao {

    @Insert
    suspend fun insertMonAn(monan: Monan)

    @Update
    suspend fun updateMonAn(monan: Monan)

    @Delete
    suspend fun deleteMonAn(monan: Monan)

    // Lấy toàn bộ danh sách món ăn
    @Query("SELECT * FROM tb_monan")
    suspend fun getAllMonAn(): List<Monan>

    // Lấy món ăn theo bữa (ví dụ: Sáng, Trưa, Tối)
    @Query("SELECT * FROM tb_monan WHERE buaan = :buaan")
    suspend fun getMonAnByBua(buaan: String): List<Monan>

    // Xóa toàn bộ dữ liệu trong bảng món ăn
    @Query("DELETE FROM tb_monan")
    suspend fun deleteAllMonAn()

    // Tìm kiếm món ăn theo tên (Gần đúng)
    @Query("SELECT * FROM tb_monan WHERE tenmonan LIKE '%' || :tenMon || '%'")
    suspend fun searchMonAn(tenMon: String): List<Monan>

    // Tính tổng số calo của các món đã ăn
    @Query("SELECT SUM(socalo) FROM tb_monan")
    suspend fun getTotalCalo(): Int?
}