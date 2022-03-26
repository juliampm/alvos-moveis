package com.example.testeav1.componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.security.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Alvo extends Thread {
    private int identificacao;
    private int origemx;
    private int origemy = 0;
    private int destinox;
    private int destinoy = 390;
    private int localizacaoAtualizada;
    private long timestamp;
    private int freqAtualizacaoPosicao = 30;
    private Boolean chegouDestino = false;
    private Boolean atingido = false;
    private Circle circuloAlvo;
    private Random rand = new Random();

    public Alvo(int identificacao){
        this.timestamp = System.currentTimeMillis();
        this.identificacao = identificacao;
        int x = rand.nextInt(2);
        if(x == 1){
            this.origemx = 0;
        }else{
            this.origemx = 400;
        }
        circuloAlvo = new Circle(origemx, origemy,Dados.TAMANHO_ALVO,Color.RED);
        desenharAlvo(localizacaoAtualizada);
        start();
    }

    public void desenharAlvo(int y){
        this.circuloAlvo.setTranslateY(y);
    }

    @Override
    public void run() {
        super.run();
        while(destinoy >= localizacaoAtualizada && !atingido) {
            try {
                this.localizacaoAtualizada += 1;
                this.desenharAlvo(this.localizacaoAtualizada);
                sleep(freqAtualizacaoPosicao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (atingido) {
            System.out.println("Alvo " +identificacao + " foi atingido!");
        } else {
            System.out.println("Alvo " +identificacao + " não foi atingido!");
        }
    }

    public Circle getCirculoAlvo() {
        return circuloAlvo;
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public int getOrigemx() {
        return origemx;
    }

    public int getDestinox() {
        return destinox;
    }

    public int getOrigemy() {
        return origemy;
    }

    public int getLocalizacaoAtualizada() {
        return localizacaoAtualizada;
    }

    public int getDestinoy() {
        return destinoy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getFreqAtualizacaoPosicao() {
        return freqAtualizacaoPosicao;
    }

    public void setFreqAtualizacaoPosicao(int freqAtualizacaoPosicao) {
        this.freqAtualizacaoPosicao = freqAtualizacaoPosicao;
    }

    public void setAtingido() {
        this.atingido = true;
    }

}