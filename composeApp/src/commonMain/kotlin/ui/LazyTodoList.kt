package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ext.formatDateBefore
import kotlinx.datetime.Clock
import theme.TodoMaterialTheme
import viewmodel.TodoAction
import viewmodel.TodoState

@Composable
fun LazyTodoList(modifier: Modifier = Modifier, todoState: TodoState) {
    val todoList by todoState.todoList.collectAsState()
    val editMode by todoState.editMode.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize().then(modifier),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        items(items = todoList, key = { it.id }, contentType = { it.id }, itemContent = {
            TodoListItem(modifier = Modifier.fillMaxWidth(),
                editMode = editMode,
                isSelected = it.selected == 1L,
                isCompleted = it.completed == 1L,
                onSelected = {
                    todoState.dispatch(
                        TodoAction.TodoSelectedUpdate(
                            id = it.id,
                            isSelected = it.selected != 1L
                        )
                    )
                },
                onCompleted = {
                    todoState.dispatch(
                        TodoAction.TodoCompletedUpdate(
                            id = it.id,
                            isCompleted = it.completed != 1L
                        )
                    )
                },
                onClick = {
                    if (!editMode) {
                        todoState.dispatch(TodoAction.OnClickTodoModel(it))
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = it.content,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = (Clock.System.now()
                                .toEpochMilliseconds() - it.time).formatDateBefore(),
                            fontSize = 12.sp,
                            color = if (it.completed == 1L) TodoMaterialTheme.colors.onPrimary else TodoMaterialTheme.colors.primary,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                })
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                thickness = 8.dp,
            )
        })
    }
}