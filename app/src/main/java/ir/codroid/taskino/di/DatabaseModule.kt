package ir.codroid.taskino.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.codroid.taskino.data.AppDatabase
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
        ).build()

    @Singleton
    @Provides
    fun provideTodoDao (appDatabase: AppDatabase) = appDatabase.todoDao()
}