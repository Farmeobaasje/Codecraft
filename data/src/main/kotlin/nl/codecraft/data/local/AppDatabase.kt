package nl.codecraft.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepoEntity::class, RepoNoteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun repoNoteDao(): RepoNoteDao
}
