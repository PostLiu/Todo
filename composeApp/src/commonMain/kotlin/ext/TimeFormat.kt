package ext

import kotlin.time.Duration.Companion.milliseconds

fun Long.formatDateBefore(): String {
    return milliseconds.toComponents { days, hours, minutes, seconds, _ ->
        when {
            days > 365 -> "${days / 365}年前"
            days > 30 -> "${days / 30}个月前"
            days > 7 -> "${days / 7}周前"
            days > 0 -> "${days}天前"
            hours > 0 -> "${hours}小时前"
            minutes > 0 -> "${minutes}分钟前"
            seconds > 0 -> "${seconds}秒前"
            else -> "刚刚"
        }
    }
}