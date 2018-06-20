package team.tamba.petstore;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import team.tamba.petstore.entities.Endereco;
import team.tamba.petstore.entities.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etSenha2;
    private EditText etCPF;
    private EditText etTelefone;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCEP;
    private Button btCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        etSenha2 = findViewById(R.id.etSenha2);
        etCPF = findViewById(R.id.etCPF);
        etTelefone = findViewById(R.id.etTelefone);
        etLogradouro = findViewById(R.id.etLogradouro);
        etNumero = findViewById(R.id.etNumero);
        etBairro = findViewById(R.id.etBairro);
        etCEP = findViewById(R.id.etCEP);

        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isAnyFieldEmpty()) {
                    return;
                }
                if (!isPasswordOK()) {
                    return;
                }
                if (!isValidEmail()) {
                    return;
                }
                salvarUsuario(getApplicationContext());

            }
        });


    }

    private boolean isValidEmail() {
        String email = etEmail.getText().toString();
        List<Usuario> usuarios = Usuario.find(Usuario.class, "email = ?", email);
        if (usuarios.size() > 0) {
            etEmail.setError("E-mail já cadastrado!");
            return false;
        }
        return true;
    }

    private boolean isPasswordOK() {
        etSenha.setError(null);
        if (!etSenha.getText().toString().equalsIgnoreCase(etSenha2.getText().toString())) {
            etSenha.setError("Senhas Diferentes");
            return false;
        }
        return true;
    }

    private void salvarUsuario(Context context) {
        Usuario usuario = new Usuario();
        usuario.setNome(etNome.getText().toString());
        usuario.setEmail(etEmail.getText().toString());
        usuario.setSenha(etSenha.getText().toString());
        usuario.setCpf(etCPF.getText().toString());
        usuario.setTelefone(etTelefone.getText().toString());

        Endereco endereco = new Endereco();
        endereco.setLogradouro(etLogradouro.getText().toString());
        endereco.setNumero(etNumero.getText().toString());
        endereco.setBairro(etBairro.getText().toString());
        endereco.setCep(etCEP.getText().toString());
        endereco.save();

        usuario.setEndereco(endereco);
        usuario.save();

        btCadastrar.setText("Concluir");
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private boolean isAnyFieldEmpty() {
        if (etNome.getText().toString().isEmpty()) {
            etNome.setError("Campo Obrigatório!");
            return true;
        }
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Campo Obrigatório");
            return true;
        }
        if (etSenha.getText().toString().isEmpty()) {
            etSenha.setError("Campo Obrigatório");
            return true;
        }
        if (etSenha2.getText().toString().isEmpty()) {
            etSenha2.setError("Campo Obrigatório");
            return true;
        }
        if (etCPF.getText().toString().isEmpty()) {
            etCPF.setError("Campo Obrigatório");
            return true;
        }
        if (etTelefone.getText().toString().isEmpty()) {
            etTelefone.setError("Campo Obrigatório");
            return true;
        }
        if (etLogradouro.getText().toString().isEmpty()) {
            etLogradouro.setError("Campo Obrigatório");
            return true;
        }
        if (etNumero.getText().toString().isEmpty()) {
            etNumero.setError("Campo Obrigatório");
            return true;
        }
        if (etBairro.getText().toString().isEmpty()) {
            etBairro.setError("Campo Obrigatório");
            return true;
        }
        if (etCEP.getText().toString().isEmpty()) {
            etCEP.setError("Campo Obrigatório");
            return true;
        }

        return false;

    }


}
