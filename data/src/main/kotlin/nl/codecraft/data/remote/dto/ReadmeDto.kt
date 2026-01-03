package nl.codecraft.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReadmeDto(
    @Json(name = "name")
    val name: String,
    
    @Json(name = "path")
    val path: String,
    
    @Json(name = "sha")
    val sha: String,
    
    @Json(name = "size")
    val size: Int,
    
    @Json(name = "url")
    val url: String,
    
    @Json(name = "html_url")
    val htmlUrl: String,
    
    @Json(name = "git_url")
    val gitUrl: String,
    
    @Json(name = "download_url")
    val downloadUrl: String?,
    
    @Json(name = "type")
    val type: String,
    
    @Json(name = "content")
    val content: String,
    
    @Json(name = "encoding")
    val encoding: String
)
