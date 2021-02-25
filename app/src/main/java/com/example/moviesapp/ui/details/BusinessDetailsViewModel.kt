package com.example.moviesapp.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.moviesapp.data.repository.BusinessesRepository
import com.example.moviesapp.model.BusinessDetailsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BusinessDetailsViewModel @ViewModelInject constructor(
    private val businessesRepository: BusinessesRepository
) : ViewModel() {
    private val _businessesLiveData = MutableLiveData<BusinessDetailsResponse>()

    val businessesLiveData: LiveData<BusinessDetailsResponse>
        get() = _businessesLiveData




    fun getBusinessById(id: String) {
        viewModelScope.launch {
            _businessesLiveData.value = businessesRepository.fetchBusinessDetailsFromRemote(id)
        }
    }


}
