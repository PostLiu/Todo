import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.TodoMaterialTheme
import ui.BottomSheetEditTodo
import ui.LazyTodoList
import ui.TodoAddFloatingActionButton
import ui.TodoAppBar
import ui.TodoEditTips
import viewmodel.rememberTodoState

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun App() {
    val todoState = rememberTodoState()
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = todoState.scaffoldState,
        backgroundColor = TodoMaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = TodoMaterialTheme.colors.surface,
        sheetContentColor = TodoMaterialTheme.colors.onSurface,
        sheetPeekHeight = 0.dp,
        floatingActionButton = { TodoAddFloatingActionButton(todoState = todoState) },
        sheetContent = { BottomSheetEditTodo(todoState = todoState) },
        topBar = { TodoAppBar(todoState = todoState) },
        content = { LazyTodoList(modifier = Modifier.padding(it), todoState = todoState) }
    )
    TodoEditTips(todoState = todoState)
}