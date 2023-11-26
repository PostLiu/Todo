package viewmodel

import com.postliu.Todo

sealed class TodoAction {

    /**
     * 添加待办
     * @param content 待办内容
     * @param time 添加时间戳
     */
    data class AddTodo(val content: String, val time: Long) : TodoAction()

    /**
     * 编辑待办
     * @param id 待办id
     * @param content 待办内容
     */
    data class EditTodo(val id: Long, val content: String) : TodoAction()

    /**
     * 点击的待办
     * @param todoModel 点击的待办数据
     */
    data class OnClickTodoModel(val todoModel: Todo?) : TodoAction()

    /**
     * 更新待办的选中状态
     * @param id 待办id
     * @param isSelected 选中状态
     */
    data class TodoSelectedUpdate(val id: Long, val isSelected: Boolean) : TodoAction()

    /**
     * 更新待办的完成状态
     * @param id 待办id
     * @param isCompleted 完成状态
     */
    data class TodoCompletedUpdate(val id: Long, val isCompleted: Boolean) : TodoAction()

    /**
     * 移除选中的待办
     */
    data object RemovedSelectedTodo : TodoAction()

    /**
     * 清空待办
     */
    data object ClearAllTodo : TodoAction()

    /**
     * 已完成待办的显示/隐藏
     * @param hide 如果为 true，则不展示标记为完成的待办，如果为 false，则展示标记为完成的待办
     */
    data class HideCompletedTodo(val hide: Boolean) : TodoAction()

    /**
     * 编辑模式
     * @param editMode 如果为 true，则进入编辑模式，如果为 false，则退出编辑模式
     */
    data class EditMode(val editMode: Boolean) : TodoAction()

    /**
     * 待办全选状态更新
     * @param allSelected 如果为 true，则全部选中，否则取消全选
     */
    data class AllTodoSelectedUpdate(val allSelected: Boolean) : TodoAction()

    /**
     * 菜单栏的展示/隐藏
     * @param expanded 是否展开菜单栏
     */
    data class DropMenuExpandedState(val expanded: Boolean) : TodoAction()

    /**
     * 删除待办提示窗的显示/隐藏
     * @param show 是否显示删除提示窗
     */
    data class ShowDeletedDialog(val show: Boolean) : TodoAction()

    /**
     * 清空待办提示窗的显示/隐藏
     * @param show 是否显示清空提示窗
     */
    data class ShowClearDialog(val show: Boolean) : TodoAction()

    /**
     * 展开或折叠底部待办编辑框
     */
    data object ExpandedOrCollapsedBottomSheetEdit : TodoAction()
}