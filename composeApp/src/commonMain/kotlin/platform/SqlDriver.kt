package platform

import app.cash.sqldelight.db.SqlDriver
import com.postliu.TodoDatabase

expect fun createDriver(): SqlDriver

internal fun createTodoDatabase() = TodoDatabase.invoke(createDriver())