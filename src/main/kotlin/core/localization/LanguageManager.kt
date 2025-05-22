package presentation.i18n

import java.util.*

object LocalizationManager {
    private var locale: Locale = Locale("en")
    private var bundle: ResourceBundle = loadBundle()

    private fun loadBundle(): ResourceBundle {
        return ResourceBundle.getBundle("i18n.strings", locale)
    }

    fun setLanguage(languageCode: String) {
        locale = Locale(languageCode)
        bundle = loadBundle()
    }

    fun translate(key: String): String {
        return try {
            bundle.getString(key)
        } catch (e: MissingResourceException) {
            "??$key??"
        }
    }
}
