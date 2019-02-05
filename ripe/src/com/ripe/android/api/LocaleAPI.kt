package com.ripe.android.api

import org.json.JSONObject

interface LocaleAPI : BaseAPI {
    fun locale(value: String, locale: String, options: Map<String, Any>): JSONObject {
        return this.localeMultiple(listOf(value), locale, options)
    }

    fun localeMultiple(values: List<String>, locale: String, options: Map<String, Any>): JSONObject {
        var url = this.getUrl() + "locale"
        var _options: HashMap<String, Any> = hashMapOf(
                "url" to url,
                "method" to "GET",
                "params" to mapOf(
                        "values" to values,
                        "locale" to locale
                )
        )
        _options.putAll(options)
        _options = this._build(_options)
        url = _options["url"] as String
        return this._cacheURL(url, _options)
    }
}
