package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastrarProdutoActivity extends AppCompatActivity {

    EditText NomeProduto;
    EditText MarcaProduto;
    EditText DescricaoProduto;
    EditText VolumeProduto;
    EditText unidadeProduto;
    Button botao;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        NomeProduto = (EditText) findViewById(R.id.NomeProduto);
        MarcaProduto = (EditText) findViewById(R.id.MarcaProduto);
        DescricaoProduto = (EditText) findViewById(R.id.DescricaoProduto);
        VolumeProduto = (EditText) findViewById(R.id.VolumeProduto);
        unidadeProduto = (EditText) findViewById(R.id.unidadeProduto);
        botao = (Button) findViewById(R.id.AdicionarProdutoButton);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdicionarProduto();
            }
        });

    }

    public void AdicionarProduto() {
        if (!TextUtils.isEmpty(NomeProduto.getText().toString()) &&
                !TextUtils.isEmpty(MarcaProduto.getText().toString()) &&
                !TextUtils.isEmpty(DescricaoProduto.getText().toString()) &&
                !TextUtils.isEmpty(VolumeProduto.getText().toString()) &&
                !TextUtils.isEmpty(unidadeProduto.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
                String sql = "INSERT INTO produtos (nome, marca, descricao, volume, unidade) VALUES (?,?,?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, NomeProduto.getText().toString());
                stmt.bindString(2, MarcaProduto.getText().toString());
                stmt.bindString(3, DescricaoProduto.getText().toString());
                stmt.bindString(4, VolumeProduto.getText().toString());
                stmt.bindString(5, unidadeProduto.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}