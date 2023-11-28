package platform

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.UIView
import platform.UIKit.setStatusBarStyle

@OptIn(ExperimentalForeignApi::class)
actual fun setStatusBarColor(color: Int, isDark: Boolean) {
    val uiColor = UIColor.colorWithRed(
        red = ((color shr 16) and 0xFF) / 255.0,
        green = ((color shr 8) and 0xFF) / 255.0,
        blue = (color and 0xFF) / 255.0,
        alpha = 1.0
    )
    val statusBarStyle = if (isDark) {
        UIStatusBarStyleDarkContent
    } else {
        UIStatusBarStyleLightContent
    }
    val statusBar = UIView()
    statusBar.setFrame(UIApplication.sharedApplication().statusBarFrame)
    statusBar.setBackgroundColor(uiColor)
    UIApplication.sharedApplication().setStatusBarStyle(statusBarStyle)
    UIApplication.sharedApplication().keyWindow()?.addSubview(statusBar)
}