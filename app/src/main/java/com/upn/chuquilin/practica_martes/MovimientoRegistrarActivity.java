package com.upn.chuquilin.practica_martes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upn.chuquilin.practica_martes.db.AppDatabase;
import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.entities.LocationData;
import com.upn.chuquilin.practica_martes.entities.Movimientos;
import com.upn.chuquilin.practica_martes.mapasController.MapsActivity;
import com.upn.chuquilin.practica_martes.repositories.CuentaRepository;
import com.upn.chuquilin.practica_martes.repositories.MovimientoRepository;
import com.upn.chuquilin.practica_martes.services.MovimientoService;
import com.upn.chuquilin.practica_martes.utils.CamaraUtils;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovimientoRegistrarActivity extends AppCompatActivity {

    Spinner  spTipoMov;
    EditText edMontoMov;
    EditText edMotivoMov;
    TextView tvLatitudMov;
    TextView tvLongitudMov;
    TextView tvUrlImagenMov;

    Button btCamaraMov;
    Button btGaleriaMov;
    Button btRegistrarMov;

    String seleccionSpinner;
    ImageView ivBauchesMov;
    String imagenBase64 = "";
    String urlImage = "";
    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_registrar);

        int idObtener;
        idObtener = getIntent().getIntExtra("id",0);
        Log.d("APP_MAIN: idRM", String.valueOf(idObtener));

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        MovimientoRepository repositoryM = db.movimientoRepository();

        spTipoMov     = findViewById(R.id.spTipoMov);
        edMontoMov    = findViewById(R.id.edMontoMov);
        edMotivoMov   = findViewById(R.id.edMotivoMov);
        tvLatitudMov  = findViewById(R.id.tvLatitudMov);
        tvLongitudMov = findViewById(R.id.tvLongitudMov);
        ivBauchesMov  = findViewById(R.id.ivBaucherMov);
        tvUrlImagenMov= findViewById(R.id.tvUrlImagenMov);
        btCamaraMov   = findViewById(R.id.btCamaraMov);
        btGaleriaMov  = findViewById(R.id.btGaleriaMov);
        btRegistrarMov= findViewById(R.id.btRegistrarMov);

//***************SPINNER**********************
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.datos_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoMov.setAdapter(adapter);
        spTipoMov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionSpinner = parent.getItemAtPosition(position).toString();
                Log.i("MAIN_APP: Spinner", seleccionSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//************************************************
//        Intent intent =  new Intent(getApplicationContext(), MapsActivity.class);
//        startActivity(intent);

        double Latitud = LocationData.getInstance().getLatitude();
        double Longitud = LocationData.getInstance().getLongitude();
        Log.d("MAIN_APP3-Lat", String.valueOf(Latitud));
        Log.d("MAIN_APP3-Long", String.valueOf(Longitud));

        tvLatitudMov.setText(String.valueOf(Latitud));
        tvLongitudMov.setText(String.valueOf(Longitud));



        btCamaraMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOpenCamera();
            }
        });

        btGaleriaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btRegistrarMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMontoMov.getText().toString().trim().isEmpty() || edMotivoMov.getText().toString().trim().isEmpty() || seleccionSpinner.equals(" ")) {
                    Toast.makeText(getBaseContext(), "Llenar Datos", Toast.LENGTH_SHORT).show();
                } else {
                    Movimientos movimientos = new Movimientos();
                    movimientos.cuentaID       = idObtener;
                    movimientos.tipoMovimiento = seleccionSpinner;
                    movimientos.monto          = Integer.parseInt(String.valueOf(edMontoMov.getText()));
                    movimientos.motivo         = String.valueOf(edMotivoMov.getText());
                    movimientos.latitud        = String.valueOf(Latitud);
                    movimientos.longitud       = String.valueOf(Longitud);
                    movimientos.imagenBase64   = imagenBase64;
                    movimientos.urlimagen      = urlImage;
                    movimientos.sincronizadoMovimientos = false;

                    //movimientos.tipoMovimiento = seleccionSpinner;

                    repositoryM.createMovimientos(movimientos);
                    Log.i("MAIN_APP: GuardaM en DB", new Gson().toJson(movimientos));
                }
                Intent intent = new Intent(getApplicationContext(), CuentaDetallesActivity.class);
                intent.putExtra("id", idObtener);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivBauchesMov.setImageBitmap(photo);

            imagenBase64 = BitmaptoBase64(photo);

            if (isNetworkConnected()) {
                base64toLink(imagenBase64);

            }

        }
        if(requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close(); // close cursor

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ivBauchesMov.setImageBitmap(bitmap);

            imagenBase64 = BitmaptoBase64(bitmap);

            if (isNetworkConnected()) {
                base64toLink(imagenBase64);

            }

//            base64toLink(base64);
        }
    }



    private void handleOpenCamera() {
        if(checkSelfPermission(android.Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED)
        {
            // abrir camara
            Log.i("MAIN_APP", "Tiene permisos para abrir la camara");
            openCamara();
        } else {
            // solicitar el permiso
            Log.i("MAIN_APP", "No tiene permisos para abrir la camara, solicitando");
            String[] permissions = new String[] {Manifest.permission.CAMERA};
            requestPermissions(permissions, 1001);
        }
    }

    private void openCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAMERA_REQUEST);
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST);
    }


    private String BitmaptoBase64 (Bitmap imagenBitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagenBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String Resulbase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.i("APP_MAIN", Resulbase64);
        return Resulbase64;
    }

    private Bitmap Base64toBitmap(String imagenBase64){
        byte[] decodedBytes = Base64.decode(imagenBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length);

    }
    private void base64toLink(String base64) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://demo-upn.bit2bittest.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovimientoService service = retrofit1.create(MovimientoService.class);
        Call<MovimientoService.ImagenResponse> call = service.guardarImage(new MovimientoService.ImagenToSave(base64));
        call.enqueue(new Callback<MovimientoService.ImagenResponse>() {
            @Override
            public void onResponse(Call<MovimientoService.ImagenResponse> call, Response<MovimientoService.ImagenResponse> response) {
                if (response.isSuccessful()) {
                    MovimientoService.ImagenResponse imageResponse = response.body();
                    Log.i("Respues", response.toString());
                    urlImage = "https://demo-upn.bit2bittest.com/" + imageResponse.getUrl();
                    tvUrlImagenMov.setText(urlImage);
                    Toast.makeText(getBaseContext(), "Link GENERADO", Toast.LENGTH_SHORT).show();
                    Log.i("Imagen url:", urlImage);

                } else {

                    Log.e("Error cargar imagen",response.toString());
                }
            }

            @Override
            public void onFailure(Call<MovimientoService.ImagenResponse> call, Throwable t) {

            }
        });

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}