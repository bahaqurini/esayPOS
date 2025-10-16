package org.aasoft.easypos

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver



actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(Database.Schema, "test.db")
}