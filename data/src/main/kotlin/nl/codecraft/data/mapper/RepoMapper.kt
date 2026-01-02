package nl.codecraft.data.mapper

import nl.codecraft.data.local.RepoEntity
import nl.codecraft.data.remote.dto.OwnerDto
import nl.codecraft.data.remote.dto.RepoDto
import nl.codecraft.model.Owner
import nl.codecraft.model.Repo

fun RepoDto.toRepoEntity(): RepoEntity {
    return RepoEntity(
        id = id,
        name = name,
        fullName = fullName,
        description = description,
        htmlUrl = htmlUrl,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        language = language,
        ownerId = owner.id,
        ownerLogin = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        ownerHtmlUrl = owner.htmlUrl,
        ownerType = owner.type,
        updatedAt = updatedAt,
        isPrivate = isPrivate,
        isFork = isFork,
    )
}

fun RepoEntity.toRepo(): Repo {
    return Repo(
        id = id,
        name = name,
        fullName = fullName,
        description = description,
        htmlUrl = htmlUrl,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        language = language,
        owner = Owner(
            id = ownerId,
            login = ownerLogin,
            avatarUrl = ownerAvatarUrl,
            htmlUrl = ownerHtmlUrl,
            type = ownerType,
        ),
        updatedAt = updatedAt,
        isPrivate = isPrivate,
        isFork = isFork,
    )
}
