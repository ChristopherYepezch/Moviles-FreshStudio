package com.example.event_proyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button mBtnAnadir,btnExit;
    private ListView mListView;
    private EditText mEditText;
    private List<String> mLista= new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    String CHANNEL_ID = "NOTIFICACION";
    Integer NOTIFICATION_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        mBtnAnadir=findViewById(R.id.btnAgregar);
        mBtnAnadir.setOnClickListener(this);
        mListView=findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        mEditText=findViewById(R.id.etLista);
        btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAgregar: String texto= mEditText.getText().toString().trim();
                mLista.add(texto);
                create_Notification_Channel();
                create_notification(texto);
                mEditText.getText().clear();
                mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
                mListView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(this, "Item Clicked"+position,Toast.LENGTH_SHORT).show();
    }

    public void create_notification (String texto)
    {
        String notification_result="";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle("Fresh Studio");
        builder.setColor(getResources().getColor(R.color.purple_200));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(getResources().getColor(R.color.purple_200),1000,1000);
        builder.setVibrate(new long []{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        notification_result ="Añadida la actividad " + texto + " Exitosamente";
        builder.setContentText(notification_result);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(notification_result));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    public void  create_Notification_Channel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}