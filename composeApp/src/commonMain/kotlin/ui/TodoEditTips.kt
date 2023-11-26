package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import viewmodel.TodoAction
import viewmodel.TodoState

@Composable
fun TodoEditTips(todoState: TodoState) {
    val deletedDialogShowState by todoState.showDeletedDialog.collectAsState()
    val clearDialogShowState by todoState.showClearDialog.collectAsState()
    // 删除提示窗
    showDefaultDialog(showState = deletedDialogShowState, onDismissRequest = {
        todoState.dispatch(TodoAction.ShowDeletedDialog(false))
    }, confirm = {
        todoState.dispatch(TodoAction.ShowDeletedDialog(false))
        todoState.dispatch(TodoAction.RemovedSelectedTodo)
    }, dismiss = {
        todoState.dispatch(TodoAction.ShowDeletedDialog(false))
    }, content = {
        Text("是否删除所选待办", fontWeight = FontWeight.W500, fontSize = 16.sp)
    })
    // 清空提示窗
    showDefaultDialog(showState = clearDialogShowState, onDismissRequest = {
        todoState.dispatch(TodoAction.ShowClearDialog(false))
    }, confirm = {
        todoState.dispatch(TodoAction.ShowClearDialog(false))
        todoState.dispatch(TodoAction.ClearAllTodo)
    }, dismiss = {
        todoState.dispatch(TodoAction.ShowClearDialog(false))
    }, content = {
        Text("是否清空所有待办", fontWeight = FontWeight.W500, fontSize = 16.sp)
    })
}