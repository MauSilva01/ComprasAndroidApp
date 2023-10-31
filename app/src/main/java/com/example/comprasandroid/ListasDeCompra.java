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

public class ListasDeCompra extends AppCompatActivity {

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

    public ListView listViewListaCompras;

    private SQLiteDatabase bancoDados;

    public Button botaoAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_de_compra);

        botaoAdd = (Button) findViewById(R.id.buttonCadastroListaCompras) ;

        botaoP = (Button) findViewById(R.id.ButtomTelaListP);

        botaoL = (Button) findViewById(R.id.ButtomTelaListC);

        botaoM = (Button) findViewById(R.id.ButtonTelaListM);

        botaoH = (Button) findViewById(R.id.ButtomTelaHome);

        botaoSair = (Button) findViewById(R.id.ButtonLogOut);

        botaoAdd.setOnClickListener(view -> telaAdicionarListaCompras());

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

        listViewListaCompras = findViewById(R.id.listViewListaCompras);

        criarBancoDados();
        listarDados();


    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("Compras", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS listaCompras (" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT " +
                    " , nomeListaCompras VARCHAR )");
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
            meuCursor = bancoDados.rawQuery("SELECT id, nomeListaCompras FROM listaCompras", null);
            ArrayList<String> linhas = new ArrayList<String>();

            meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );

            listViewListaCompras.setAdapter(meuAdapter);

            if (meuCursor != null && meuCursor.moveToFirst()) {
                do {
                    linhas.add(meuCursor.getString(1));
                } while (meuCursor.moveToNext());

                meuAdapter.notifyDataSetChanged();

                listViewListaCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        meuCursor.moveToPosition(position);
                        int listaComprasId = meuCursor.getInt(0);

                        Intent intent = new Intent(ListasDeCompra.this, DetalhesListaCompras.class);
                        intent.putExtra("listaComprasId", listaComprasId);
                        startActivity(intent);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void telaAdicionarListaCompras(){

        Intent intent = new Intent(this, CadastrarListaCompras.class);
        startActivity(intent);
    }

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