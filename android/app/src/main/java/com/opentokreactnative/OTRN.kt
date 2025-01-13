package com.opentokreactnative

import com.opentok.android.Session
import com.opentok.android.Stream

public final class OTRN {

  companion object {
    lateinit var session: Session
    lateinit var stream: Stream

    fun putSession(s : Session) {
        this.session = s
    }

    fun getSess() : Session {
        return this.session
    }

    fun putStream(s : Stream) {
        this.stream = s
    }

    fun getStr() : Stream {
        return this.stream
    }
  }
}