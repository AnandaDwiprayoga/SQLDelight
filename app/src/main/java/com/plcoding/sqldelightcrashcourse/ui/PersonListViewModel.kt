package com.plcoding.sqldelightcrashcourse.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.sqldelightcrashcourse.data.PersonDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import persondb.PersonEntity
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val datasource: PersonDataSource
): ViewModel(){
    val persons = datasource.getAllPersons()

    var personDetails by mutableStateOf<PersonEntity?>(null)
        private set

    var firstNameText by mutableStateOf("")
        private set

    var lastNameText by mutableStateOf("")
        private set

    fun onInsertPersonClick(){
        if(firstNameText.isBlank() || lastNameText.isBlank()){
            return
        }

        viewModelScope.launch {
            datasource.insertPerson(firstNameText, lastNameText)
            firstNameText = ""
            lastNameText = ""
        }
    }

    fun onDeleteClick(id: Long){
        viewModelScope.launch {
            datasource.deletePersonById(id)
        }
    }

    fun getPersonById(id: Long){
        viewModelScope.launch {
            personDetails = datasource.getPersonById(id)
        }
    }

    fun onFirstNameChange(value: String){
        firstNameText = value
    }

    fun onLastNameChange(value: String){
        lastNameText = value
    }

    fun onPersonDetailsDialogDismiss(){
        personDetails = null
    }
}