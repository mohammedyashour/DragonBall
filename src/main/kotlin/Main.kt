import di.appModule
import i18n.LanguageManager.setLanguage
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.ui.DragonBallApp

suspend fun main() {
    startKoin { modules(appModule) }
    setLanguage("ar")
    val ui: DragonBallApp = getKoin().get()
    ui.start()
}