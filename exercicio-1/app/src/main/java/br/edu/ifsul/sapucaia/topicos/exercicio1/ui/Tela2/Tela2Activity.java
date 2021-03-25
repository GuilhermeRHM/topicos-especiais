package br.edu.ifsul.sapucaia.topicos.exercicio1.ui.Tela2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

import br.edu.ifsul.sapucaia.topicos.exercicio1.R;
import br.edu.ifsul.sapucaia.topicos.exercicio1.models.Usuario;
import br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Extras;
import br.edu.ifsul.sapucaia.topicos.exercicio1.ui.Tela3.Tela3Activity;

public class Tela2Activity extends ListActivity {
    private ArrayList<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        usuarios = getUsuarios();

        ArrayAdapter adaptador = new ArrayAdapter<>(this,
                R.layout.activity_tela2_list_item,
                usuarios.stream()
                        .map(usuario -> new StringBuilder(usuario.getNome() + ": " + usuario.getGenero()))
                        .collect(Collectors.toList()));

        ListView lvUsuarios = (ListView) findViewById(android.R.id.list);
        lvUsuarios.setAdapter(adaptador);
    }

    private ArrayList<Usuario> getUsuarios() {
        String json = getIntent()
                .getStringExtra(Extras.USUARIOS_CADASTRADOS);

        Type tipo = new TypeToken<ArrayList<Usuario>>() {
        }.getType();

        return new Gson()
                .fromJson(json, tipo);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Usuario usuario = usuarios.get(position);
        String usuarioJson = new Gson()
                .toJson(usuario);

        Intent intent = new Intent(getBaseContext(), Tela3Activity.class);
        intent.putExtra(Extras.USUARIO_CLICADO, usuarioJson);

        startActivity(intent);
    }
}
