package com.example.mindfulmate.data.repository

import com.example.mindfulmate.data.service.auth.AccountService
import com.example.mindfulmate.data.service.user.UserService
import com.example.mindfulmate.data.repository.user.UserRepositoryImpl
import com.example.mindfulmate.domain.model.user.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var accountService: AccountService
    private lateinit var userService: UserService
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        accountService = mockk()
        userService = mockk()
        userRepository = UserRepositoryImpl(accountService, userService)
    }

    @Test
    fun `signIn calls account service method`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        coEvery { accountService.signIn(email, password) } returns Unit

        // Act
        userRepository.signIn(email, password)

        // Assert
        coVerify { accountService.signIn(email, password) }
    }

    @Test
    fun `getUser calls user service method and returns user`() = runBlocking {
        // Arrange
        val user = User("1", "John", "Doe", "johndoe", "12345", "john@example.com")
        coEvery { userService.getUser() } returns user

        // Act
        val result = userRepository.getUser()

        // Assert
        coVerify { userService.getUser() }
        assertEquals(user, result)
    }
}
