package br.edu.ifsul.sapucaia.topicos.exercicio1.ui.Tela3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;

import br.edu.ifsul.sapucaia.topicos.exercicio1.R;
import br.edu.ifsul.sapucaia.topicos.exercicio1.models.Usuario;
import br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Extras;
import br.edu.ifsul.sapucaia.topicos.exercicio1.utils.DateUtils;

public class Tela3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela3);

        String json = getIntent()
                .getStringExtra(Extras.USUARIO_CLICADO);

        Usuario usuario = new Gson()
                .fromJson(json, Usuario.class);

        String toast = usuario.getNome() + "\n" +
                usuario.getEmail() + "\n" +
                usuario.getTelefone() + "\n" +
                DateUtils.formatar(usuario.getDataDeNascimento());

        Toast.makeText(this, toast, Toast.LENGTH_LONG)
                .show();
    }
}
