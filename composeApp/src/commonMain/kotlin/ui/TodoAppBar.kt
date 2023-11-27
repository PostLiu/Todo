package ui

import TodoIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import todoicons.TodoSelectedStateFinish
import todoicons.TodoSelectedStateNormal
import viewmodel.TodoAction
import viewmodel.TodoState

@Composable
fun TodoAppBar(todoState: TodoState) {
    val todoList by todoState.todoList.collectAsState()
    val editMode by todoState.editMode.collectAsState()
    val allSelected by todoState.allSelected.collectAsState()
    val dropdownMenuExpanded by todoState.dropdownMenuExpanded.collectAsState()
    val canDeleted by todoState.canDeleted.collectAsState()
    val hideCompletedTodo by todoState.hideCompleted.collectAsState()
    TopAppBar(
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = Color.Black,
        title = {
            AnimatedVisibility(
                editMode, enter = expandHorizontally(), exit = shrinkHorizontally()
            ) {
                IconButton(onClick = {
                    todoState.dispatch(TodoAction.EditMode(false))
                }, content = {
                    Icon(Icons.Sharp.Close, contentDescription = null)
                })
            }
            AnimatedVisibility(
                !editMode, enter = expandHorizontally(), exit = shrinkHorizontally()
            ) {
                Text("待办")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
            if (editMode) {
                IconButton(onClick = {
                    todoState.dispatch(TodoAction.ShowDeletedDialog(true))
                }, enabled = canDeleted, content = {
                    Icon(
                        Icons.Sharp.Delete,
                        contentDescription = null,
                    )
                })
                IconButton(onClick = {
                    todoState.dispatch(TodoAction.AllTodoSelectedUpdate(!allSelected))
                }, content = {
                    Icon(
                        if (allSelected) TodoIcons.TodoSelectedStateFinish else TodoIcons.TodoSelectedStateNormal,
                        contentDescription = null,
                    )
                }, modifier = Modifier.padding(end = 12.dp))
            } else {
                IconButton(onClick = {
                    todoState.dispatch(TodoAction.DropMenuExpandedState(true))
                }, modifier = Modifier.padding(end = 12.dp)) {
                    Icon(Icons.Sharp.Menu, contentDescription = null)
                }
                if (dropdownMenuExpanded) {
                    DropdownMenu(expanded = dropdownMenuExpanded, onDismissRequest = {
                        todoState.dispatch(TodoAction.DropMenuExpandedState(false))
                    }, content = {
                        DropdownMenuItem(onClick = {
                            todoState.dispatch(TodoAction.EditMode(true))
                        }, enabled = todoList.isNotEmpty(), content = {
                            Text("编辑")
                        })
                        DropdownMenuItem(onClick = {
                            todoState.dispatch(TodoAction.HideCompletedTodo(!hideCompletedTodo))
                        }, content = {
                            Text(if (!hideCompletedTodo) "隐藏已完成待办" else "显示已完成待办")
                        })
                        DropdownMenuItem(onClick = {
                            todoState.dispatch(TodoAction.ShowClearDialog(true))
                        }, content = {
                            Text("清空待办")
                        })
                    })
                }
            }
        },
    )
}