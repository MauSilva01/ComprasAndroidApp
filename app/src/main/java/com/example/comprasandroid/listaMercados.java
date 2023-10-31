package com.example.comprasandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class listaMercados extends AppCompatActivity {

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

    public Button botaoAddMercado;
    private SQLiteDatabase bancoDados;

    public ListView listViewMercados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mercados);

        botaoAddMercado = (Button) findViewById(R.id.buttonCadastroMercado);

        //Botoes do Menu
        botaoP = (Button) findViewById(R.id.ButtomTelaListP);

        botaoL = (Button) findViewById(R.id.ButtomTelaListC);

        botaoM = (Button) findViewById(R.id.ButtonTelaListM);

        botaoH = (Button) findViewById(R.id.ButtomTelaHome);

        botaoSair = (Button) findViewById(R.id.ButtonLogOut);

        botaoAddMercado.setOnClickListener(view -> telaAdicionarMercados());

        botaoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrarListaProduto();
            }
        });

        botaoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrarListaMercado();
            }
        });

        botaoL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrarListaDeCompras();
            }
        });

        botaoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrarTelaInicial();
            }
        });

        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoltarTelaLogin();
            }
        });

        listViewMercados = findViewById(R.id.listViewMercados);

        criarBancoDados();
        listarDados();

    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS mercados (" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT " +
                    " , nomeMercado VARCHAR )");
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
            meuCursor = bancoDados.rawQuery("SELECT id, nomeMercado FROM mercados", null);
            ArrayList<String> linhas = new ArrayList<String>();

            meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );

            listViewMercados.setAdapter(meuAdapter);

            if (meuCursor != null && meuCursor.moveToFirst()) {
                do {
                    linhas.add(meuCursor.getString(1));
                } while (meuCursor.moveToNext());

                meuAdapter.notifyDataSetChanged();

                listViewMercados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        meuCursor.moveToPosition(position);
                        int MercadosId = meuCursor.getInt(0);

                        Intent intent = new Intent(listaMercados.this, DetalhesMercados.class);
                        intent.putExtra("MercadosId", MercadosId);
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
            String sql = "INSERT INTO mercados (nomeMercado) VALUES (?) ";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1, "Nagumo");

            stmt.executeInsert();


            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    */

    public void telaAdicionarMercados(){

        Intent intent = new Intent(this, CadastrarMercados.class);
        startActivity(intent);
    }

    //Codigo do Menu
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