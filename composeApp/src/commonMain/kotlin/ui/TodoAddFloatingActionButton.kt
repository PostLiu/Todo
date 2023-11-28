package ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import theme.TodoMaterialTheme
import viewmodel.TodoAction
import viewmodel.TodoState

@Composable
fun TodoAddFloatingActionButton(todoState: TodoState) {
    FloatingActionButton(
        onClick = {
            todoState.dispatch(TodoAction.OnClickTodoModel(null))
            todoState.dispatch(TodoAction.ExpandedOrCollapsedBottomSheetEdit)
        },
        content = {
            Icon(Icons.Sharp.Add, contentDescription = null)
        },
        backgroundColor = TodoMaterialTheme.colors.primary,
        contentColor = TodoMaterialTheme.colors.onPrimary
    )
}