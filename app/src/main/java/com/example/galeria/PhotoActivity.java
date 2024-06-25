package com.example.galeria;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photo);

        //obtenha o caminho da foto que foi envia via o intent de criaçao
        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path");
        //carregue a foto em um bitmap
        Bitmap bitmap = Util.getBitmap(photoPath);
        ImageView imPhoto = findViewById(R.id.imPhoto);
        imPhoto.setImageBitmap(bitmap);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //seta tbphoto como actionbar padrao de photo activity
        Toolbar toolbar = findViewById(R.id.tbPhoto);
        setSupportActionBar(toolbar);

        //botao para voltar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void setSupportActionBar(@Nullable androidx.appcompat.widget.Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    //cria as opçoes de menu definidas no arquivo de menu passado e as adiciona no menu da activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_activity_tb, menu);
        return true;
    }

    //toda vez que o icone de compartilhar for clicado, entao sera executado o codigo que compartilha a foto
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opShare) {
            sharePhoto();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // codigo para compartilhar foto
    void sharePhoto() {

        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this, "com.example.galeria.fileprovider", new File(photoPath));
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, photoUri);
        i.setType("image/jpeg");
        startActivity(i);
    }
}