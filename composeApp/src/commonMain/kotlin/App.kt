import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.BottomSheetEditTodo
import ui.LazyTodoList
import ui.TodoAppBar
import ui.TodoEditTips
import viewmodel.TodoAction
import viewmodel.rememberTodoState

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun App() {
    val todoState = rememberTodoState()
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = todoState.scaffoldState,
        backgroundColor = Color(0xFFF2F2F2),
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = MaterialTheme.colors.surface,
        sheetPeekHeight = 0.dp,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                todoState.dispatch(TodoAction.OnClickTodoModel(null))
                todoState.dispatch(TodoAction.ExpandedOrCollapsedBottomSheetEdit)
            }, content = {
                Icon(Icons.Sharp.Add, contentDescription = null)
            })
        },
        sheetContent = { BottomSheetEditTodo(todoState = todoState) },
        topBar = { TodoAppBar(todoState = todoState) },
        content = { LazyTodoList(modifier = Modifier.padding(it), todoState = todoState) }
    )
    TodoEditTips(todoState = todoState)
}