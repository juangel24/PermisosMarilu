package com.example.permisosmarilu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Switch camSw, audioSw, ubicacionSw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camSw = findViewById(R.id.cam);
        audioSw = findViewById(R.id.audio);
        ubicacionSw = findViewById(R.id.ubicacion);

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.cam:
                        permiso(Manifest.permission.CAMERA, 1, camSw);
                        break;
                    case R.id.audio:
                        permiso(Manifest.permission.RECORD_AUDIO, 2, audioSw);
                        break;
                    case R.id.ubicacion:
                        permiso(Manifest.permission.ACCESS_FINE_LOCATION, 3, ubicacionSw);
                        break;
                }
            }
        };

        camSw.setOnClickListener(click);
        audioSw.setOnClickListener(click);
        ubicacionSw.setOnClickListener(click);

        statusPermiso(Manifest.permission.CAMERA, camSw);
        statusPermiso(Manifest.permission.RECORD_AUDIO, audioSw);
        statusPermiso(Manifest.permission.ACCESS_FINE_LOCATION, ubicacionSw);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permiso camara aceptado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Permiso camara denegado", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permiso audio aceptado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Permiso audio denegado", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permiso ubicacion aceptado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Permiso ubicacion denegado", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void permiso(String permiso, int codigo, Switch Sw){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, permiso)){
                    String[] listPermisos = new String[] {permiso};
                    ActivityCompat.requestPermissions(this, listPermisos, codigo);
                    Sw.setChecked(false);
                }
                else{
                    String[] listPermisos = new String[] {permiso};
                    ActivityCompat.requestPermissions(this, listPermisos, codigo);
                    Sw.setChecked(false);
                }
            }
            camSw.setChecked(true);
        }
    }

    public void statusPermiso(String permiso, Switch Sw){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(MainActivity.this, permiso) != PackageManager.PERMISSION_GRANTED)
                Sw.setChecked(false);
            else
                Sw.setChecked(true);
        }
    }
}
