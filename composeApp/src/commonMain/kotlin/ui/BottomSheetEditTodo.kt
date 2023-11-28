package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import theme.TodoMaterialTheme
import viewmodel.TodoAction
import viewmodel.TodoState

@Composable
fun BottomSheetEditTodo(todoState: TodoState) {
    var input by remember { mutableStateOf("") }
    val clickTodo by todoState.todoModelClick.collectAsState()
    DisposableEffect(clickTodo?.content) {
        input = clickTodo?.content.orEmpty()
        onDispose {
            input = ""
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth().background(
            TodoMaterialTheme.colors.surface,
            RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ),
        content = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                content = {
                    TextButton(onClick = {
                        todoState.dispatch(TodoAction.ExpandedOrCollapsedBottomSheetEdit)
                    },
                        colors = ButtonDefaults.textButtonColors(contentColor = TodoMaterialTheme.colors.onSurface),
                        content = { Text("取消") })
                    Box(modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                        content = {
                            Text(
                                "新建待办",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = TodoMaterialTheme.colors.onSurface,
                            )
                        })
                    TextButton(
                        onClick = {
                            if (clickTodo != null) {
                                val todoId = clickTodo?.id
                                if (todoId != null) {
                                    todoState.dispatch(TodoAction.EditTodo(todoId, input))
                                } else {
                                    todoState.dispatch(
                                        TodoAction.AddTodo(
                                            content = input,
                                            time = Clock.System.now().toEpochMilliseconds()
                                        )
                                    )
                                }
                            } else {
                                todoState.dispatch(
                                    TodoAction.AddTodo(
                                        content = input,
                                        time = Clock.System.now().toEpochMilliseconds()
                                    )
                                )
                            }
                            input = ""
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = TodoMaterialTheme.colors.onSurface),
                        enabled = input.isNotEmpty(),
                        content = { Text("保存") })
                })
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 24.dp)
                    .fillMaxWidth(),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("添加事项...") },
            )
        })
}