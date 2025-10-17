package org.aasoft.easypos.controller


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.aasoft.easypos.Database
import org.aasoft.easypos.User

import org.aasoft.easypos.createDriver


fun createDatabase(): Database {
    val driver = createDriver()
    val database = Database(driver)

    // Do more work with the database (see below).

    return database
}
class DB {

    val database = createDatabase()
    init {
        database.userQueries
    }
    val dbQuery = database.userQueries
    fun insert(name: String,email: String) {
        dbQuery.insertUser(name,email)
    }
    fun getAll(): List<User> {
        return dbQuery.selectAll().executeAsList()
    }
    fun delete(id: Long) {
        dbQuery.deleteUser(id)
    }

}
@Composable
fun Test(name: String) {
    Text(text = "Hello $name!")

}