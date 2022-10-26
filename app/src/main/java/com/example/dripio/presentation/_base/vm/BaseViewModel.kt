package com.example.dripio.presentation._base.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private var _loading = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading

    fun launch(errorBlock: ((Throwable) -> Unit)? = null, block: suspend () -> Unit) {
        _loading.value = true
        viewModelScope.launch {
            try {
                block.invoke()
                _loading.postValue(true)
            } catch(ex: Throwable) {
                Log.e("BASE_VIEW_MODEL_ERROR", ex.toString())
                errorBlock?.invoke(ex)
                _loading.postValue(true)
            }
        }
    }
}