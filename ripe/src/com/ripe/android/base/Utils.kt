package base

import com.ripe.android.base.Ripe

fun Ripe.getFrameKey(view: String, position: Int, token: String = "-"): String {
    return "$view$token$position"
}

fun Ripe.parseFrameKey(frame: String, token: String = "-"): List<String> {
    return frame.split(token)
}

fun Ripe.frameNameHack(frame: String): String {
    val _frame = this.parseFrameKey(frame)
    val view = _frame[0]
    var position = if (view === "side") _frame[1] else view
    return position
}