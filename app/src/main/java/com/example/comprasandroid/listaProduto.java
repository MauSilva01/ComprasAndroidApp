package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class listaProduto extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewProdutos;

    public Button botao;
    //botao da tela de lista de Produtos
    public Button botaoP;
    //botao da tela de Listas De compras
    public Button botaoL;
    //botao da tela de lista de Mercados
    public Button botaoM;
    //botao da tela inicial(home)
    public Button botaoH;
    //botao de logout(Sair)
    public Button botaoSair;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        listViewProdutos = findViewById(R.id.listViewProdutos);

        //Botoes do Menu
        botao = (Button) findViewById(R.id.buttonCadastroProduto);

        botaoP = (Button) findViewById(R.id.ButtomTelaListP);

        botaoL = (Button) findViewById(R.id.ButtomTelaListC);

        botaoM = (Button) findViewById(R.id.ButtonTelaListM);

        botaoH = (Button) findViewById(R.id.ButtomTelaHome);

        botaoSair = (Button) findViewById(R.id.ButtonLogOut);

        botao.setOnClickListener(view -> telaAdicionarProduto());

        botaoM.setOnClickListener(view -> entrarListaMercado());

        botaoL.setOnClickListener(view -> entrarListaDeCompras());

        botaoH.setOnClickListener(view -> entrarTelaInicial());

        botaoSair.setOnClickListener(view -> VoltarTelaLogin());


        criarBancoDados();
        listarDados();


    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS produtos(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT " +
                    " , nome VARCHAR" +
                    " , marca VARCHAR" +
                    " , descricao VARCHAR" +
                    " , volume INTEGER" +
                    " , unidade VARCHAR)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private Cursor meuCursor;
    private ArrayAdapter<String> meuAdapter;

    public void listarDados() {
        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            meuCursor = bancoDados.rawQuery("SELECT id, nome, marca, descricao, volume, unidade FROM produtos", null);
            ArrayList<String> linhas = new ArrayList<String>();

            meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );

            listViewProdutos.setAdapter(meuAdapter);

            if (meuCursor != null && meuCursor.moveToFirst()) {
                do {
                    linhas.add(meuCursor.getString(1));
                } while (meuCursor.moveToNext());

                meuAdapter.notifyDataSetChanged();

                listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        meuCursor.moveToPosition(position);
                        int produtoId = meuCursor.getInt(0);

                        Intent intent = new Intent(listaProduto.this, DetalhesProdutos.class);
                        intent.putExtra("produto_id", produtoId);
                        startActivity(intent);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   /* public void inserirDadosTemp() {

        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            String sql = "INSERT INTO produtos (nome) VALUES (?) ";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1, "Requeijao");

            stmt.executeInsert();


            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
        public void deletarTabela () {
            // Abra o banco de dados ou crie-o se não existir
            SQLiteDatabase bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);

            // SQL para excluir a tabela
            String sql = "DROP TABLE IF EXISTS produtos";

            try {
                // Execute a instrução SQL para excluir a tabela
                bancoDados.execSQL(sql);
                // Confirme a transação
                bancoDados.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Encerre a transação e feche o banco de dados
                bancoDados.endTransaction();
                bancoDados.close();
            }
        } */

    public void telaAdicionarProduto(){

        Intent intent = new Intent(this, CadastrarProdutoActivity.class);
        startActivity(intent);
    }

    // Codigo do Menu
    public void entrarListaProduto(){

        Intent intent = new Intent(this, listaProduto.class);
        startActivity(intent);
    }

    public void entrarListaMercado(){

        Intent intent = new Intent(this, listaMercados.class);
        startActivity(intent);
    }

    public void entrarListaDeCompras(){

        Intent intent = new Intent(this, ListasDeCompra.class);
        startActivity(intent);
    }

    public void entrarTelaInicial(){

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void VoltarTelaLogin(){

        Intent intent = new Intent(this, FormLogin.class);
        startActivity(intent);
    }
}