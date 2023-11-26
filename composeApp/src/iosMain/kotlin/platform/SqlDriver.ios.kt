package platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.postliu.TodoDatabase

actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(TodoDatabase.Schema, "todo.db")
}