package com.kareemdev.latihanpagingnetwork.ui

import android.content.Context
import androidx.lifecycle.*
import com.kareemdev.latihanpagingnetwork.data.QuoteRepository
import com.kareemdev.latihanpagingnetwork.di.Injection
import com.kareemdev.latihanpagingnetwork.network.QuoteResponseItem
import kotlinx.coroutines.launch

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {
    private val _quote = MutableLiveData<List<QuoteResponseItem>>()
    var quote: LiveData<List<QuoteResponseItem>> = _quote

    fun getQuote() {
        viewModelScope.launch {
            _quote.postValue(quoteRepository.getQuote())
        }
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}