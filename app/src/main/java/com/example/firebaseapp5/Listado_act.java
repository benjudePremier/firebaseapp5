package com.example.firebaseapp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Models.Profesionales;

public class Listado_act extends AppCompatActivity {

    private ListView listado;

    ArrayList<Profesionales> listadoProfesional = new ArrayList<Profesionales>();

    Profesionales profesionalSelected;

    FirebaseDatabase firebase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_act);

        listado = (ListView)findViewById(R.id.lv);

        iniciarBase();

        // ... Mostrar Datos desde firebase.

        databaseReference.child("Profesionales").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot objSnapshot: snapshot.getChildren())
                {
                    Profesionales p = objSnapshot.getValue(Profesionales.class);

                    listadoProfesional.add(p);

                    ArrayAdapter adapt = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, listadoProfesional);
                    listado.setAdapter(adapt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Método para obtener la selección del LISTVIEW.

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                profesionalSelected = (Profesionales) parent.getItemAtPosition(position);

            }
        });

    }

    // Método para eliminar de Firebase
    public void Eliminar(View v)
    {
        Profesionales p = new Profesionales();
        p.setId(profesionalSelected.getId());
        databaseReference.child("Profesionales").child(p.getId()).removeValue();
        Toast.makeText(this, "Has eliminado un Profesional", Toast.LENGTH_LONG).show();
    }



    public void iniciarBase()
    {
        FirebaseApp.initializeApp(this);
        firebase = FirebaseDatabase.getInstance();
        databaseReference = firebase.getReference();
    }
}