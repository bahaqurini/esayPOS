package org.aasoft.easypos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform