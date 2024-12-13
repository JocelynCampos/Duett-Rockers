package org.example.duetrockers;

import Views.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.duetrockers.DAO.GameDAO;
import org.example.duetrockers.DAO.MatchPlayerDAO;
import org.example.duetrockers.DAO.PlayerDAO;
import org.example.duetrockers.entities.Game;
import org.example.duetrockers.entities.Match;
import org.example.duetrockers.entities.Player;
import org.example.duetrockers.entities.MatchPlayer;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;

    private ViewManager manager;

    @Override
    public void start(Stage stage) throws IOException {
        manager = new ViewManager(stage, HEIGHT, WIDTH);

        manager.switchView(ViewManager.ViewType.START_MENU);

        stage.setTitle("Piper Games!");

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Shutting down...");
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
