package br.edu.ifsul.sapucaia.topicos.exercicio1.ui.Tela1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.ifsul.sapucaia.topicos.exercicio1.R;
import br.edu.ifsul.sapucaia.topicos.exercicio1.models.Usuario;
import br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Extras;
import br.edu.ifsul.sapucaia.topicos.exercicio1.ui.Tela2.Tela2Activity;
import br.edu.ifsul.sapucaia.topicos.exercicio1.utils.DateUtils;
import br.edu.ifsul.sapucaia.topicos.exercicio1.utils.MaskUtils;
import lombok.SneakyThrows;

import static br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Mensagens.CAMPO_OBRIGATORIO;
import static br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Mensagens.DATA_DE_NASCIMENTO_INVALIDA;
import static br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Mensagens.EMAIL_INVALIDO;
import static br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Mensagens.TAMANHO_DATA_DE_NASCIMENTO;
import static br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Mensagens.TAMANHO_NOME;
import static br.edu.ifsul.sapucaia.topicos.exercicio1.models.constantes.Mensagens.TAMANHO_TELEFONE;

public class Tela1Activity extends AppCompatActivity implements Validator.ValidationListener {
    private static final String DATA_DE_NASCIMENTO_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    private final ArrayList<Usuario> usuariosCadastrados = new ArrayList<>();

    private Validator validator;

    @NotEmpty(message = CAMPO_OBRIGATORIO)
    @Length(min = 3, max = 20, message = TAMANHO_NOME)
    private EditText etNome;

    @NotEmpty(message = CAMPO_OBRIGATORIO)
    @Email(message = EMAIL_INVALIDO)
    private EditText etEmail;

    @NotEmpty(message = CAMPO_OBRIGATORIO)
    @Length(min = 13, max = 13, message = TAMANHO_TELEFONE)
    private EditText etTelefone;

    @NotEmpty(message = CAMPO_OBRIGATORIO)
    @Pattern(regex = DATA_DE_NASCIMENTO_REGEX, message = DATA_DE_NASCIMENTO_INVALIDA)
    @Length(min = 10, max = 10, message = TAMANHO_DATA_DE_NASCIMENTO)
    private EditText etDataDeNascimento;

    @Checked(message = CAMPO_OBRIGATORIO)
    private RadioGroup rgGenero;

    private CheckBox cbFilme;

    private CheckBox cbMusica;

    private TextView tvGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela1);

        inicializarComponentes();

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void inicializarComponentes() {
        this.etNome = (EditText) findViewById(R.id.et_nome);
        this.etEmail = (EditText) findViewById(R.id.et_email);
        this.etTelefone = (EditText) findViewById(R.id.et_telefone);
        this.etDataDeNascimento = (EditText) findViewById(R.id.et_data_de_nascimento);
        this.rgGenero = (RadioGroup) findViewById(R.id.rg_genero);
        this.cbFilme = (CheckBox) findViewById(R.id.cb_filme);
        this.cbMusica = (CheckBox) findViewById(R.id.cb_musica);
        this.tvGenero = (TextView) findViewById(R.id.tv_genero);

        this.etTelefone.addTextChangedListener(MaskUtils.mascara("(##)#########"));
        this.etDataDeNascimento.addTextChangedListener(MaskUtils.mascara("##/##/####"));
    }

    @SneakyThrows
    @Override
    public void onValidationSucceeded() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.etNome.getText().toString());
        usuario.setEmail(this.etEmail.getText().toString());
        usuario.setTelefone(this.etTelefone.getText().toString());
        usuario.setDataDeNascimento(DateUtils.interpretar(
                this.etDataDeNascimento.getText().toString()));
        usuario.setGenero(getGenero(this.rgGenero.getCheckedRadioButtonId()));
        usuario.setInteresses(getInteresses(
                Arrays.asList(this.cbMusica, this.cbFilme)));

        cadastrarUsuario(usuario);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String erro = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(erro);
            } else if (view instanceof RadioGroup) {
                ((TextView) findViewById(R.id.tv_genero)).setError(erro);
            } else {
                Toast.makeText(this, erro, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private String getGenero(int idRadioButton) {
        return ((RadioButton) findViewById(idRadioButton)).getText()
                .toString();
    }

    private ArrayList<String> getInteresses(List<CheckBox> checkBoxes) {
        ArrayList<String> interesses = new ArrayList<>();

        for (CheckBox checkbox : checkBoxes) {
            if (checkbox.isChecked()) {
                interesses.add(checkbox.getText()
                        .toString());
            }
        }

        return interesses;
    }

    private void cadastrarUsuario(Usuario usuario) throws ParseException {
        this.usuariosCadastrados.add(usuario);

        limparInputs();

        String textoDoToast = montarTextoDoToast(usuario);

        Toast.makeText(this, textoDoToast, Toast.LENGTH_LONG)
                .show();
    }

    private void limparInputs() {
        etNome.getText()
                .clear();

        etEmail.getText()
                .clear();

        etTelefone.getText()
                .clear();

        etDataDeNascimento.getText()
                .clear();

        rgGenero.clearCheck();

        cbMusica.setChecked(false);

        cbFilme.setChecked(false);

        tvGenero.setError(null);
    }

    private String montarTextoDoToast(Usuario usuario) {
        String dataDeNascimentoFormatada = DateUtils.formatar(usuario.getDataDeNascimento());

        StringBuilder toast = new StringBuilder("Usuário cadastrado com sucesso!\n" +
                "\nNome: " + usuario.getNome() +
                "\nE-mail: " + usuario.getEmail() +
                "\nTelefone: " + usuario.getTelefone() +
                "\nData de nascimento: " + dataDeNascimentoFormatada +
                "\nGênero: " + usuario.getGenero());

        int quantidadeDeInteresses = usuario.getInteresses()
                .size();

        if (quantidadeDeInteresses > 0) {
            toast.append("\nInteresses: ");

            for (int i = 0; i < quantidadeDeInteresses; i++) {
                if (quantidadeDeInteresses > 1 && i == quantidadeDeInteresses - 1) {
                    toast.append(" e ");
                }

                toast.append(usuario.getInteresses()
                        .get(i)
                        .toLowerCase());
            }
        }

        return toast.toString();
    }

    public void onClickCadastrar(View view) {
        validator.validate();
    }

    public void onClickEnviar(View view) {
        if (this.usuariosCadastrados.size() > 0) {
            String usuariosCadastradosJson = new Gson()
                    .toJson(this.usuariosCadastrados);

            Intent intent = new Intent(getBaseContext(), Tela2Activity.class);
            intent.putExtra(Extras.USUARIOS_CADASTRADOS, usuariosCadastradosJson);

            startActivity(intent);
        } else {
            String erro = "É necessário cadastrar ao menos 1 (um) usuário";

            Toast.makeText(this, erro, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
