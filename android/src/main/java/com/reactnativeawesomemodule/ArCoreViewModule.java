package com.reactnativeawesomemodule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
public class ArCoreViewModule extends ReactContextBaseJavaModule  {
  ArCoreViewModule(ReactApplicationContext context) {
    super(context);
  }
  @Override
  public String getName() {
    return "ArCoreViewModule";
  }
  @ReactMethod
  public void createCalendarEvent(String path) {
    ArCoreView.setObjectWithPathFile(path);
  }
}
