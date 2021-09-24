package ru.alexapps.personaldictionary.worddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.data.repository.IBncWordRepository
import ru.alexapps.personaldictionary.data.repository.IWordRepository
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(private val wordRepository: IWordRepository, private val bncWordRepository: IBncWordRepository) :
    ViewModel() {
    private val _wordsLiveData: MutableLiveData<List<Word>> = MutableLiveData(emptyList())
    val wordsLiveData: LiveData<List<Word>> = _wordsLiveData
    private val _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    private var currentWord: String? = null
    init {
        refresh()
    }

    private fun fetchWord(word: String) {
        if (word != currentWord) {
            currentWord = word
            viewModelScope.launch {
                _loadingLiveData.value = true
                _wordsLiveData.value = wordRepository.fetchWord(word)
                _loadingLiveData.value = false
            }
        }
    }
    fun refresh() {
        viewModelScope.launch {
            val words = bncWordRepository.getRandomBncWords(1, 10, 1)
            if(words.isNotEmpty()) {
                fetchWord(words[0].word)
            }
        }
    }
}