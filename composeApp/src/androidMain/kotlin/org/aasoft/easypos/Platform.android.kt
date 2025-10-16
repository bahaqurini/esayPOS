package org.aasoft.easypos

import android.content.Context
import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
//import org.aasoft.easypos.Database
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun createDriver(): SqlDriver {
    val context: Context = AppContext.get()!!
    return AndroidSqliteDriver(Database.Schema, context, "test.db")

}