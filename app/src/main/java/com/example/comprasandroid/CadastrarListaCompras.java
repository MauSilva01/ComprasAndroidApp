package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastrarListaCompras extends AppCompatActivity {

    EditText NomeListaCompras;

    Button botao;

    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_lista_compras);

        NomeListaCompras = (EditText) findViewById(R.id.NomeListaCompras);

        botao = (Button) findViewById(R.id.AdicionarListaComprasButton);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdicionarListaCompras();
            }
        });
    }

    public void AdicionarListaCompras() {
        if (!TextUtils.isEmpty(NomeListaCompras.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
                String sql = "INSERT INTO listaCompras (nomeListaCompras) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, NomeListaCompras.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}