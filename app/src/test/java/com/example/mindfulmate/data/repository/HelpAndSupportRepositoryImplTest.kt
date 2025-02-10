package com.example.mindfulmate.data.repository

import com.example.mindfulmate.data.service.help_support.HelpAndSupportService
import com.example.mindfulmate.data.repository.help_support.HelpAndSupportRepositoryImpl
import com.example.mindfulmate.domain.model.help_support.FAQModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HelpAndSupportRepositoryImplTest {

    private lateinit var helpAndSupportService: HelpAndSupportService
    private lateinit var helpAndSupportRepository: HelpAndSupportRepositoryImpl

    @Before
    fun setUp() {
        helpAndSupportService = mockk()
        helpAndSupportRepository = HelpAndSupportRepositoryImpl(helpAndSupportService)
    }

    @Test
    fun `getFAQ calls service method and returns result`() = runBlocking {
        // Arrange
        val faqs = listOf(FAQModel("1", "FAQ 1", listOf()))
        coEvery { helpAndSupportService.getFAQ() } returns faqs

        // Act
        val result = helpAndSupportRepository.getFAQ()

        // Assert
        coVerify { helpAndSupportService.getFAQ() }
        assertEquals(faqs, result)
    }
}
