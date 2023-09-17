package ir.codroid.taskino.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.codroid.taskino.data.data_store.TaskinoDataStore
import ir.codroid.taskino.data.data_store.TaskinoDataStoreImpl
import ir.codroid.taskino.data.local.ToDoDao
import ir.codroid.taskino.data.repository.DataStoreRepositoryImpl
import ir.codroid.taskino.data.repository.TodoRepositoryImpl
import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTodoRepository(todoDao: ToDoDao): TodoRepository =
        TodoRepositoryImpl(todoDao = todoDao)


    @Singleton
    @Provides
    fun provideTaskinoDataStore(@ApplicationContext context: Context): TaskinoDataStore =
        TaskinoDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideDataStoreRepository(taskinoDataStore: TaskinoDataStore): DataStoreRepository =
        DataStoreRepositoryImpl(taskinoDataStore)
}