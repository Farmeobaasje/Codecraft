package nl.codecraft.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.codecraft.data.local.AppDatabase
import nl.codecraft.data.local.RepoDao
import nl.codecraft.data.local.RepoNoteDao
import nl.codecraft.data.local.SettingsDataStore
import nl.codecraft.domain.repository.GitHubRepository
import nl.codecraft.domain.repository.ThemeRepository
import nl.codecraft.data.repository.GitHubRepositoryImpl
import nl.codecraft.data.repository.ThemeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "codecraft-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(database: AppDatabase): RepoDao {
        return database.repoDao()
    }

    @Provides
    @Singleton
    fun provideRepoNoteDao(database: AppDatabase): RepoNoteDao {
        return database.repoNoteDao()
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(settingsDataStore: SettingsDataStore): ThemeRepository {
        return ThemeRepositoryImpl(settingsDataStore)
    }

    @Provides
    @Singleton
    fun provideGitHubRepository(
        repoDao: RepoDao,
        repoNoteDao: RepoNoteDao,
        apiService: nl.codecraft.data.remote.GitHubApiService
    ): GitHubRepository {
        return GitHubRepositoryImpl(repoDao, repoNoteDao, apiService)
    }
}
