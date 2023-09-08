package com.uva.fastapp.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uva.fastapp.domain.CatRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsListViewModel(private val repository: CatRepository) : ViewModel() {

    val photos: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.e("quick", throwable.message!!)
            }
        ) {
            withContext(Dispatchers.IO) {
                val answer = repository.uploadCats(10).map { it.url }
                photos.emit(answer)
            }
        }
    }
}
