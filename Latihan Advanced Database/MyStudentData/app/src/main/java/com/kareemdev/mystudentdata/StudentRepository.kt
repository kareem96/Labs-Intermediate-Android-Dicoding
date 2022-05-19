package com.kareemdev.mystudentdata

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kareemdev.mystudentdata.database.*
import com.kareemdev.mystudentdata.helper.InitialDataSource
import com.kareemdev.mystudentdata.helper.SortType
import com.kareemdev.mystudentdata.helper.SortUtils

class StudentRepository(private val studentDao: StudentDao) {
    /*fun getAllStudent(): LiveData<List<Student>> = studentDao.getAllStudent()*/
    fun getAllStudent(sortType: SortType): LiveData<PagedList<Student>> {
        val query = SortUtils.getSortedQuery(sortType)
        val student = studentDao.getAllStudent(query)
        /*return studentDao.getAllStudent(query)*/

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(student, config).build()
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