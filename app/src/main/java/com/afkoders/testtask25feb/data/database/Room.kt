package com.afkoders.testtask25feb.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

@Dao
interface UsersDao {
    @Query("select * from usersDb")
    fun getUsers(): LiveData<List<UserDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buttonActions: List<UserDbModel>)

}

@Database(entities = [UserDbModel::class], version = 1)
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
