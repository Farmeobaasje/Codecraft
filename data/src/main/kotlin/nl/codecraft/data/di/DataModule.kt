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
import nl.codecraft.data.repository.GitHubRepository
import nl.codecraft.data.repository.GitHubRepositoryImpl
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
    fun provideGitHubRepository(
        repoDao: RepoDao,
        apiService: nl.codecraft.data.remote.GitHubApiService
    ): GitHubRepository {
        return GitHubRepositoryImpl(repoDao, apiService)
    }
}
