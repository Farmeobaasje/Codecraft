package nl.codecraft.data.mapper

import nl.codecraft.data.local.RepoEntity
import nl.codecraft.data.remote.dto.OwnerDto
import nl.codecraft.data.remote.dto.RepoDto
import nl.codecraft.model.Owner
import nl.codecraft.model.Repo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RepoMapperTest {

    @Test
    fun `RepoDto toRepoEntity should map correctly`() {
        // Given
        val repoDto = RepoDto(
            id = 123,
            name = "test-repo",
            fullName = "owner/test-repo",
            description = "A test repository",
            htmlUrl = "https://github.com/owner/test-repo",
            stargazersCount = 42,
            forksCount = 7,
            language = "Kotlin",
            owner = OwnerDto(
                id = 456,
                login = "owner",
                avatarUrl = "https://avatar.url",
                htmlUrl = "https://github.com/owner",
                type = "User"
            ),
            updatedAt = "2024-01-15T10:30:00Z",
            isPrivate = false,
            isFork = true
        )

        // When
        val result = repoDto.toRepoEntity()

        // Then
        assertEquals(123, result.id)
        assertEquals("test-repo", result.name)
        assertEquals("owner/test-repo", result.fullName)
        assertEquals("A test repository", result.description)
        assertEquals("https://github.com/owner/test-repo", result.htmlUrl)
        assertEquals(42, result.stargazersCount)
        assertEquals(7, result.forksCount)
        assertEquals("Kotlin", result.language)
        assertEquals(456, result.ownerId)
        assertEquals("owner", result.ownerLogin)
        assertEquals("https://avatar.url", result.ownerAvatarUrl)
        assertEquals("https://github.com/owner", result.ownerHtmlUrl)
        assertEquals("User", result.ownerType)
        assertEquals("2024-01-15T10:30:00Z", result.updatedAt)
        assertEquals(false, result.isPrivate)
        assertEquals(true, result.isFork)
    }

    @Test
    fun `RepoEntity toRepo should map correctly`() {
        // Given
        val repoEntity = RepoEntity(
            id = 789,
            name = "another-repo",
            fullName = "user/another-repo",
            description = "Another test repository",
            htmlUrl = "https://github.com/user/another-repo",
            stargazersCount = 100,
            forksCount = 25,
            language = "Java",
            ownerId = 999,
            ownerLogin = "user",
            ownerAvatarUrl = "https://avatar2.url",
            ownerHtmlUrl = "https://github.com/user",
            ownerType = "Organization",
            updatedAt = "2024-02-20T15:45:00Z",
            isPrivate = true,
            isFork = false
        )

        // When
        val result = repoEntity.toRepo()

        // Then
        assertEquals(789, result.id)
        assertEquals("another-repo", result.name)
        assertEquals("user/another-repo", result.fullName)
        assertEquals("Another test repository", result.description)
        assertEquals("https://github.com/user/another-repo", result.htmlUrl)
        assertEquals(100, result.stargazersCount)
        assertEquals(25, result.forksCount)
        assertEquals("Java", result.language)
        
        val expectedOwner = Owner(
            id = 999,
            login = "user",
            avatarUrl = "https://avatar2.url",
            htmlUrl = "https://github.com/user",
            type = "Organization"
        )
        assertEquals(expectedOwner, result.owner)
        
        assertEquals("2024-02-20T15:45:00Z", result.updatedAt)
        assertEquals(true, result.isPrivate)
        assertEquals(false, result.isFork)
    }

    @Test
    fun `RepoDto toRepoEntity should handle null description`() {
        // Given
        val repoDto = RepoDto(
            id = 111,
            name = "null-desc-repo",
            fullName = "owner/null-desc-repo",
            description = null,
            htmlUrl = "https://github.com/owner/null-desc-repo",
            stargazersCount = 0,
            forksCount = 0,
            language = null,
            owner = OwnerDto(
                id = 222,
                login = "owner",
                avatarUrl = "https://avatar.url",
                htmlUrl = "https://github.com/owner",
                type = "User"
            ),
            updatedAt = "2024-01-01T00:00:00Z",
            isPrivate = false,
            isFork = false
        )

        // When
        val result = repoDto.toRepoEntity()

        // Then
        assertEquals(111, result.id)
        assertEquals("null-desc-repo", result.name)
        assertEquals(null, result.description)
        assertEquals(null, result.language)
    }

    @Test
    fun `RepoEntity toRepo should handle null description and language`() {
        // Given
        val repoEntity = RepoEntity(
            id = 333,
            name = "null-fields-repo",
            fullName = "user/null-fields-repo",
            description = null,
            htmlUrl = "https://github.com/user/null-fields-repo",
            stargazersCount = 5,
            forksCount = 1,
            language = null,
            ownerId = 444,
            ownerLogin = "user",
            ownerAvatarUrl = "https://avatar.url",
            ownerHtmlUrl = "https://github.com/user",
            ownerType = "User",
            updatedAt = "2024-01-01T00:00:00Z",
            isPrivate = false,
            isFork = false
        )

        // When
        val result = repoEntity.toRepo()

        // Then
        assertEquals(333, result.id)
        assertEquals("null-fields-repo", result.name)
        assertEquals(null, result.description)
        assertEquals(null, result.language)
    }

    @Test
    fun `mapping should be bidirectional consistent`() {
        // Given
        val originalRepoDto = RepoDto(
            id = 555,
            name = "consistent-repo",
            fullName = "owner/consistent-repo",
            description = "Consistent mapping test",
            htmlUrl = "https://github.com/owner/consistent-repo",
            stargazersCount = 50,
            forksCount = 10,
            language = "Python",
            owner = OwnerDto(
                id = 666,
                login = "owner",
                avatarUrl = "https://consistent.avatar",
                htmlUrl = "https://github.com/owner",
                type = "User"
            ),
            updatedAt = "2024-03-01T12:00:00Z",
            isPrivate = true,
            isFork = false
        )

        // When
        val entity = originalRepoDto.toRepoEntity()
        val repo = entity.toRepo()

        // Then - Verify the round-trip preserves essential data
        assertEquals(originalRepoDto.id, repo.id)
        assertEquals(originalRepoDto.name, repo.name)
        assertEquals(originalRepoDto.fullName, repo.fullName)
        assertEquals(originalRepoDto.description, repo.description)
        assertEquals(originalRepoDto.htmlUrl, repo.htmlUrl)
        assertEquals(originalRepoDto.stargazersCount, repo.stargazersCount)
        assertEquals(originalRepoDto.forksCount, repo.forksCount)
        assertEquals(originalRepoDto.language, repo.language)
        assertEquals(originalRepoDto.owner.id, repo.owner.id)
        assertEquals(originalRepoDto.owner.login, repo.owner.login)
        assertEquals(originalRepoDto.owner.avatarUrl, repo.owner.avatarUrl)
        assertEquals(originalRepoDto.owner.htmlUrl, repo.owner.htmlUrl)
        assertEquals(originalRepoDto.owner.type, repo.owner.type)
        assertEquals(originalRepoDto.updatedAt, repo.updatedAt)
        assertEquals(originalRepoDto.isPrivate, repo.isPrivate)
        assertEquals(originalRepoDto.isFork, repo.isFork)
    }
}
