package nl.codecraft.data.remote

import nl.codecraft.data.remote.dto.RepoDto
import nl.codecraft.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user: String): List<RepoDto>

    @GET("search/repositories")
    suspend fun searchTrendingRepositories(
        @Query("q") query: String = "stars:>1000",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 10
    ): SearchResponseDto
}
