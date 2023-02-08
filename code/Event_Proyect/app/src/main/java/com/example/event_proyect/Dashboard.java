package com.example.event_proyect;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;
    private TextView txtid, txtnombres, txtemail;
    private ImageView imagenUser;
    Button btnLogOut,btnSeeTable,btnSeeGrid;
    //Variables opcionales para desloguear de google tambien
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imagenUser = findViewById(R.id.imagenUsuario);
        imagenUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(Dashboard.this, imagenUser);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.share:
                                shareImage();
                                return true;
                            case R.id.delete:
                                deleteImage();
                                return true;
                            case R.id.info:
                                showInfo();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
                return true;
            }
        });
        txtid = findViewById(R.id.txtId);
        txtnombres = findViewById(R.id.txtNombres);
        txtemail = findViewById(R.id.txtCorreo);
        btnLogOut = findViewById(R.id.btnLogout);
        btnSeeTable = findViewById(R.id.btnSeeTable);
        btnSeeGrid = findViewById(R.id.btnGrid);
        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //set datos:
        try{
            txtid.setText(currentUser.getUid());
            txtnombres.setText(currentUser.getDisplayName());
            txtemail.setText(currentUser.getEmail());
        }
        catch(Exception e){

        }

        //cargar imágen con glide:
        Glide.with(this).load(currentUser.getPhotoUrl()).into(imagenUser);

        //Configurar las gso para google signIn con el fin de luego desloguear de google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();

                //Cerrar sesión con google tambien: Google sign out
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Abrir MainActivity con SigIn button
                        if(task.isSuccessful()){
                            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(mainActivity);
                            Dashboard.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        btnSeeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( Dashboard.this, TableActivity.class);
                startActivity(intent);
            }
        });

        btnSeeGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Dashboard.this, GridActivity.class);
                startActivity(intent);
            }
        });

    }



    private void shareImage() {
        // Código para compartir la imagen
    }

    private void deleteImage() {
        // Código para eliminar la imagen
    }

    private void showInfo() {
        // Código para mostrar la información de la imagen
    }


}

