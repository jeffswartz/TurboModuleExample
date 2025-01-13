package com.nativelocalstorage

import android.content.Context
import android.content.SharedPreferences
import com.nativelocalstorage.NativeLocalStorageSpec
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.opentok.android.OpentokError
import com.opentok.android.Session
import com.opentok.android.Session.SessionListener
import com.opentok.android.Stream

class NativeLocalStorageModule(reactContext: ReactApplicationContext) : NativeLocalStorageSpec(reactContext), SessionListener {
  private lateinit var session: Session
  private var context = reactContext

  override fun getName() = NAME

  override fun setItem(value: String, key: String) {
    val sharedPref = getReactApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(key, value)
    editor.apply()
  }

  override fun getItem(key: String): String? {
    val sharedPref = getReactApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val username = sharedPref.getString(key, null)
    return username.toString()
  }

  override fun removeItem(key: String) {
    val sharedPref = getReactApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.remove(key)
    editor.apply()
  }

  override fun clear() {
    val sharedPref = getReactApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.clear()
    editor.apply()
  }

  override fun initSession(apiKey: String, sessionId: String, options: ReadableMap?) {
    val sharedPref = getReactApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(apiKey, sessionId)
    editor.apply()


    session = Session.Builder(context, apiKey, sessionId)
                .build();
    session.setSessionListener(this)
    // session.connect(token)
  }

  override fun connect(sessionId: String, token: String, promise: Promise) {
    session.connect(token)
    promise.resolve("foo")
  }

  override fun disconnect(sessionId: String, promise: Promise) {
    session.disconnect()
    promise.resolve(null)
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

  companion object {
    const val NAME = "NativeLocalStorage"
  }
}


