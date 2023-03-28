import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.alibaba.fastjson2.to
import com.alibaba.fastjson2.toJSONString
import com.aosp.R
import com.aosp.Route
import com.aosp.exts.loadIconByResources
import com.aosp.navParameter
import com.aosp.ui.CheckFileTreePage
import com.aosp.ui.CheckFileTreeParameter
import com.aosp.ui.HomePage
import com.aosp.ui.SettingsPage
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
@Preview
fun App() {

    val colors = MaterialTheme.colors.copy(background = R.Colors.white, onBackground = R.Colors.white)

    MaterialTheme(colors = colors){
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = Route.MAIN,
            navTransition = NavTransition()
        ) {
            scene(
                route = Route.MAIN,
                navTransition = NavTransition(),
            ) {
                HomePage(navigator)
            }
            scene(
                route = Route.SETTINGS,
                navTransition = NavTransition()
            ) {
                SettingsPage(navigator)
            }
            scene(
                route = Route.CHECK,
                navTransition = NavTransition()
            ){
                val obj = it.navParameter<CheckFileTreeParameter>()
                println("$obj")
                CheckFileTreePage(navigator, obj)
            }
        }
    }
}



fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition.Aligned(
            Alignment.Center
        ),
        size = DpSize(R.Dimens.window_withe, R.Dimens.window_height)
    )
    PreComposeWindow(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = R.Strings.app_name,
        icon = loadIconByResources("picture/icon_lunch.png"),
        resizable = true
    ) {
        App()
    }
}
