package com.randomthings.presentation.jokes

import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesContentUsecase: JokesContentUsecase,
) : BaseViewModel() {

}
