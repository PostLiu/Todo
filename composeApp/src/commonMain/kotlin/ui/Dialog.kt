package ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun showDefaultDialog(
    showState: Boolean,
    onDismissRequest: () -> Unit,
    confirm: () -> Unit,
    dismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showState) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = confirm, content = { Text("确认") })
            },
            dismissButton = {
                TextButton(onClick = dismiss, content = { Text("取消") })
            },
            title = { Text("温馨提示") },
            text = content,
            shape = RoundedCornerShape(12.dp)
        )
    }
}