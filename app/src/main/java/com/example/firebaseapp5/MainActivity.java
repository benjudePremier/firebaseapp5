package com.example.firebaseapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import Models.Profesionales;

public class MainActivity extends AppCompatActivity {

    private EditText ednombre, edsalario, edprofesion;
    private Button button;

    FirebaseDatabase firebase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ednombre = (EditText) findViewById(R.id.etnombre);
        edsalario = (EditText) findViewById(R.id.etsalario);
        edprofesion = (EditText) findViewById(R.id.etprofesion);
        button = (Button) findViewById(R.id.btn);

        inicializarBase();

        // ... operaciones de b.d ...

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ednombre.equals(""))
                {
                    Profesionales p = new Profesionales();

                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(ednombre.getText().toString());
                    p.setSalario(edsalario.getText().toString());
                    p.setProfesion(edprofesion.getText().toString());

                    databaseReference.child("Profesionales").child(p.getId()).setValue(p);

                    Toast.makeText(getBaseContext(), "Se ha guardado el profesional", Toast.LENGTH_LONG).show();

                }else
                {
                    Toast.makeText(getBaseContext(), "No se ha guardado", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    // Método me permitirá obtener mi base de datos

    public void inicializarBase()
    {
        FirebaseApp.initializeApp(this);
        firebase = FirebaseDatabase.getInstance();
        databaseReference = firebase.getReference();
    }

    public void Listado(View v)
    {
        Intent i = new Intent(this, Listado_act.class);
        startActivity(i);
    }

}