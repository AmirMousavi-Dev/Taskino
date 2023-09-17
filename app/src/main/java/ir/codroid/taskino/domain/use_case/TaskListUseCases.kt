package ir.codroid.taskino.domain.use_case

data class TaskListUseCases(
    val getAllTaskUseCase: GetAllTaskUseCase,
    val searchTaskUseCase: SearchTaskUseCase,
    val deleteAllTaskUseCase: DeleteAllTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val addTaskUseCase: AddTaskUseCase,
    val saveLanguageUseCase: SaveLanguageUseCase,
    val readLanguageUseCase: ReadLanguageUseCase,
    val saveSortStateUseCase: SaveSortStateUseCase,
    val readSortStateUseCase: ReadSortStateUseCase
)