package com.app.jetpacksubmissiontwo.data.remote.network

import java.io.IOException

class NoConnectionException : IOException() {
    override val message: String?
        get() = "No connection!"
}
