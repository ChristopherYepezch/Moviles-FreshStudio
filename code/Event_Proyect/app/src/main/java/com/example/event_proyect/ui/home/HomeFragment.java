package com.example.event_proyect.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.event_proyect.GridActivity;
import com.example.event_proyect.MainActivity;
import com.example.event_proyect.MenuHome;
import com.example.event_proyect.R;
import com.example.event_proyect.TableActivity;
import com.example.event_proyect.databinding.ActivityMenuHomeBinding;
import com.example.event_proyect.databinding.FragmentHomeBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private AppBarConfiguration mAppBarConfiguration;

    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;
    private TextView txtid, txtnombres, txtemail;
    private ImageView imagenUser;
    Button btnLogOut,btnSeeTable,btnSeeGrid;
    //Variables opcionales para desloguear de google tambien
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        imagenUser = binding.imagenUsuario;
        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        imagenUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(requireContext(), imagenUser);
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
        txtid = binding.txtId;
        txtnombres = binding.txtNombres;
        txtemail = binding.txtCorreo;
        btnLogOut = binding.btnLogout;
        btnSeeTable = binding.btnSeeTable;
        btnSeeGrid = binding.btnGrid;
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
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

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
                            Intent mainActivity = new Intent( requireContext(), MainActivity.class);
                            startActivity(mainActivity);
                            requireActivity().finish();
                        }else{
                            Toast.makeText(getContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        btnSeeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( getContext(), TableActivity.class);
                startActivity(intent);
            }
        });

        btnSeeGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( requireContext(), GridActivity.class);
                startActivity(intent);
            }
        });

        return root;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}