package org.example.duetrockers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.duetrockers.DAO.GameDAO;
import org.example.duetrockers.entities.Game;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {

        Game game = new Game("Chess", 2, 2);
        GameDAO dao = new GameDAO();

        if(dao.saveGame(game))
        {
            System.out.println("Game saved");
        }
        else
        {
            System.out.println("Game not saved");
        }

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}