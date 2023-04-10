package com.example.smarthome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel:ViewModel() {
    private val repository = SharedRepository()

    private val _arrayListLiveData = MutableLiveData<ArrayList<Command>>()
    val arrayListLiveData:LiveData<ArrayList<Command>> = _arrayListLiveData

    private val _arrayListLiveDataIntent = MutableLiveData<ArrayList<IntentClass>>()
    val arrayListLiveDataIntent:LiveData<ArrayList<IntentClass>> = _arrayListLiveDataIntent

    private val _detailIntent = MutableLiveData<IntentClass>()
    val detailIntent:LiveData<IntentClass> = _detailIntent


    fun refreshCommandList(token:String){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.getCommandList(token)
            _arrayListLiveData.postValue(response)
        }
    }


    fun refreshIntentList(token: String){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.getIntentList(token)
            _arrayListLiveDataIntent.postValue(response)
        }
    }


    fun refreshDetailIntent(token: String, code:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDetailIntent(token, code)
            _detailIntent.postValue(response)
        }
    }


}