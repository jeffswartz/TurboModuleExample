package com.opentokreactnative

import android.content.Context
import android.content.SharedPreferences
import com.opentokreactnative.NativeLocalStorageSpec
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError
import com.opentok.android.Session
import com.opentok.android.Session.SessionListener
import com.opentok.android.Session.SignalListener
import com.opentok.android.Stream

class NativeLocalStorageModule(reactContext: ReactApplicationContext) : NativeLocalStorageSpec(reactContext), SessionListener, SignalListener {
  private lateinit var session: Session
  private var context = reactContext

  override fun getName() = NAME


  override fun initSession(apiKey: String, sessionId: String, options: ReadableMap?) {

    session = Session.Builder(context, apiKey, sessionId)
                .build()

    OTRN.putSession(session);

    session.setSessionListener(this)
    // session.connect(token)
  }

  override fun connect(sessionId: String, token: String, promise: Promise) {
    session.connect(token)
    promise.resolve(null)
  }

  override fun disconnect(sessionId: String, promise: Promise) {
    session.disconnect()
    promise.resolve(null)
  }

  override fun sendSignal(sessionId: String, type: String, data: String) {
    session.sendSignal(type, data)
  }

  override fun onConnected(session: Session) {
      val payload =
        Arguments.createMap().apply {
          putString("sessionId", session.getSessionId())
          putString("connectionId", session.getConnection().getConnectionId())
        }
      emitOnSessionConnected(payload)
  }

  override fun onDisconnected(session: Session) {
      val payload =
        Arguments.createMap().apply {
          putString("sessionId", session.getSessionId())
          putString("connectionId", session.getConnection().getConnectionId())
        }
      emitOnSessionDisconnected(payload)
  }

  override fun onStreamReceived(session: Session, stream: Stream) {
      OTRN.putStream(stream);
      val payload =
        Arguments.createMap().apply {
          putString("streamId", stream.streamId)
        }
      emitOnStreamCreated(payload)
  }

  override fun onStreamDropped(session: Session, stream: Stream) {
      val payload =
        Arguments.createMap().apply {
          putString("streamId", stream.streamId)
        }
      emitOnStreamDestroyed(payload)
  }

  override fun onError(session: Session, opentokError: OpentokError) {
      //
  }

  override fun onSignalReceived(session: Session, type: String, data: String, connection: Connection) {
      val payload =
        Arguments.createMap().apply {
          putString("sessionId", session.sessionId)
          putString("connectionId", connection.connectionId)
          putString("type", type)
          putString("data", data)
        }
      emitOnSignalReceived(payload)
  }

  companion object {
    const val NAME = "NativeLocalStorage"
  }
}


