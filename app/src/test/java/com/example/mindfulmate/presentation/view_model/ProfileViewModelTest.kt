package com.example.mindfulmate.presentation.view_model

import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.usecase.daily_checkin.GetDailyCheckInsUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileParams
import com.example.mindfulmate.presentation.view_model.profile.ProfileUiState
import com.example.mindfulmate.presentation.view_model.profile.ProfileViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var getDailyCheckInsUseCase: GetDailyCheckInsUseCase
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserUseCase = mockk()
        getDailyCheckInsUseCase = mockk()
        viewModel = ProfileViewModel(getUserUseCase, getDailyCheckInsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUser updates uiState to Success with profileParams`() = runTest {
        val mockUser = User(
            id = "1",
            firstName = "John",
            lastName = "Doe",
            username = "johndoe",
            email = "john@example.com",
            number = "123456789"
        )
        coEvery { getUserUseCase() } returns mockUser

        viewModel.loadUser()
        advanceUntilIdle()

        coVerify { getUserUseCase() }
        val state = viewModel.uiState.first() as ProfileUiState.Success
        assertEquals(
            ProfileParams(
                firstName = mockUser.firstName,
                lastName = mockUser.lastName,
                username = mockUser.username,
                email = mockUser.email,
                number = mockUser.number
            ),
            state.profileParams
        )
    }

    @Test
    fun `loadUser updates uiState to Failure on exception`() = runTest {
        val errorMessage = "Failed to load user"
        coEvery { getUserUseCase() } throws RuntimeException(errorMessage)

        viewModel.loadUser()
        advanceUntilIdle()

        coVerify { getUserUseCase() }
        val state = viewModel.uiState.first() as ProfileUiState.Failure
        assertEquals(errorMessage, state.message)
    }

    @Test
    fun `fetchLastDailyCheckIn emits latest check-in date`() = runTest {
        val checkIns = listOf(
            Pair("2025-01-19", 4.5f),
            Pair("2025-01-20", 5.0f)
        )
        coEvery { getDailyCheckInsUseCase() } returns checkIns

        viewModel.fetchLastDailyCheckIn()
        advanceUntilIdle() // Ensure coroutines complete

        coVerify { getDailyCheckInsUseCase() }
        assertEquals("2025-01-20", viewModel.lastDailyCheckIn.first())
    }

    @Test
    fun `fetchLastDailyCheckIn emits null on exception`() = runTest {
        coEvery { getDailyCheckInsUseCase() } throws RuntimeException()

        viewModel.fetchLastDailyCheckIn()
        advanceUntilIdle() // Ensure coroutines complete

        coVerify { getDailyCheckInsUseCase() }
        assertNull(viewModel.lastDailyCheckIn.first())
    }
}
