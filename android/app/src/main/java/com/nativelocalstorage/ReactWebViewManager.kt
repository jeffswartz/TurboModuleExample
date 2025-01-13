package com.nativelocalstorage

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.CustomWebViewManagerInterface;
import com.facebook.react.viewmanagers.CustomWebViewManagerDelegate;

@ReactModule(name = ReactWebViewManager.REACT_CLASS)
class ReactWebViewManager(context: ReactApplicationContext) : SimpleViewManager<ReactWebView>(), CustomWebViewManagerInterface<ReactWebView> {
  private val delegate: CustomWebViewManagerDelegate<ReactWebView, ReactWebViewManager> =
    CustomWebViewManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<ReactWebView> = delegate

  override fun getName(): String = REACT_CLASS

  override fun createViewInstance(context: ThemedReactContext): ReactWebView = ReactWebView(context)

  @ReactProp(name = "streamId")
  override public fun setStreamId(view: ReactWebView, streamId: String?) {
    view.setStreamId(streamId)
  }

  @ReactProp(name = "sessionId")
  override public fun setSessionId(view: ReactWebView, sessionId: String?) {
    view.setSessionId(sessionId)
  }

  companion object {
    const val REACT_CLASS = "CustomWebView"
  }

  override fun getExportedCustomBubblingEventTypeConstants(): Map<String, Any> =
      mapOf(
          "onSubscriberConnected" to
              mapOf(
                  "phasedRegistrationNames" to
                      mapOf(
                          "bubbled" to "onSubscriberConnected",
                          "captured" to "onSubscriberConnectedCapture"
                      )))
}