package com.kareemdev.mystudentdata

import android.app.Application
import com.kareemdev.mystudentdata.database.StudentDatabase

class MyApplication : Application() {
    val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}