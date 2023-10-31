package com.example.comprasandroid;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
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
        setContentView(R.layout.activity_home);

        botaoP = (Button) findViewById(R.id.ButtomTelaListP);

        botaoL = (Button) findViewById(R.id.ButtomTelaListC);

        botaoM = (Button) findViewById(R.id.ButtonTelaListM);

        botaoH = (Button) findViewById(R.id.ButtomTelaHome);

        botaoSair = (Button) findViewById(R.id.ButtonLogOut);

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