package org.aasoft.easypos
import android.content.Context
import java.lang.ref.WeakReference

object AppContext {

    private var value: WeakReference<Context?>? = null

    fun set(context: Context) {
        value = WeakReference(context)
    }

    internal fun get(): Context? {
        return value?.get()
    }

}