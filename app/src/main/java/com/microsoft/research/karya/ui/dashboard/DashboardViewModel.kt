package com.microsoft.research.karya.ui.dashboard

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.research.karya.data.manager.AuthManager
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskInfo
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskStatus
import com.microsoft.research.karya.data.repo.AssignmentRepository
import com.microsoft.research.karya.data.repo.TaskRepository
import com.microsoft.research.karya.utils.DateUtils
import com.microsoft.research.karya.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import javax.inject.Inject

private const val WFC_CODE_SEED = "93848374"

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val taskRepository: TaskRepository,
    private val assignmentRepository: AssignmentRepository,
    private val authManager: AuthManager,
    private val datastore: DataStore<Preferences>
) : ViewModel() {

    private var taskInfoList = listOf<TaskInfo>()
    private val taskInfoComparator =
        compareByDescending<TaskInfo> { taskInfo -> taskInfo.taskStatus.assignedMicrotasks }
            .thenByDescending { taskInfo -> taskInfo.taskStatus.skippedMicrotasks }
            .thenBy { taskInfo -> taskInfo.taskID }

    private val _dashboardUiState: MutableStateFlow<DashboardUiState> =
        MutableStateFlow(DashboardUiState.Success(DashboardStateSuccess(emptyList())))
    val dashboardUiState = _dashboardUiState.asStateFlow()

    private val _taskList: MutableStateFlow<List<TaskInfo>> = MutableStateFlow(listOf())
    val taskList = _taskList.asStateFlow()

    private val _progress: MutableStateFlow<Int> = MutableStateFlow(0)
    val progress = _progress.asStateFlow()
    private var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<DashboardError?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _workerAccessCode: MutableStateFlow<String> = MutableStateFlow("")
    val workerAccessCode = _workerAccessCode.asStateFlow()

    // Work from center user
    private val _workFromCenterUser: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val workFromCenterUser = _workFromCenterUser.asStateFlow()
    private val _userInCenter: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userInCenter = _userInCenter.asStateFlow()
    var centerAuthExpirationTime: Long = 0

    init {
        viewModelScope.launch {
            val worker = authManager.getLoggedInWorker()
            _workerAccessCode.value = worker.accessCode

            try {
                if (worker.params != null && !worker.params.isJsonNull) {
                    val tags = worker.params.asJsonObject.getAsJsonArray("tags")
                    for (tag in tags) {
                        if (tag.asString == "_wfc_") {
                            _workFromCenterUser.value = true
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.w(e)
                _workFromCenterUser.value = false
            }
        }
    }

    fun authorizeWorkFromCenterUser(code: String) {
        val today = DateUtils.getCurrentDate().substring(0, 10)
        val message = WFC_CODE_SEED + today + "\n"
        val md5Encoder = MessageDigest.getInstance("MD5")
        md5Encoder.update(message.toByteArray(), 0, message.length)
        val hash = BigInteger(1, md5Encoder.digest()).toString(16).substring(0, 6)
        if (code == hash) {
            _userInCenter.value = true
            // TODO: hour offset is hard coded
            centerAuthExpirationTime = Date().time + 2 * 60 * 60 * 1000
        }
    }

    fun revokeWFCAuthorization() {
        _userInCenter.value = false
    }

    fun checkWorkFromCenterUserAuth() {
        val currentTime = Date().time
        if (currentTime > centerAuthExpirationTime) {
            _userInCenter.value = false
        }
    }

    suspend fun refreshList() {
        val worker = authManager.getLoggedInWorker()
        val tempList = mutableListOf<TaskInfo>()

        // Get task report summary
        val taskSummary = assignmentRepository.getTaskReportSummary(worker.id)

        taskInfoList.forEach { taskInfo ->
            val taskId = taskInfo.taskID
            val taskStatus = fetchTaskStatus(taskId)
            val summary = if (taskSummary.containsKey(taskId)) taskSummary[taskId] else null
            tempList.add(
                TaskInfo(
                    taskInfo.taskID,
                    taskInfo.taskName,
                    taskInfo.taskInstruction,
                    taskInfo.scenarioName,
                    taskStatus,
                    taskInfo.isGradeCard,
                    summary
                )
            )
        }
        taskInfoList = tempList.sortedWith(taskInfoComparator)

        val success =
            DashboardUiState.Success(
                DashboardStateSuccess(taskInfoList.sortedWith(taskInfoComparator))
            )
        _dashboardUiState.value = success
        _taskList.value = taskInfoList.sortedWith(taskInfoComparator)
    }

    /**
     * Returns a hot flow connected to the DB
     * @return [Flow] of list of [TaskRecord] wrapper in a [Result]
     */
    @Suppress("USELESS_CAST")
    fun getAllTasks() {
        viewModelScope.launch {
            val worker = authManager.getLoggedInWorker()

            taskRepository
                .getAllTasksFlow()
                .flowOn(Dispatchers.IO)
                .onEach { taskList ->

                    // Get task report summary
                    val taskSummary = assignmentRepository.getTaskReportSummary(worker.id)

                    val tempList = mutableListOf<TaskInfo>()
                    taskList.forEach { taskRecord ->
                        // TODO: Here only NPE are expected in the case when the key do not exist
                        //      in the JSON object so can be simply "?.asString ?: null", can't be?
                        val taskInstruction = try {
                            taskRecord.params.asJsonObject.get("instruction").asString
                        } catch (e: Exception) {
                            Timber.e(e)
                            null
                        }
                        val taskId = taskRecord.id
                        val taskStatus = fetchTaskStatus(taskId)
                        val summary = if (taskSummary.containsKey(taskId)) taskSummary[taskId] else null

                        tempList.add(
                            TaskInfo(
                                taskRecord.id,
                                taskRecord.display_name,
                                taskInstruction,
                                taskRecord.scenario_name,
                                taskStatus,
                                false,
                                summary
                            )
                        )
                    }
                    taskInfoList = tempList

                    val success =
                        DashboardUiState.Success(
                            DashboardStateSuccess(taskInfoList.sortedWith(taskInfoComparator))
                        )
                    _dashboardUiState.value = success
                    _taskList.value = taskInfoList.sortedWith(taskInfoComparator)
                }
                .catch {
                    Timber.w(it)
                    _dashboardUiState.value = DashboardUiState.Error(it)
                    _error.value = DashboardError(it.message ?: "Some error occured", ERROR_LVL.ERROR, ERROR_TYPE.TASK_ERROR)
                }
                .collect()
        }
    }

    fun setLoading() {
        _dashboardUiState.value = DashboardUiState.Loading
        _isLoading.value = true
    }

    private suspend fun fetchTaskStatus(taskId: String): TaskStatus {
        return taskRepository.getTaskStatus(taskId)
    }

    fun setProgress(i: Int) {
        _progress.value = i
    }

    fun setError(error: DashboardError) {
        _error.value = error
    }
}

class DashboardError(val errorMessage: String, val errorLevel: ERROR_LVL, val errorType: ERROR_TYPE)
