package com.example.mindfulmate.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.mindfulmate.data.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    fun isUserSignedIn(): Boolean {
        return accountService.hasUser()
    }
}