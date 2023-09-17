package ir.codroid.taskino.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.codroid.taskino.domain.repository.DataStoreRepository
import ir.codroid.taskino.domain.repository.TodoRepository
import ir.codroid.taskino.domain.use_case.AddTaskUseCase
import ir.codroid.taskino.domain.use_case.DeleteAllTaskUseCase
import ir.codroid.taskino.domain.use_case.DeleteTaskUseCase
import ir.codroid.taskino.domain.use_case.GetAllTaskUseCase
import ir.codroid.taskino.domain.use_case.ReadLanguageUseCase
import ir.codroid.taskino.domain.use_case.ReadSortStateUseCase
import ir.codroid.taskino.domain.use_case.SaveLanguageUseCase
import ir.codroid.taskino.domain.use_case.SaveSortStateUseCase
import ir.codroid.taskino.domain.use_case.SearchTaskUseCase
import ir.codroid.taskino.domain.use_case.TaskListUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideTaskListUseCases(
        todoRepository: TodoRepository,
        dataStoreRepository: DataStoreRepository
    ): TaskListUseCases = TaskListUseCases(
        getAllTaskUseCase = GetAllTaskUseCase(todoRepository),
        searchTaskUseCase = SearchTaskUseCase(todoRepository),
        deleteAllTaskUseCase = DeleteAllTaskUseCase(todoRepository),
        deleteTaskUseCase = DeleteTaskUseCase(todoRepository),
        addTaskUseCase = AddTaskUseCase(todoRepository),
        saveLanguageUseCase = SaveLanguageUseCase(dataStoreRepository),
        readLanguageUseCase = ReadLanguageUseCase(dataStoreRepository),
        saveSortStateUseCase = SaveSortStateUseCase(dataStoreRepository),
        readSortStateUseCase = ReadSortStateUseCase(dataStoreRepository)
    )

}