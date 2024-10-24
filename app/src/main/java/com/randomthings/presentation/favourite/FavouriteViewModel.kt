package com.randomthings.presentation.favourite

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.randomthings.domain.content.ContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val contentUseCase: ContentUseCase,
) : BaseViewModel() {

    companion object {
        const val TAG = "FavouriteViewModel"
    }

    private val _favouriteContents = mutableStateListOf<ImageContent>()

    val favouriteContents: List<ImageContent> = _favouriteContents

    fun fetchFavouriteContents() {
        _favouriteContents.clear()
        viewModelScope.launch {
            contentUseCase.getAllFavouriteContents()
                .catch { e->
                    Log.e("ERROR", e.message.orEmpty());
                }
                .collect {
                    _favouriteContents.addAll(it)
                }
        }
    }

}
