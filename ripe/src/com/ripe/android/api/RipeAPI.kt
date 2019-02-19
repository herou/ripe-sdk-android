package com.ripe.android.api

import com.ripe.android.base.Ripe

class RipeAPI constructor(override var owner: Ripe) :
        BaseAPI,
        BrandAPI,
        LocaleAPI {

    constructor(options: Map<String, Any>) : this(Ripe(null, null, options))
}
