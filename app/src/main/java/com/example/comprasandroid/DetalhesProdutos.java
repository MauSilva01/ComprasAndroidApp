package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetalhesProdutos extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText marcaEditText;
    private EditText descricaoEditText;
    private EditText volumeEditText;
    private EditText unidadeEditText;

    private Button botaoApagar;
    private Button botaoSalvar;

    private SQLiteDatabase bancoDados;
    private int produtoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produtos);

        // Inicializa o banco de dados
        bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);

        //lista os Detalhes dos produtos
        produtoId = getIntent().getIntExtra("produto_id", -1);

        if (produtoId != -1) {
            // Consulte o banco de dados para obter os detalhes do produto com base no ID
            String[] columns = {"nome", "marca", "descricao", "volume", "unidade"};
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(produtoId)};
            Cursor cursor = bancoDados.query("produtos", columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // Recupere as informações do produto
                String nomeProduto = cursor.getString(cursor.getColumnIndex("nome"));
                String marcaProduto = cursor.getString(cursor.getColumnIndex("marca"));
                String descricaoProduto = cursor.getString(cursor.getColumnIndex("descricao"));
                String volumeProduto = cursor.getString(cursor.getColumnIndex("volume"));
                String unidadeProduto = cursor.getString(cursor.getColumnIndex("unidade"));

                // Inicialize as EditTexts com as informações do produto
                nomeEditText = findViewById(R.id.NomeProdutoDetalhe);
                marcaEditText = findViewById(R.id.MarcaProdutoDetalhe);
                descricaoEditText = findViewById(R.id.DescricaoProdutoDetalhe);
                volumeEditText = findViewById(R.id.VolumeProdutoDetalhe);
                unidadeEditText = findViewById(R.id.unidadeProdutoDetalhe);

                nomeEditText.setText(nomeProduto);
                marcaEditText.setText(marcaProduto);
                descricaoEditText.setText(descricaoProduto);
                volumeEditText.setText(volumeProduto);
                unidadeEditText.setText(unidadeProduto);
            }

            cursor.close();
        }

        if (produtoId != -1) {
            botaoApagar = (Button) findViewById(R.id.ButtonApagarProduto);

            botaoApagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApagarProduto(produtoId);
                }
            });
        }

        botaoSalvar = (Button) findViewById(R.id.buttonSalvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalvarAlteracao();
            }
        });
    }

    public void ApagarProduto(int produtoId) {
        if (produtoId != -1) {
            String sql = "DELETE FROM produtos WHERE id = " + produtoId;
            bancoDados.execSQL(sql);

            // O produto foi excluído, você pode finalizar a atividade ou navegar de volta à lista de produtos
            Toast.makeText(this, "Produto excluído com sucesso", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a atividade após a exclusão
        }
    }

    public void SalvarAlteracao() {
        String valueNome = nomeEditText.getText().toString();
        String valueMarca = marcaEditText.getText().toString();
        String valueDescricao = descricaoEditText.getText().toString();
        String valueVolume = volumeEditText.getText().toString();
        String valueUnidade = unidadeEditText.getText().toString();

        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            String sql = "UPDATE produtos SET nome = ?, marca = ?, descricao = ?, volume = ?, unidade = ? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueNome);
            stmt.bindString(2, valueMarca);
            stmt.bindString(3, valueDescricao);
            stmt.bindString(4, valueVolume);
            stmt.bindString(5, valueUnidade);
            stmt.bindLong(6, produtoId);

            stmt.executeUpdateDelete();
            bancoDados.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}



