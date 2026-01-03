package nl.codecraft.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.model.Owner
import nl.codecraft.model.Repo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserReposUseCaseTest {

    private val mockRepository: GitHubRepository = mockk()
    private val useCase = GetUserReposUseCase(mockRepository)

    @Test
    fun `invoke should return flow of repos from repository`() = runTest {
        // Given
        val username = "testuser"
        val expectedRepos = listOf(
            Repo(
                id = 1,
                name = "repo1",
                fullName = "testuser/repo1",
                description = "Test repository 1",
                htmlUrl = "https://github.com/testuser/repo1",
                stargazersCount = 10,
                forksCount = 2,
                language = "Kotlin",
                owner = Owner(
                    id = 1,
                    login = "testuser",
                    avatarUrl = "https://avatar.url",
                    htmlUrl = "https://github.com/testuser",
                    type = "User"
                ),
                updatedAt = "2024-01-01T00:00:00Z",
                isPrivate = false,
                isFork = false
            ),
            Repo(
                id = 2,
                name = "repo2",
                fullName = "testuser/repo2",
                description = "Test repository 2",
                htmlUrl = "https://github.com/testuser/repo2",
                stargazersCount = 5,
                forksCount = 1,
                language = "Java",
                owner = Owner(
                    id = 1,
                    login = "testuser",
                    avatarUrl = "https://avatar.url",
                    htmlUrl = "https://github.com/testuser",
                    type = "User"
                ),
                updatedAt = "2024-01-02T00:00:00Z",
                isPrivate = false,
                isFork = true
            )
        )

        coEvery { mockRepository.getUserRepos(username) } returns flowOf(expectedRepos)

        // When
        val resultFlow = useCase(username)
        val result = mutableListOf<List<Repo>>()
        resultFlow.collect { result.add(it) }

        // Then
        assertEquals(1, result.size)
        assertEquals(expectedRepos, result[0])
    }

    @Test
    fun `invoke should return empty flow when repository returns empty list`() = runTest {
        // Given
        val username = "emptyuser"
        coEvery { mockRepository.getUserRepos(username) } returns flowOf(emptyList())

        // When
        val resultFlow = useCase(username)
        val result = mutableListOf<List<Repo>>()
        resultFlow.collect { result.add(it) }

        // Then
        assertEquals(1, result.size)
        assertEquals(emptyList<Repo>(), result[0])
    }

    @Test
    fun `invoke should propagate error from repository`() = runTest {
        // Given
        val username = "erroruser"
        val expectedException = RuntimeException("Network error")
        coEvery { mockRepository.getUserRepos(username) } throws expectedException

        // When & Then
        try {
            useCase(username).collect { }
            throw AssertionError("Expected exception to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(expectedException.message, e.message)
        }
    }
}
