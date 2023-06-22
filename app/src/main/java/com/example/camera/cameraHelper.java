package com.example.camera;

import android.content.Context;

import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class cameraHelper {

    private Context context;
    private LifecycleOwner lifecycleOwner;
    private PreviewView previewView;

    public cameraHelper(Context context, LifecycleOwner lifecycleOwner, PreviewView previewView) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.previewView = previewView;
    }

    public void startCamera(){
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderListenableFuture.addListener(() -> {
            ProcessCameraProvider processCameraProvider = null;
            try {
                processCameraProvider = cameraProviderListenableFuture.get();
                processCameraProvider.unbindAll();
                bind(processCameraProvider);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void bind(ProcessCameraProvider processCameraProvider){
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
    }

    public void checkPermission(String[] permissao){
        ActivityCompat.requestPermissions((MainActivity) context, permissao, 1) ;

    }

}
