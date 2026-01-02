package nl.codecraft.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "stargazers_count")
    val stargazersCount: Int,
    @Json(name = "forks_count")
    val forksCount: Int,
    @Json(name = "language")
    val language: String?,
    @Json(name = "owner")
    val owner: OwnerDto,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "private")
    val isPrivate: Boolean,
    @Json(name = "fork")
    val isFork: Boolean,
)

@JsonClass(generateAdapter = true)
data class OwnerDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "type")
    val type: String,
)
