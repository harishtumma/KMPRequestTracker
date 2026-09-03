package com.example.kmprequesttracker.domain.usecase

import com.example.kmprequesttracker.domain.model.RequestStatus
import com.example.kmprequesttracker.domain.model.UserRequest
import com.example.kmprequesttracker.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetRequestsUseCaseTest {

    private class FakeRequestRepository(private val shouldFail: Boolean = false) : RequestRepository {
        override fun getRequests(): Flow<List<UserRequest>> = flow {
            if (shouldFail) throw Exception("Test error")
            emit(listOf(
                UserRequest("1", "Title", "Desc", RequestStatus.PENDING, Clock.System.now(), emptyList(), emptyList())
            ))
        }

        override fun getRequestById(id: String): Flow<UserRequest?> = flow {
            emit(null)
        }
    }

    @Test
    fun `when invoke called, returns list of requests from repository`() = runTest {
        // Given
        val repo = FakeRequestRepository()
        val useCase = GetRequestsUseCase(repo)

        // When
        val result = useCase().first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Title", result[0].title)
    }

    @Test
    fun `when repository fails, use case propagates error`() = runTest {
        // Given
        val repo = FakeRequestRepository(shouldFail = true)
        val useCase = GetRequestsUseCase(repo)

        // When/Then
        val result = runCatching { useCase().first() }
        
        assertTrue(result.isFailure)
        assertEquals("Test error", result.exceptionOrNull()?.message)
    }
}
