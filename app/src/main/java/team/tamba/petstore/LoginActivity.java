package team.tamba.petstore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import team.tamba.petstore.entities.Produto;
import team.tamba.petstore.entities.TipoProduto;
import team.tamba.petstore.entities.Usuario;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String LOGIN = "Login";
    public static final String USER_ID = "UserID";

    private EditText etLogin;
    private EditText etSenha;
    private Button btLogin;
    private Button btCadastro;
    private CheckBox cbLembrarLogin;
    private SharedPreferences sharedPreferences;

    public static float round(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        etLogin = findViewById(R.id.etLogin);
        etSenha = findViewById(R.id.etSenha);
        cbLembrarLogin = findViewById(R.id.cbLembrarLogin);
        cbLembrarLogin.setChecked(false);

        String lastLogin = sharedPreferences.getString(LOGIN, "");
        if (lastLogin != null && !lastLogin.isEmpty()) {
            etLogin.setText(lastLogin);
            cbLembrarLogin.setChecked(true);
        }

        mockShop();

        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etLogin.getText().toString();
                String senha = etSenha.getText().toString();
                if (email.isEmpty()) {
                    etLogin.setError("Campo Obrigatório");
                    return;
                }
                if (senha.isEmpty()) {
                    etSenha.setError("Campo Obrigatório");
                    return;
                }
                Usuario usuario = isValidEmail(email);
                if (usuario != null) {
                    if (usuario.isPasswordValid(senha)) {
                        rememberLogin(usuario);
                        startActivity(MainActivity.class);
                    } else {
                        etSenha.setError("Senha inválida");
                    }
                }

            }
        });

        btCadastro = findViewById(R.id.btCadastro);
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CadastroActivity.class);
            }
        });

    }

    public void rememberLogin(Usuario usuario) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN, usuario.getEmail());
        editor.putLong(USER_ID, usuario.getId());
        editor.commit();
    }

    public void startActivity(Class classe) {
        Intent intent = new Intent(this, classe);
        startActivity(intent);
    }

    private Usuario isValidEmail(String email) {
        List<Usuario> usuarios = Usuario.find(Usuario.class, "email = ?", email);
        if (usuarios.size() < 1) {
            etLogin.setError("Usuário Inexistente");
            return null;
        }
        return usuarios.get(0);
    }

    private float genFloat() {
        Random r = new Random();
        float min = 2.99f;
        float max = 29.99f;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return round(min + r.nextFloat() * (max - min), 2);
    }

    private int genInt() {
        Random r = new Random();
        return r.nextInt(999);
    }

    private void mockShop() {
        List<TipoProduto> tipoProdutos = TipoProduto.listAll(TipoProduto.class);
        if (tipoProdutos.size() < 1) {
            TipoProduto tipoProduto = new TipoProduto("Alimentação", "Tudo para a alimentação do seu amiguinho!");
            tipoProduto.save();
            tipoProduto = new TipoProduto("Medicamentos", "Tudo para a Saúde do seu amiguinho!");
            tipoProduto.save();
            tipoProduto = new TipoProduto("Acessórios", "Tudo para o conforto do seu amiguinho!");
            tipoProduto.save();
        }

        List<Produto> produtos = Produto.listAll(Produto.class);
        if (produtos.size() < 1) {
            TipoProduto tipoProduto = TipoProduto.findById(TipoProduto.class, 1l);
            Produto produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD1", "alimento1");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD2", "alimento2");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD3", "alimento3");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD4", "alimento4");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD5", "alimento5");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD6", "alimento6");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD7", "alimento7");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD8", "alimento8");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD9", "alimento9");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD10", "alimento10");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD11", "alimento11");
            produto.save();

            tipoProduto = TipoProduto.findById(TipoProduto.class, 3l);
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD12", "acessorio1");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD13", "acessorio2");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD14", "acessorio3");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD15", "acessorio4");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD16", "acessorio5");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD17", "acessorio6");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD18", "acessorio7");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD19", "acessorio8");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD20", "acessorio9");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD21", "acessorio10");
            produto.save();

            tipoProduto = TipoProduto.findById(TipoProduto.class, 2l);

            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD22", "medicamento1");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD23", "medicamento2");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD24", "medicamento3");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD25", "medicamento4");
            produto.save();
            produto = new Produto(tipoProduto, genFloat(), genInt(), "PROD26", "medicamento5");
            produto.save();

        }
    }
}
