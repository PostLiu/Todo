import androidx.compose.ui.graphics.vector.ImageVector
import todoicons.TodoCompletedStateFinish
import todoicons.TodoCompletedStateNormal
import todoicons.TodoSelectedStateFinish
import todoicons.TodoSelectedStateNormal
import kotlin.collections.List as _KtList

object TodoIcons

private var mAllIcons: _KtList<ImageVector>? = null

val TodoIcons.AllIcons: _KtList<ImageVector>
    get() {
        if (mAllIcons != null) {
            return mAllIcons!!
        }
        mAllIcons = listOf(
            TodoCompletedStateFinish, TodoCompletedStateNormal, TodoSelectedStateNormal,
            TodoSelectedStateFinish
        )
        return mAllIcons!!
    }
