package platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.postliu.TodoDatabase
import org.postliu.todo.utils.ContextUtils

actual fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(TodoDatabase.Schema, ContextUtils.instance.context, "todo.db")
}