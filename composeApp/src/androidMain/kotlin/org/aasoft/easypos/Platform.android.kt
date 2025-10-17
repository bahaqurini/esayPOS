package org.aasoft.easypos

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver





actual fun createDriver(): SqlDriver {
    val context: Context = AppContext.get()!!
    return AndroidSqliteDriver(Database.Schema, context, "test.db")

}

