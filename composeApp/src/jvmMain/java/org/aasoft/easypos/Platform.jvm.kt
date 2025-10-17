package org.aasoft.easypos

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

actual fun createDriver(): SqlDriver= JdbcSqliteDriver("jdbc:sqlite:test.db", Properties(), Database.Schema)