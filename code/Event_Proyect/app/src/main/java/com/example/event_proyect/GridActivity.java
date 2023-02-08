package com.example.event_proyect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.event_proyect.databinding.ActivityGridBinding;

public class GridActivity extends AppCompatActivity {

    ActivityGridBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] flowerName = {"Navidad San Rafael","Portal Shopping Event","Electronic Event 2023", "Romeo Santos",
                "Año viejo amazonas 2023","Desfile Confraternidad 2022","Sinfonia Quito","Arte Contemporaneo","Evento"};
        int[] flowerImages = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,
                R.drawable.h,R.drawable.i};

        GridAdapter gridAdapter = new GridAdapter(GridActivity.this,flowerName,flowerImages);
        binding.gridView.setAdapter(gridAdapter);


        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(GridActivity.this,"You Clicked on "+ flowerName[position],Toast.LENGTH_SHORT).show();

            }
        });

    }
}