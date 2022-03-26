package com.example.testeav1;

import com.example.testeav1.componentes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    //Lista de alvos
    ArrayList<Alvo> alvos = new ArrayList();

    Lancador lancador;

    @Override
    public void start(Stage stage) throws IOException {
        //Criar a tela
        var root = new Pane();
        var scene = new Scene(root, Dados.TAMANHO_MAX_TELA_X,Dados.TAMANHO_MAX_TELA_Y, Color.WHITESMOKE);
        stage.setTitle("AV1");
        stage.setScene(scene);
        stage.show();

        //Função que lança alvos a cada determinado tempo
        Timeline novosAlvosTimer = new Timeline(
                new KeyFrame(Duration.millis(5000),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            var alvo = new Alvo(alvos.size());
                            alvos.add(alvo);
                            //A cada alvo gerado, gera-se um tiro correspondente, por enquanto
                            lancador.atirar(alvo.getTimestamp(), alvo.getIdentificacao(), alvo.getOrigemx());
                        }
                    }));
        //Funcionando por tempo indefinido
        novosAlvosTimer.setCycleCount(Timeline.INDEFINITE);
        novosAlvosTimer.play();

        //Atualizar a tela para tentar diminuir travamentos de renderização
        Timeline refresh = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                root.getChildren().clear();
                                root.getChildren().add(lancador.getRetangulo());
                                for (Alvo alvo : alvos) {
                                    if(alvo.isAlive()) {
                                        root.getChildren().add(alvo.getCirculoAlvo());
                                    }
                                }
                                for (Tiro tiro : lancador.getTiros()) {
                                    if(tiro.isAlive()) {
                                        root.getChildren().add(tiro.getCirculoTiro());
                                    }
                                }
                            }
                        }));
        refresh.setCycleCount(Timeline.INDEFINITE);
        refresh.play();

        //Função que verifica colisão entre tiros e alvos
        Timeline colisaoTimer = new Timeline(
                new KeyFrame(Duration.millis(30),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                for (Tiro tiro : lancador.getTiros()) {
                                    if (tiro.isAlive()) {
                                        var alvo = alvos.get(tiro.getIdentificacaoAlvo());
                                        if(Utils.distanciaEuclidiana(tiro.getLocalizacaoX(), tiro.getLocalizacaoY(), alvo.getOrigemx(), alvo.getLocalizacaoAtualizada())
                                                <= Dados.TAMANHO_TIRO + Dados.TAMANHO_ALVO - 1){
                                            alvo.setAtingido();
                                            tiro.setContatoAlvo();
                                        }
                                    }
                                }
                            }
                        }));
        colisaoTimer.setCycleCount(Timeline.INDEFINITE);
        colisaoTimer.play();

//        Timeline limparListaAlvos = new Timeline(
//                new KeyFrame(Duration.millis(5000),
//                        new EventHandler<ActionEvent>() {
//                            @Override
//                            public void handle(ActionEvent event) {
//                                alvos.removeIf(alvo -> !alvo.isAlive());
//                                System.out.println("Tamanho da lista: " + alvos.size());
//                            }
//                        }));
//        limparListaAlvos.setCycleCount(Timeline.INDEFINITE);
//        limparListaAlvos.play();

        this.lancador = new Lancador();
        root.getChildren().add(this.lancador.getRetangulo());

    }

    public static void main(String[] args) {
        launch();
    }

}