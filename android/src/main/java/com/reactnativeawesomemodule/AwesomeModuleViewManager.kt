package com.reactnativeawesomemodule

import android.view.View
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class AwesomeModuleViewManager(reactContext: ReactApplicationContext) : SimpleViewManager<ArCoreView>() {
  override fun getName() = "AwesomeModuleView"

  override fun createViewInstance(reactContext: ThemedReactContext): ArCoreView {
    return ArCoreView(reactContext)
  }

  @ReactProp(name = "color")
  fun setColor(view: View, color: String) {
//    view.setBackgroundColor(Color.parseColor(color))
  }
}
