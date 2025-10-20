package org.aasoft.easypos.controller
import org.aasoft.easypos.Database


import org.aasoft.easypos.createDriver


fun createDatabase(): Database {
    val driver = createDriver()
    val database = Database(driver)


    return database
}

