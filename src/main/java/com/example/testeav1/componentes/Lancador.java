package com.example.testeav1.componentes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Lancador extends Thread{
    private Rectangle retangulo;
    private ArrayList<Tiro> tiros = new ArrayList();

    public Lancador(){
        retangulo = new Rectangle();
        desenharRetangulo();
        start();
    }

    public void desenharRetangulo(){
        retangulo.setX(180);
        retangulo.setY(Dados.TAMANHO_MAX_TELA_Y - Dados.ALTURA_LANCADOR);
        retangulo.setWidth(40);
        retangulo.setHeight(Dados.ALTURA_LANCADOR);
        retangulo.setFill(Color.BLACK);
    }

    public Localizacao mirar (long timestamp, int identificacao) throws InterruptedException {
        long tempoAtual = System.currentTimeMillis() ;
        long tempoDecorrido = tempoAtual - timestamp;
        long distPercorrida = tempoDecorrido/Dados.TEMPO_ATUALIZACAO_PROJETEIS;
        long novoTamanho = Dados.TAMANHO_MAX_TELA_Y - distPercorrida - Dados.ALTURA_LANCADOR;
        double sup = novoTamanho*1.0f;
        double inf = 0.f;
        double mid;
        double distancia = 0.f;

        int i =0;
        while (true) {
            mid = (sup + inf)/2.f;
            distancia = Math.sqrt(200.f*200.f + mid*mid);
            i++;
            if(distancia - (novoTamanho - mid) < Dados.TAMANHO_ALVO - 1 && distancia - (novoTamanho - mid) > (Dados.TAMANHO_ALVO - 1)*-1){
                System.out.println("Atirou mirando no Alvo: "+ identificacao);
                var distancia2 = (distancia*distancia);
                return new Localizacao(200*200/distancia2, mid*mid/distancia2);
            } else if (inf >= sup) {
                System.out.println("Não achou");
                var distancia2 = (distancia*distancia);
                return new Localizacao(200*200/(distancia2), mid*mid/(distancia2));
            } else if(distancia > novoTamanho - mid) {
               sup = mid;
            } else if(distancia < novoTamanho - mid) {
               inf = mid;
            }
        }
    }

    public void atirar(long timestamp, int identificacaoAlvo, int origemX) throws InterruptedException {
        Localizacao pontoParaAtirar = mirar(timestamp, identificacaoAlvo);
        boolean sentidoDoTiro = origemX < 200;
        Tiro t = new Tiro(pontoParaAtirar.getX(), pontoParaAtirar.getY(), sentidoDoTiro, identificacaoAlvo);
        tiros.add(t);
    }

    public Rectangle getRetangulo() {
        return retangulo;
    }

    public ArrayList<Tiro> getTiros() {
        return tiros;
    }
}
