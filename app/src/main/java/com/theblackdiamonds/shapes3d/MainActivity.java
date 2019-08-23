package com.theblackdiamonds.shapes3d;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ShapeType shapeType = ShapeType.CUBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        Button btn_cube = findViewById(R.id.button_cube);
        Button btn_sphere = findViewById(R.id.button_sphere);
        Button btn_cylinder = findViewById(R.id.button_cylinder);

        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {

            if (shapeType == ShapeType.CUBE) {
                placeCube(hitResult.createAnchor());
            } else if (shapeType == ShapeType.SPHERE) {
                placeSphere(hitResult.createAnchor());
            } else if (shapeType == ShapeType.CYLINDER) {
                placeCylinder(hitResult.createAnchor());
            }

        }));

        btn_cube.setOnClickListener(view -> shapeType = ShapeType.CUBE);
        btn_sphere.setOnClickListener(view -> shapeType = ShapeType.SPHERE);
        btn_cylinder.setOnClickListener(view -> shapeType = ShapeType.CYLINDER);
    }

    private void placeCylinder(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(MainActivity.this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeCylinder(0.1f, 0.2f,
                            new Vector3(0f, 0.2f, 0f), material);
                    placeModel(modelRenderable, anchor);
                });
    }

    private void placeSphere(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(MainActivity.this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeSphere(0.1f,
                            new Vector3(0f, 0.1f, 0f), material);
                    placeModel(modelRenderable, anchor);
                });
    }

    private void placeCube(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(MainActivity.this, new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable = ShapeFactory.makeCube(new Vector3(0.1f, 0.1f, 0.1f),
                            new Vector3(0f, 0f, 0f), material);
                    placeModel(modelRenderable, anchor);
                });
    }

    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }

    private enum ShapeType {
        CUBE,
        SPHERE,
        CYLINDER
    }
}