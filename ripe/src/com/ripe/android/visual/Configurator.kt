package visual

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import base.frameNameHack
import base.getFrameKey
import com.ripe.android.base.ObservableCallback
import com.ripe.android.base.ObservableSyncCallback
import com.ripe.android.base.Ripe
import com.ripe.android.visual.Visual
import kotlinx.coroutines.*
import java.net.URL

class Configurator constructor(private val imageView: ImageView, override val owner: Ripe, override val options: Map<String, Any> = HashMap()) :
        Visual(owner, options) {

    private val width: Int = options["width"] as? Int ?: 1000
    private val height: Int = options["height"] as? Int ?: 1000
    private val size: Int? = options["size"] as? Int
    private var ready = false
    private var ownerBinds = HashMap<String, ObservableCallback>()
    private var frames: Map<String, Int>? = null
    private var buffer = HashMap<String, Bitmap?>()

    init {
        this.initLayout()
        this.bind()
        val hasModel = this.owner.brand != null && this.owner.model != null
        val self = this
        @Suppress("experimental_api_usage")
        if (hasModel) MainScope().launch { self.updateConfig() }
    }

    fun highlight(part: String) {}

    fun lowlight(part: String? = null) {}

    override fun update(state: Map<String, Any>) {
        super.update(state)

        if (!this.ready) {
            return
        }


    }

    private suspend fun updateConfig() {
        this.ready = false
        this.lowlight()

        this.frames = this.owner.api.getFramesAsync().await()
        this.populateBuffer()
    }

    private fun initLayout() {}

    private fun bind() {
        this.bindOwner("config") {
            if (it != null) {
                val self = this
                @Suppress("experimental_api_usage")
                MainScope().launch { self.updateConfig() }
            }
        }

        this.bindOwner("selected_part") {
            val part = it!!["part"] as String
            this.highlight(part)
        }

        this.bindOwner("deselected_part") {
            val part = it!!["part"] as String
            this.lowlight(part)
        }
    }

    private fun bindOwner(event: String, callback: ObservableSyncCallback) {
        this.ownerBinds[event] = this.owner.bind(event, callback = callback)
    }

    private fun populateBuffer() {
        this.buffer.clear()
        for ((view, frames) in this.frames!!) {
            for (index in 0..frames) {
                this.buffer[this.owner.getFrameKey(view, index)] = null
            }
        }
    }

    private fun loadFrame(view: String, position: Int, options: Map<String, Any>? = null): Deferred<Any> {
        val frame = this.owner.getFrameKey(view, position = position)

        val url = this.owner.api.getImageUrl(mapOf("frame" to this.owner.frameNameHack(frame)))
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL(url)
            val inputStream = url.openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            withContext(Dispatchers.Main) {
                this.
            }
        }
    }

    override fun deinit() {
        super.deinit()

        for ((key, value) in this.ownerBinds) {
            this.owner.unbind(key, value)
        }
    }
}