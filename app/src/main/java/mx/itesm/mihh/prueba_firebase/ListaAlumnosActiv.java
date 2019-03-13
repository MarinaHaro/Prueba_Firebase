package mx.itesm.mihh.prueba_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListaAlumnosActiv extends AppCompatActivity {

    private ArrayList<String> arrAlumnos; //Datos para la lista
    private ListView lstAlumnos; // Componente lista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alumnos);

        lstAlumnos = findViewById(R.id.lstAlumnos);
        arrAlumnos = new ArrayList<>();
        descargarAlumnos();
    }


    public void descargarAlumnos() {
        FirebaseDatabase bd = FirebaseDatabase.getInstance();
        DatabaseReference ruta = bd.getReference("Alumno/");
        ruta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrAlumnos.clear();
                for (DataSnapshot registro : dataSnapshot.getChildren()) {
                    HashMap dAlumno = (HashMap) registro.getValue();
                    String matricula = dAlumno.get("matricula").toString();
                    String nombre = dAlumno.get("nombre").toString();
                    int semestre = Integer.parseInt(dAlumno.get("semestre").toString());
                    arrAlumnos.add(matricula + ", " + nombre + ", " + semestre);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, arrAlumnos);
                lstAlumnos.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
