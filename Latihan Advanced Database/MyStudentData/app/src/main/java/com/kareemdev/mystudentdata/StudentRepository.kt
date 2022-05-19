package com.kareemdev.mystudentdata

import androidx.lifecycle.LiveData
import com.kareemdev.mystudentdata.database.*
import com.kareemdev.mystudentdata.helper.InitialDataSource
import com.kareemdev.mystudentdata.helper.SortType
import com.kareemdev.mystudentdata.helper.SortUtils

class StudentRepository(private val studentDao: StudentDao) {
    /*fun getAllStudent(): LiveData<List<Student>> = studentDao.getAllStudent()*/
    fun getAllStudent(sortType: SortType): LiveData<List<Student>> {
        val query = SortUtils.getSortedQuery(sortType)
        return studentDao.getAllStudent(query)
    }

    suspend fun insertAllData(){
        studentDao.insertStudent(InitialDataSource.getStudents())
        studentDao.insertUniversity(InitialDataSource.getUniversities())
        studentDao.insertCourse(InitialDataSource.getCourses())
        studentDao.insertCourseStudentCrossRef((InitialDataSource.getCourseStudentRelation()))


    }

    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentDao.getAllStudentWithCourse()

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentDao.getAllStudentAndUniversity()

    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentDao.getAllUniversityAndStudent()
}