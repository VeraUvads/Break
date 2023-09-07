package com.uva.fastapp.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uva.fastapp.AppContainer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val photos: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(
            CoroutineExceptionHandler { coroutineContext, throwable ->
                Log.e("quick", throwable.message!!)
            }
        ) {
            withContext(Dispatchers.IO) {
                val answer = AppContainer.repository.uploadCats().map { it.url }
                photos.emit(answer)
            }
        }
    }
}
