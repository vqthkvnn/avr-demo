package com.reactnativeawesomemodule;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.File;

public class ArCoreView extends FrameLayout {
  public static ReactActivity reactActivity = null;
  private ThemedReactContext context;

  private ArFragment arFragment;
  private static ModelRenderable objectRender;
  private Float SCALE = 0.1f;
  private AnchorNode anchorNodeDelete;

  public static void setContext(ReactActivity context) {
    reactActivity = context;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public ArCoreView(ThemedReactContext context) {
    super(context);
    this.context = context;
    init();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public void init() {
    inflate(reactActivity, R.layout.activity_main, this);
    arFragment = (ArFragment) reactActivity.getSupportFragmentManager().findFragmentById(R.id.ui_fragment);
    Uri uri  =  Uri.parse("chair.sfb");
    Log.e("URI", uri.toString());
    ModelRenderable.builder()
      .setSource(reactActivity, uri)
      .build()
      .thenAccept(modelRenderable -> objectRender = modelRenderable)
      .exceptionally(
        throwable -> {
          Toast toast =
            Toast.makeText(reactActivity, "Unable to load andy renderable", Toast.LENGTH_LONG);
          toast.setGravity(Gravity.CENTER, 0, 0);
          toast.show();
          return null;
        }
      );
    arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
      if (objectRender == null) {
        Log.e("LOI", "ANH NULL");
        return;
      }
      Anchor anchor = hitResult.createAnchor();
      AnchorNode anchorNode = new AnchorNode(anchor);
      anchorNode.setParent(arFragment.getArSceneView().getScene());

      // add
      TransformableNode object = new TransformableNode(arFragment.getTransformationSystem());
      object.setParent(anchorNode);
      object.setRenderable(objectRender);
      object.select();

      object.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));
      object.getRotationController().setEnabled(true);
      object.getScaleController().setEnabled(true);
      object.setOnTouchListener(new Node.OnTouchListener() {
        @Override
        public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
          anchorNodeDelete = anchorNode;
          return true;
        }
      });
    });
  }

  public void setSCALE(Float SCALE) {
    this.SCALE = SCALE;
  }

  public Float getSCALE() {
    return SCALE;
  }

  public Boolean deleteNodeObject() {
    try {
      arFragment.getArSceneView().getScene().removeChild(anchorNodeDelete);
      anchorNodeDelete.getAnchor().detach();
      anchorNodeDelete.setParent(null);
      anchorNodeDelete = null;
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  public static void setObjectWithPathFile(String file){
    ModelRenderable.builder()
      .setSource(reactActivity, Uri.parse(file))
      .build()
      .thenAccept(modelRenderable -> objectRender = modelRenderable)
      .exceptionally(
        throwable -> {
          Toast toast =
            Toast.makeText(reactActivity, "Unable to load andy renderable", Toast.LENGTH_LONG);
          toast.setGravity(Gravity.CENTER, 0, 0);
          toast.show();
          return null;
        }
      );
  }
}
