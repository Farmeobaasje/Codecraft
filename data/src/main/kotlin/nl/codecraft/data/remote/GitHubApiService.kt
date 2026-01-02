package nl.codecraft.data.remote

import nl.codecraft.data.remote.dto.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user: String): List<RepoDto>
}
