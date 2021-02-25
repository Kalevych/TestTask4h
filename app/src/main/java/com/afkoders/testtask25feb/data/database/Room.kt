package com.afkoders.testtask25feb.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.nio.file.Files.delete


/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

@Dao
interface UsersDao {
    @Query("select * from usersDb")
    fun getUsers(): LiveData<List<UserDbModel>>

    @Query("SELECT * FROM usersDb WHERE $USER_ID_COLUMN_NAME = :id")
    fun getUser(id: String): UserDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDbModel)

    @Query("DELETE FROM usersDb WHERE $USER_ID_COLUMN_NAME = :id")
    fun deleteUser(id: String)

    @Query("DELETE FROM usersDb")
    @Transaction
    fun deleteUsers()

    @Transaction
    fun deleteAndInsertAll(users: List<UserDbModel>) {
        deleteUsers()
        insertAll(users)
    }
}

@Database(entities = [UserDbModel::class], version = 2)
abstract class UsersDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}

private lateinit var INSTANCE: UsersDatabase

fun getDatabase(context: Context): UsersDatabase {
    synchronized(UsersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                UsersDatabase::class.java,
                "users"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
