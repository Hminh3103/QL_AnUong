package com.example.doanltdd.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.doanltdd.Data.Entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("""
    UPDATE tb_users
    SET 
        password = :password
    WHERE username = :username
""")
    suspend fun update(
        username: String,
        password: String,
    ): Int

    @Query("SELECT * FROM tb_users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?
    @Query("SELECT * FROM tb_users WHERE username = :username")
    suspend fun checkTaiKhoan(username: String): User?

    @Query("SELECT * FROM tb_users")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM tb_users")
    suspend fun deleteAll()

    @Query(
        """
    SELECT EXISTS(
        SELECT 1 FROM tb_users
        WHERE username = :username
        AND password = :password
    )
"""
    )
    suspend fun isLoginValid(username: String, password: String): Boolean
    @Query(
        """
    SELECT EXISTS(
        SELECT 1 FROM tb_users
        WHERE username = :username
        AND email = :email
    )
"""
    )
    suspend fun isForgotPassValid(username: String, email: String): Boolean

}
