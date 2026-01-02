# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Added `.clinerules` file with development session rules for Cline AI assistant.
- Added multi-module Android project structure with :app, :data, :domain, :core:model, :core:common modules.
- Configured Gradle with version catalog (libs.versions.toml) and all necessary dependencies.
- Implemented Hilt setup with CodeCraftApplication class.
- Defined core data models: Repo, Owner, User.
- Implemented Room database setup with RepoEntity, RepoDao, and AppDatabase.
- Implemented remote data source with RepoDto, OwnerDto, and GitHubApiService.
- Created NetworkModule for Retrofit and Moshi dependency injection.
- Implemented GitHubRepository interface and implementation.
- Created RepoMapper for data transformation between DTO, Entity, and Domain models.
- Implemented GetUserReposUseCase for domain logic.
- Created DataModule for Room database and repository dependency injection.
