package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastrarMercados extends AppCompatActivity {

    EditText NomeMercados;

    Button botao;

    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_mercados);

        NomeMercados = (EditText) findViewById(R.id.NomeMercado);

        botao = (Button) findViewById(R.id.AdicionarMercadoButton);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdicionarMercados();
            }
        });
    }

    public void AdicionarMercados() {
        if (!TextUtils.isEmpty(NomeMercados.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
                String sql = "INSERT INTO mercados (nomeMercado) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, NomeMercados.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
