package com.kareemdev.mystudentdata

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.kareemdev.mystudentdata.database.Student
import com.kareemdev.mystudentdata.database.StudentAndUniversity
import com.kareemdev.mystudentdata.database.StudentWithCourse
import com.kareemdev.mystudentdata.database.UniversityAndStudent
import com.kareemdev.mystudentdata.helper.SortType
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(private val studentRepository: StudentRepository) : ViewModel() {
    private val _sort = MutableLiveData<SortType>()
    init {
        insertAllData()
        _sort.value = SortType.ASCENDING
    }

    fun changeSortType(sortType: SortType){
        _sort.value = sortType
    }

    /*fun getAllStudent(): LiveData<List<Student>> = studentRepository.getAllStudent()*/
    fun getAllStudent(): LiveData<PagedList<Student>> = _sort.switchMap {
        studentRepository.getAllStudent(it)
    }

    private fun insertAllData() = viewModelScope.launch {
        studentRepository.insertAllData()
    }

    class ViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentRepository.getAllStudentAndUniversity()

    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> =  studentRepository.getAllUniversityAndStudent()

    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentRepository.getAllStudentWithCourse()

}