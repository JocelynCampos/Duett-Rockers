package org.example.duetrockers;

import Views.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.duetrockers.DAO.GameDAO;
import org.example.duetrockers.entities.Game;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;

    private ViewManager manager;

    @Override
    public void start(Stage stage) throws IOException
    {
        manager = new ViewManager(stage, HEIGHT, WIDTH);

        manager.switchView(ViewManager.ViewType.START_MENU);

        stage.setTitle("Piper Games!");

        stage.show();
    }

    @Override
    public void stop() throws Exception
    {
        System.out.println("Shutting down...");
        super.stop();
    }

    public static void main(String[] args)
    {
        launch();
    }

    private void testDatabase()
    {
        Game game = new Game("Chess", 2);
        GameDAO dao = new GameDAO();

        if(dao.saveGame(game))
        {
            System.out.println("Game saved");
        }
        else
        {
            System.out.println("Game not saved");
        }

        Game testGame = dao.getGameByID(1);

        System.out.println("Game fetched: " +testGame.getGameName()+"\nId: "+testGame.getId());

        Game testGame2 = new Game("League of Legends", 10);

        if(dao.saveGame(testGame2))
        {
            System.out.println("Game saved");
        }
        else
        {
            System.out.println("Game not saved");
        }

        List<Game> games = dao.getAllGames();

        for(Game g : games)
        {
            System.out.println("Game name: "+g.getGameName()+"\nId: "+g.getId());
        }

        game.setPlayerCount(3);

        if(dao.updateGame(game))
        {
            System.out.println("Game updated");
        }

        System.out.println("Max players: "+ game.getPlayerCount());

        if(dao.deleteGame(game))
        {
            System.out.println("Game deleted");
        }

        games = dao.getAllGames();

        for(Game g : games)
        {
            System.out.println("Game name: "+g.getGameName()+"\nId: "+g.getId());
        }
    }
}