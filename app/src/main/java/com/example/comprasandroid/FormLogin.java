package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FormLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;

    public Button botaoLogin;

    private EditText editTextEmail;

    private EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
        botaoLogin = (Button) findViewById(R.id.bt_Login);

        editTextEmail = (EditText) findViewById(R.id.edit_email);

        editTextSenha = (EditText) findViewById(R.id.edit_senha);

        String email = "mauriciokawc@gmail.com";
        String senha = "123456";

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailDigitado = editTextEmail.getText().toString(); // Substitua editTextEmail pelo ID do EditText onde o usuário digita o email.
                String senhaDigitada = editTextSenha.getText().toString(); // Substitua editTextSenha pelo ID do EditText onde o usuário digita a senha.

                if (emailDigitado.equals(email) && senhaDigitada.equals(senha)) {
                    AbrirTelaInicial();
                } else {
                    Toast.makeText(getApplicationContext(), "Credenciais inválidas. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });



            IniciarComponentes();

        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this,FormCadastro.class);
                startActivity(intent);
            }
        });
    }

        private void IniciarComponentes(){
            text_tela_cadastro = findViewById(R.id.text_tela_cadastro);

        }

        public void AbrirTelaInicial(){


            Intent intent = new Intent(this, Home.class);
            startActivity(intent);

        }
    }
