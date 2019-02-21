package com.ripe.android.api

import kotlinx.coroutines.Deferred

interface BrandAPI: BaseAPI {

    fun getConfig(options: Map<String, Any> = HashMap(), callback: (config: Map<String, Any>?, isValid: Boolean) -> Unit) {
        var _options = this.getConfigOptions(options)
        _options = this.build(_options)
        val url = _options["url"] as String
        return this.cacheURL(url, _options, callback)
    }

    fun getConfigAsync(options: Map<String, Any> = HashMap()): Deferred<Map<String, Any>?> {
        var _options = this.getConfigOptions(options)
        _options = this.build(_options)
        val url = _options["url"] as String
        return this.cacheURLAsync(url, _options)
    }

    fun getConfigOptions(options: Map<String, Any> = HashMap()): Map<String, Any> {
        val configOptions = options.toMutableMap()
        val brand = options["brand"] as String? ?: this.owner.brand
        val model = options["model"] as String? ?: this.owner.model
        val url = "${this.getUrl()}brands/${brand}/models/${model}/config"
        configOptions.putAll(mapOf("url" to url, "method" to "GET"))
        return configOptions
    }

    fun getDefaults(options: Map<String, Any> = HashMap(), callback: (defaults: Map<String, Any>?, isValid: Boolean) -> Unit) {
        var _options = this.getDefaultsOptions(options)
        _options = this.build(_options)
        val url = _options["url"] as String
        return this.cacheURL(url, _options, callback)
    }

    fun getDefaultsOptions(options: Map<String, Any> = HashMap()): Map<String, Any> {
        val defaultOptions = options.toMutableMap()
        val brand = options["brand"] as String? ?: this.owner.brand
        val model = options["model"] as String? ?: this.owner.model
        val url = "${this.getUrl()}brands/${brand}/models/${model}/defaults"
        defaultOptions.putAll(mapOf("url" to url, "method" to "GET"))
        return defaultOptions
    }
}
