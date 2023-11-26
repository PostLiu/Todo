package viewmodel

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.postliu.Todo
import database.TodoHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun rememberTodoState(
    scope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    softwareKeyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
) = remember {
    TodoState(scope, bottomSheetScaffoldState, softwareKeyboardController)
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
class TodoState(
    private val scope: CoroutineScope,
    private val bottomSheetScaffoldState: BottomSheetScaffoldState,
    private val softwareKeyboardController: SoftwareKeyboardController?
) {
    val scaffoldState get() = bottomSheetScaffoldState

    private val mTodoList = MutableStateFlow<List<Todo>>(emptyList())
    val todoList = mTodoList.asStateFlow()

    private val mDropdownMenuExpanded = MutableStateFlow(false)
    val dropdownMenuExpanded = mDropdownMenuExpanded.asStateFlow()

    private val mAllSelected = MutableStateFlow(false)
    val allSelected = mAllSelected.asStateFlow()

    private val mEditMode = MutableStateFlow(false)
    val editMode = mEditMode.asStateFlow()

    private val mCanDeleted = MutableStateFlow(false)
    val canDeleted = mCanDeleted.asStateFlow()

    private val mTodoModelClick = MutableStateFlow<Todo?>(null)
    val todoModelClick = mTodoModelClick.asStateFlow()

    private val mShowDeletedDialog = MutableStateFlow(false)
    val showDeletedDialog = mShowDeletedDialog.asStateFlow()

    private val mShowClearDialog = MutableStateFlow(false)
    val showClearDialog = mShowClearDialog.asStateFlow()

    private val mHideCompleted = MutableStateFlow(false)
    val hideCompleted = mHideCompleted.asStateFlow()

    init {
        initTodoList()
        initHideCompletedMode()
    }

    fun dispatch(action: TodoAction) = when (action) {
        is TodoAction.AddTodo -> {
            addTodo(action.content, action.time)
            hideSoftKeyboard()
        }

        is TodoAction.EditTodo -> {
            editTodo(action.id, action.content)
        }

        is TodoAction.OnClickTodoModel -> {
            clickTodoModel(action.todoModel)
        }

        is TodoAction.TodoSelectedUpdate -> {
            todoSelected(action.id, action.isSelected)
        }

        is TodoAction.TodoCompletedUpdate -> {
            todoCompleted(action.id, action.isCompleted)
        }

        is TodoAction.ClearAllTodo -> {
            clearAllTodo()
        }

        is TodoAction.RemovedSelectedTodo -> {
            removedSelectedTodo()
        }

        is TodoAction.HideCompletedTodo -> {
            hideCompletedTodo(action.hide)
        }

        is TodoAction.EditMode -> {
            editModeUpdate(action.editMode)
        }

        is TodoAction.AllTodoSelectedUpdate -> {
            allTodoSelectedUpdate(action.allSelected)
        }

        is TodoAction.DropMenuExpandedState -> {
            updateDropdownMenuExpandedState(action.expanded)
        }

        is TodoAction.ShowDeletedDialog -> {
            deletedDialogShowState(action.show)
        }

        is TodoAction.ShowClearDialog -> {
            clearDialogShowState(action.show)
        }

        is TodoAction.ExpandedOrCollapsedBottomSheetEdit -> {
            collapseOrExpandBottomSheet()
        }
    }

    /**
     * 展开/折叠底部编辑框
     */
    private fun collapseOrExpandBottomSheet() {
        scope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            } else {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }
    }

    /**
     * 删除弹窗显示/隐藏
     */
    private fun deletedDialogShowState(show: Boolean) {
        scope.launch {
            mShowDeletedDialog.emit(show)
            updateDropdownMenuExpandedState(false)
        }
    }

    /**
     * 清空待办弹窗显示/隐藏
     */
    private fun clearDialogShowState(show: Boolean) {
        scope.launch {
            mShowClearDialog.emit(show)
            updateDropdownMenuExpandedState(false)
        }
    }

    /**
     * AppBar 菜单的显示/隐藏
     */
    private fun updateDropdownMenuExpandedState(expanded: Boolean) {
        scope.launch {
            mDropdownMenuExpanded.emit(expanded)
        }
    }

    /**
     * 全选/取消全选
     */
    private fun allTodoSelectedUpdate(allSelected: Boolean) {
        scope.launch {
            mTodoList.update {
                if (allSelected) {
                    mAllSelected.emit(true)
                    TodoHelper.setAllTodoSelected()
                } else {
                    mAllSelected.emit(false)
                    TodoHelper.setAllTodoUnSelected()
                }
            }
            checkAnySelectedTodo()
        }
    }

    /**
     * 编辑模式/退出编辑模式
     */
    private fun editModeUpdate(editMode: Boolean) {
        scope.launch {
            if (editMode) {
                updateDropdownMenuExpandedState(false)
                if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
                checkAnySelectedTodo()
                checkAllSelectedTodo()
            } else {
                allTodoSelectedUpdate(false)
            }
            mEditMode.emit(editMode)
        }
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyboard() {
        softwareKeyboardController?.hide()
    }

    /**
     * 初始化默认数据
     */
    private fun initTodoList() {
        scope.launch {
            mTodoList.update { TodoHelper.queryAllTodo() }
            checkAnySelectedTodo()
        }
    }

    private fun initHideCompletedMode() {
        scope.launch {
            mHideCompleted.update {
                TodoHelper.getCompletedTodoHideMode()
            }
        }
    }

    /**
     * 添加待办
     */
    private fun addTodo(content: String, time: Long) {
        scope.launch {
            with(TodoHelper) {
                mTodoList.update {
                    addTodo(content, time)
                }
            }
            bottomSheetScaffoldState.bottomSheetState.collapse()
            hideSoftKeyboard()
        }
    }

    /**
     * 编辑待办
     */
    private fun editTodo(id: Long, content: String) {
        scope.launch {
            mTodoList.update { TodoHelper.editTodoContentById(id, content) }
            collapseOrExpandBottomSheet()
            hideSoftKeyboard()
        }
    }

    /**
     * 点击待办
     */
    private fun clickTodoModel(todoModel: Todo?) {
        scope.launch {
            mTodoModelClick.emit(todoModel)
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }
    }

    /**
     * 选中待办/取消选中待办
     */
    private fun todoSelected(id: Long, isSelected: Boolean) {
        println("选中/取消：$isSelected")
        scope.launch {
            if (isSelected) {
                mTodoList.update {
                    TodoHelper.selectedTodo(id)
                }
            } else {
                mTodoList.update {
                    TodoHelper.unSelectedTodo(id)
                }
            }
            checkAnySelectedTodo()
            checkAllSelectedTodo()
        }
    }

    /**
     * 待办完成/取消待办完成
     */
    private fun todoCompleted(id: Long, isCompleted: Boolean) {
        scope.launch {
            mTodoList.update {
                TodoHelper.completedTodo(id, isCompleted)
            }
        }
    }

    /**
     * 移除待办
     */
    private fun removedSelectedTodo() {
        scope.launch {
            mTodoList.update {
                TodoHelper.deletedTodoIsSelected()
            }
            editModeUpdate(false)
        }
    }

    /**
     * 清除待办
     */
    private fun clearAllTodo() {
        scope.launch {
            mTodoList.update {
                TodoHelper.clearAllTodo()
            }
        }
    }

    /**
     * 隐藏已完成待办
     */
    private fun hideCompletedTodo(hide: Boolean) {
        scope.launch {
            TodoHelper.updateTodoCompletedHideMode(hide).apply {
                mHideCompleted.update {
                    first
                }
                mTodoList.update {
                    second
                }
            }
            updateDropdownMenuExpandedState(false)
        }
    }

    /**
     * 检查是否有选中的
     */
    private fun checkAnySelectedTodo() {
        scope.launch {
            val canDeleted = TodoHelper.checkAnySelectedTodo()
            mCanDeleted.update { canDeleted }
        }
    }

    private fun checkAllSelectedTodo() {
        scope.launch {
            val allSelected = TodoHelper.checkAllSelectedTodo()
            mAllSelected.update { allSelected }
        }
    }
}
