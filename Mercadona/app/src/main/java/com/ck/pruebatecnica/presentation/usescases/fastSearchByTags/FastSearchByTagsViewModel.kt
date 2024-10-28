package com.ck.pruebatecnica.presentation.usescases.fastSearchByTags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FastSearchByTagsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel(){
    private val _state = MutableStateFlow(FastSearchByTagsViewState())
    val state = _state.asStateFlow()

    fun toggleCategorySelection(category: String) {
        val updatedCategories = if (_state.value.selectedCategories.contains(category)) {
            _state.value.selectedCategories - category
        } else {
            _state.value.selectedCategories + category
        }
        _state.value = _state.value.copy(selectedCategories = updatedCategories)
    }

    fun updateCharacters(){
        viewModelScope.launch {
            characterRepository.updateCharacterFromApiToDb(1)
        }
    }

}