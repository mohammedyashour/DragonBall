package i18n

import java.text.MessageFormat
import java.util.*

object LanguageManager {
    private var bundle: ResourceBundle = ResourceBundle.getBundle("i18n/strings", Locale("en"))

    fun setLanguage(lang: String) {
        val locale = when (lang.lowercase()) {
            "ar" -> Locale("ar")
            "en" -> Locale("en")
            else -> Locale("en")
        }
        bundle = ResourceBundle.getBundle("i18n/strings", locale)
    }

    fun t(key: String, vararg args: Any): String {
        val value = bundle.getString(key)
        return if (args.isNotEmpty()) MessageFormat.format(value, *args) else value
    }
}
