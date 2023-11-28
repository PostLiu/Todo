package ui

import TodoIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.TodoMaterialTheme
import todoicons.TodoCompletedStateFinish
import todoicons.TodoCompletedStateNormal
import todoicons.TodoSelectedStateFinish
import todoicons.TodoSelectedStateNormal

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListItem(
    modifier: Modifier = Modifier,
    backgroundColor: Color = TodoMaterialTheme.colors.surface,
    contentColor: Color = TodoMaterialTheme.colors.onSurface,
    editMode: Boolean,
    isCompleted: Boolean,
    isSelected: Boolean,
    onCompleted: () -> Unit = {},
    onSelected: () -> Unit = {},
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    !editMode, enter = expandHorizontally(), exit = shrinkHorizontally()
                ) {
                    IconButton(onClick = onCompleted) {
                        Icon(
                            if (isCompleted) TodoIcons.TodoCompletedStateFinish else TodoIcons.TodoCompletedStateNormal,
                            contentDescription = null
                        )
                    }
                }
                content()
                AnimatedVisibility(editMode, enter = fadeIn(), exit = fadeOut()) {
                    IconButton(onClick = onSelected) {
                        Icon(
                            if (isSelected) TodoIcons.TodoSelectedStateFinish else TodoIcons.TodoSelectedStateNormal,
                            contentDescription = null
                        )
                    }
                }
            }
        })
}