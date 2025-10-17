package org.aasoft.easypos

import app.cash.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}


expect fun createDriver(): SqlDriver