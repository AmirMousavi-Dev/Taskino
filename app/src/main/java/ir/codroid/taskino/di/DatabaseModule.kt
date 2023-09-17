package ir.codroid.taskino.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.codroid.taskino.data.local.AppDatabase
import ir.codroid.taskino.data.local.DataBaseMigration.MIGRATION_1_2
import ir.codroid.taskino.util.Constants.TASKINO_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context ,
            AppDatabase::class.java,
            TASKINO_DATABASE
        )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Singleton
    @Provides
    fun provideTodoDao (appDatabase: AppDatabase) = appDatabase.todoDao()
}