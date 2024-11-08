package com.example.information_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.information_app.R
import com.example.information_app.di.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(version = 1, entities = [Question::class], exportSchema = false)
abstract class QuestionDatabase : RoomDatabase() {

    // get access to DAO, auto generated by Room
    abstract fun getDao(): QuestionDao

    class Callback @Inject constructor(
        private val database: Provider<QuestionDatabase>, // lazy init until this Database created
        private val repository: Provider<QuestionRepository>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        private lateinit var dao: QuestionDao

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            dao = database.get().getDao()

            //initDatabase()
            val repository = repository.get()

            applicationScope.launch {
                repository.initDatabase()
            }
        }
    }
}