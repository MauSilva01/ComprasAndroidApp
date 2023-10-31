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

public class DetalhesMercados extends AppCompatActivity {

    private EditText nomeEditText;

    private Button botaoSalvar;

    private Button botaoApagar;

    private SQLiteDatabase bancoDados;

    public int mercadosId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_mercados);

        // Inicializa o banco de dados
        bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);

        //lista os Detalhes dos produtos
        mercadosId = getIntent().getIntExtra("MercadosId", -1);

        if (mercadosId != -1) {
            // Consulte o banco de dados para obter os detalhes do produto com base no ID
            String[] columns = {"nomeMercado"};
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(mercadosId)};
            Cursor cursor = bancoDados.query("mercados", columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // Recupere as informações do produto
                String nomeMercados = cursor.getString(cursor.getColumnIndex("nomeMercado"));

                // Inicialize as EditTexts com as informações do produto
                nomeEditText = findViewById(R.id.NomeMercadoDetalhe);

                nomeEditText.setText(nomeMercados);

            }
            cursor.close();
        }

        if(mercadosId != -1) {
            botaoApagar = (Button) findViewById(R.id.ButtonApagarMercado);

            botaoApagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApagarMercado(mercadosId);
                }
            });
        }

        botaoSalvar = (Button) findViewById(R.id.buttonSalvarMercado);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                SalvarAlteracao();
            }
        });
    }

    public void ApagarMercado(int mercadosId) {
        if (mercadosId != -1) {
            String sql = "DELETE FROM mercados WHERE id = " + mercadosId;
            bancoDados.execSQL(sql);

            // O produto foi excluído, você pode finalizar a atividade ou navegar de volta à lista de produtos
            Toast.makeText(this, "Mercado excluído com sucesso", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a atividade após a exclusão
        }
    }

    public void SalvarAlteracao() {

        String valueNomeMercado = nomeEditText.getText().toString();


        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            String sql = "UPDATE mercados SET nomeMercado = ? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueNomeMercado);
            stmt.bindLong(2, mercadosId);

            stmt.executeUpdateDelete();
            bancoDados.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
