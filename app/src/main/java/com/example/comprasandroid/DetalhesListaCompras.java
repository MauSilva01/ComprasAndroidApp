package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetalhesListaCompras extends AppCompatActivity {

    private EditText nomeEditText;

    private Button botaoSalvar;

    private Button botaoApagar;

    private SQLiteDatabase bancoDados;

    public int listaComprasId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_lista_compras);

        bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);

        //lista os Detalhes dos produtos
        listaComprasId = getIntent().getIntExtra("listaComprasId", -1);

        if (listaComprasId != -1) {
            // Consulte o banco de dados para obter os detalhes do produto com base no ID
            String[] columns = {"nomeListaCompras"};
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(listaComprasId)};
            Cursor cursor = bancoDados.query("listaCompras", columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // Recupere as informações do produto
                String nomeListaCompra = cursor.getString(cursor.getColumnIndex("nomeListaCompras"));

                // Inicialize as EditTexts com as informações do produto
                nomeEditText = findViewById(R.id.NomeListaComprasDetalhe);

                nomeEditText.setText(nomeListaCompra);

            }
            cursor.close();
        }

        if(listaComprasId != -1) {
            botaoApagar = (Button) findViewById(R.id.ButtonApagarListaCompras);

            botaoApagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApagarListaCompras(listaComprasId);
                }
            });
        }

        botaoSalvar = (Button) findViewById(R.id.buttonSalvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                SalvarAlteracao();
            }
        });
    }

    public void ApagarListaCompras(int listaComprasId) {
        if (listaComprasId != -1) {
            String sql = "DELETE FROM listaCompras WHERE id = " + listaComprasId;
            bancoDados.execSQL(sql);

            // O produto foi excluído, você pode finalizar a atividade ou navegar de volta à lista de produtos
            Toast.makeText(this, "Lista excluída com sucesso", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a atividade após a exclusão
        }
    }

    public void SalvarAlteracao() {

        String valueListaCompras = nomeEditText.getText().toString();


        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            String sql = "UPDATE listaCompras SET nomeListaCompras = ? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueListaCompras);
            stmt.bindLong(2, listaComprasId);

            stmt.executeUpdateDelete();
            bancoDados.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
