package com.example.testeav1.componentes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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
        retangulo.setY(400);
        retangulo.setWidth(40);
        retangulo.setHeight(Dados.ALTURA_LANCADOR);
        retangulo.setFill(Color.BLACK);
    }

    public Localizacao mirar (long timestamp, int identificacao) {
        //TODO: deixar dinamico com o tamanho da tela e usar timestamp
        double mid = (Dados.TAMANHO_MAX_TELA_Y - Dados.ALTURA_LANCADOR)/2.f;
        double oldMid = mid;
        double distancia = 0.f;

        int i =0;
        while (true) {
            distancia = Math.sqrt(200.f*200.f + mid*mid);
            i++;
            if(distancia - (400 - mid) < Dados.TAMANHO_ALVO - 1 && distancia - (400 - mid) > (Dados.TAMANHO_ALVO - 1)*-1){
                System.out.println("Atirou mirando no Alvo: "+ identificacao);
                var distancia2 = distancia*distancia;
                return new Localizacao(200*200/distancia2, mid*mid/distancia2);
            } else if(distancia > 400 - mid) {
                oldMid = mid;
                mid = mid/2.f;
            } else if(distancia < 400 - mid) {
                var midAux = mid;
                mid = (oldMid + mid) /2;
                oldMid = midAux;
            }
        }
    }

    public void atirar(long timestamp, int identificacaoAlvo, int origemX){
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
