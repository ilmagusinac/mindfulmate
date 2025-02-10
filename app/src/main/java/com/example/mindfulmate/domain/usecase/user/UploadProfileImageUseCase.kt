package com.example.mindfulmate.domain.usecase.user

import android.net.Uri
import com.example.mindfulmate.domain.repository.user.UserRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(imageUri: Uri): String {
        return repository.uploadProfileImage(imageUri)
    }
}
