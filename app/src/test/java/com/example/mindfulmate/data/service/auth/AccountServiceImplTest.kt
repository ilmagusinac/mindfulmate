package com.example.mindfulmate.data.service.auth

import com.example.mindfulmate.data.service.user.UserService
import com.example.mindfulmate.domain.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AccountServiceImplTest {

    private lateinit var accountService: AccountServiceImpl
    private val userService = mockk<UserService>()
    private val mockFirebaseAuth = mockk<FirebaseAuth>(relaxed = true)
    private val mockFirebaseUser = mockk<FirebaseUser>(relaxed = true)

    @Before
    fun setUp() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockFirebaseAuth

        // Mock FirebaseUser to avoid calling real methods
        every { mockFirebaseAuth.currentUser } returns mockFirebaseUser
        accountService = AccountServiceImpl(userService)
    }


    @Test
    fun `currentUser flow emits the correct user`() = runBlocking {
        // Arrange
        val expectedUser = User("123")
        every { mockFirebaseAuth.currentUser } returns mockFirebaseUser
        every { mockFirebaseUser.uid } returns "123"

        // Act
        val result = accountService.currentUser.first()

        // Assert
        assertEquals(expectedUser, result)
    }

    @Test
    fun `signIn calls FirebaseAuth signInWithEmailAndPassword`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        coEvery { mockFirebaseAuth.signInWithEmailAndPassword(email, password) } returns mockk()

        // Act
        accountService.signIn(email, password)

        // Assert
        coVerify { mockFirebaseAuth.signInWithEmailAndPassword(email, password) }
    }

    @Test
    fun `signUp adds user to UserService`() = runBlocking {
        // Arrange
        val email = "newuser@example.com"
        val password = "password123"
        val uid = "12345"
        every { mockFirebaseAuth.currentUser } returns mockFirebaseUser
        every { mockFirebaseUser.uid } returns uid
        every { mockFirebaseUser.email } returns email
        coEvery { mockFirebaseAuth.createUserWithEmailAndPassword(email, password) } returns mockk()
        coEvery { userService.addUser(any()) } just Runs

        // Act
        accountService.signUp(email, password)

        // Assert
        coVerify { userService.addUser(match { it.id == uid && it.email == email }) }
    }

    @Test
    fun `resetPassword sends email`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        coEvery { mockFirebaseAuth.sendPasswordResetEmail(email) } returns mockk()

        // Act
        accountService.resetPassword(email)

        // Assert
        coVerify { mockFirebaseAuth.sendPasswordResetEmail(email) }
    }

    @Test(expected = Exception::class)
    fun `deleteAccount throws exception if no user is logged in`() = runBlocking {
        // Arrange
        every { mockFirebaseAuth.currentUser } returns null

        // Act
        accountService.deleteAccount()

        // Assert
        // Exception is thrown
    }

    @Test
    fun `deleteAccount deletes current user`() = runBlocking {
        // Arrange
        every { mockFirebaseAuth.currentUser } returns mockFirebaseUser
        coEvery { mockFirebaseUser.delete() } just Awaits

        // Act
        accountService.deleteAccount()

        // Assert
        coVerify { mockFirebaseUser.delete() }
    }
}
