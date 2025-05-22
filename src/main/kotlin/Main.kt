import di.appModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.ui.DragonBallApp

suspend fun main() {
    startKoin { modules(appModule) }
    val ui: DragonBallApp = getKoin().get()
    ui.start()
}