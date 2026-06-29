package com.draco.ladb.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.draco.ladb.R

/**
 * Application language manager.
 *
 * Handles language metadata and locale switching using
 * AppCompatDelegate.setApplicationLocales().
 */
object LanguageManager {

    /**
     * Represents one selectable application language.
     */
    data class AppLanguage(
        val tag: String,
        @StringRes val label: Int
    )

    /**
     * Available application languages.
     *
     * Empty tag = Follow system.
     */
    private val languages = listOf(
        AppLanguage("", R.string.language_system),
        AppLanguage("en", R.string.language_en),
        AppLanguage("in", R.string.language_id),
        AppLanguage("fr", R.string.language_fr),
        AppLanguage("es", R.string.language_es),
        AppLanguage("pt", R.string.language_pt),
        AppLanguage("zh", R.string.language_zh),
        AppLanguage("ar", R.string.language_ar)
    )

    /**
     * Returns currently applied language tag.
     *
     * Empty string means Follow system.
     */
    fun getCurrentTag(): String {
        val locales = AppCompatDelegate.getApplicationLocales()

        if (locales.isEmpty)
            return ""

        val tag = locales.toLanguageTags()
	    .substringBefore(',')

        return when (tag) {
	    "id" -> "in"
	    else -> tag
    }
    }
    /**
     * Returns current selected language index.
     */
    fun getCurrentIndex(): Int {

        val tag = getCurrentTag()

        val index = languages.indexOfFirst {
            it.tag == tag
        }

        return if (index >= 0) index else 0
    }

    /**
     * Returns localized language name.
     */
    fun getCurrentLanguageName(
        context: Context
    ): String {

        val language = languages[getCurrentIndex()]

        return context.getString(language.label)
    }

    /**
     * Apply selected language.
     */
    fun applyLanguage(
        language: AppLanguage
    ) {

        if (language.tag.isBlank()) {

            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.getEmptyLocaleList()
            )

            return
        }

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                language.tag
            )
        )
    }

    /**
     * Apply selected language by index.
     */
    fun applyLanguage(
        index: Int
    ) {

        if (index !in languages.indices)
            return

        applyLanguage(
            languages[index]
        )
    }

    /**
     * Reset application language to follow system.
     */
    fun resetLanguage() {

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.getEmptyLocaleList()
        )
    }

    /**
     * Returns currently selected language.
     */
    fun getCurrentLanguage(): AppLanguage =
        languages[getCurrentIndex()]

    /**
     * Returns all supported languages.
     */
    fun getLanguages(): List<AppLanguage> = languages
}
