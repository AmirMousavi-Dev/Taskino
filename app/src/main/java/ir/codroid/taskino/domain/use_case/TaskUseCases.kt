package ir.codroid.taskino.domain.use_case

class TaskUseCases(
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getSelectedTaskUseCase: GetSelectedTaskUseCase
) {
}