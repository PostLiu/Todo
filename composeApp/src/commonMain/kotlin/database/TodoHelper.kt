package database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.createTodoDatabase

object TodoHelper {

    private val database = createTodoDatabase()

    // 查询所有待办
    suspend fun queryAllTodo() = withContext(Dispatchers.IO) {
        database.todoQueries.queryTodoList().executeAsList()
    }

    // 添加待办
    suspend fun addTodo(content: String, time: Long) = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                insertTodo(content, time)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 编辑待办内容
    suspend fun editTodoContentById(id: Long, content: String) = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                editTodoById(content = content, id = id)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 待办标记完成
    suspend fun completedTodo(id: Long, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                updateCompletedTodoById(if (isCompleted) 1 else 0, id)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 待办标记选中
    suspend fun selectedTodo(id: Long) = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                updateSelectedTodoById(1, id)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 待办标记为选中
    suspend fun unSelectedTodo(id: Long) = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                updateSelectedTodoById(0, id)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 设置所有都是未选中状态
    suspend fun setAllTodoUnSelected() = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                updateTodoListSelected(0)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 设置所有都是选中状态
    suspend fun setAllTodoSelected() = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                updateTodoListSelected(1)
                queryTodoList()
            }.executeAsList()
        }
    }

    // 是否隐藏已完成的待办
    suspend fun updateTodoCompletedHideMode(hide: Boolean) = withContext(Dispatchers.IO) {
        val hideMode = with(database.hideModeQueries) {
            transactionWithResult {
                updateHideMode(if (hide) 1 else 0)
                queryHideMode()
            }.executeAsOneOrNull() == 1L
        }
        val list = with(database.todoQueries) {
            transactionWithResult { queryTodoList() }.executeAsList()
        }
        Pair(hideMode, list)
    }

    // 获取隐藏模式
    suspend fun getCompletedTodoHideMode() = withContext(Dispatchers.IO) {
        with(database.hideModeQueries) {
            queryHideMode().executeAsOne() == 1L
        }
    }

    // 检查是否有选中了
    suspend fun checkAnySelectedTodo() = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            queryTodoList().executeAsList().any { it.selected == 1L }
        }
    }

    // 检查是否都选中了
    suspend fun checkAllSelectedTodo() = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            queryTodoList().executeAsList().all { it.selected == 1L }
        }
    }

    // 删除选中的待办
    suspend fun deletedTodoIsSelected() = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                deleteSelectedTodoList()
                queryTodoList()
            }.executeAsList()
        }
    }

    // 清空待办
    suspend fun clearAllTodo() = withContext(Dispatchers.IO) {
        with(database.todoQueries) {
            transactionWithResult {
                clearTodoList()
                queryTodoList()
            }.executeAsList()
        }
    }
}