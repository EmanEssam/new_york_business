package com.example.moviesapp.ui


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.repository.BusinessesRepository
import com.example.moviesapp.model.Businesses
import com.example.moviesapp.model.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for [MainActivity]
 */
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val businessRepository: BusinessesRepository) :
    ViewModel() {


    private val _businessesLiveData = MutableLiveData<State<List<Businesses>>>()

    val businessesLiveData: LiveData<State<List<Businesses>>>
        get() = _businessesLiveData


    fun getBusinesses() {
        viewModelScope.launch {
            businessRepository.getAllBusiness().collect {
                _businessesLiveData.value = it
            }
        }
    }
}
