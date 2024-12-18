package Views;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.duetrockers.entities.Game;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.GameDAO;

import java.util.List;



public class ViewGames extends View
{
    private ListView<String>gameListView;
    private TextField gameNameField, playerCountField;
    private Button addButton, updateButton, deleteButton, returnButton;

    private Game selectedGame;

    public ViewGames(int width, int height, ViewManager manager) {

        super(width, height, manager);
    }

    @Override
    protected void initializeView() {
        Label titleLabel = new Label("Game Management");
        titleLabel.setFont(new Font(24));

        gameListView = new ListView<>();
        refreshGameList();

        gameListView.setPrefHeight(height / 2.75);
        gameListView.setOnMouseClicked(mouseEvent -> {
            String selected = gameListView.getSelectionModel().getSelectedItem();

            if (selectedGame != null && selected != null && selectedGame.getGameName().equals(selected)) {
                gameListView.getSelectionModel().clearSelection();
                clearForm();
                selectedGame = null;
            } else {
                fillFormWithGameData(selected);
        }

    });

        VBox formBox = createForm();

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);

        addButton.setOnAction(e -> addGame());
        updateButton.setOnAction(e -> updateGame());
        deleteButton.setOnAction(e -> deleteGame());
        returnButton.setOnAction(e -> manager.switchToPreviousView());

        VBox layout = new VBox(10, titleLabel, gameListView, formBox, buttonBox);
        layout.setLayoutX(20);
        layout.setLayoutY(20);

        root.getChildren().add(layout);
    }

    private VBox createForm() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(5);

        Label gameNameLabel = new Label("Game Name:");
        gameNameField = new TextField();

        Label playerCountLabel = new Label("Player Count:");
        playerCountField = new TextField();

        gridPane.add(gameNameLabel, 0, 0);
        gridPane.add(gameNameField, 0, 1);

        gridPane.add(playerCountLabel, 1, 0);
        gridPane.add(playerCountField, 1, 1);

        return new VBox(20, gridPane);
    }

    private void clearForm() {
        gameNameField.clear();
        playerCountField.clear();
    }

    private void refreshGameList() {
        gameListView.getItems().clear();
        GameDAO gameDAO = new GameDAO();
        List<Game> games = gameDAO.getAllGames();

        for (Game game : games) {
            gameListView.getItems().add(game.getGameName());
        }
    }

    private void fillFormWithGameData(String selectedGameName) {
        if (selectedGameName == null || selectedGameName.isEmpty()) {
            clearForm();
            return;
        }

        GameDAO gameDAO = new GameDAO();
        selectedGame = gameDAO.getAllGames().stream()
                .filter(g -> g.getGameName().equals(selectedGameName))
                .findFirst()
                .orElse(null);

        if (selectedGame != null) {
            gameNameField.setText(selectedGame.getGameName());
            playerCountField.setText(String.valueOf(selectedGame.getPlayerCount()));
        }
    }

    private void addGame() {
        String gameName = gameNameField.getText().trim();
        String playerCountText = playerCountField.getText().trim();

        if (gameName.isEmpty() || playerCountText.isEmpty()){
            System.out.println("All fields are required!");
            return;
        }

        Game game = new Game();
        game.setGameName(gameName);
        game.setPlayerCount(Integer.parseInt(playerCountText));

        GameDAO gameDAO = new GameDAO();
        if (gameDAO.saveGame(game)) {
            System.out.println("Game added successfully.");
            refreshGameList();
            clearForm();
        } else {
            System.out.println("Failed to add game.");
        }
    }

    private void updateGame() {
        if (selectedGame == null) {
            System.out.println("Select a game to update!");
            return;
        }

        String gameName = gameNameField.getText().trim();
        String playerCountText = playerCountField.getText().trim();

        if (gameName.isEmpty() || playerCountText.isEmpty()){
            System.out.println("All fields are required!");
            return;
        }

        selectedGame.setGameName(gameName);
        selectedGame.setPlayerCount(Integer.parseInt(playerCountText));

        GameDAO gameDAO = new GameDAO();
        if (gameDAO.updateGame(selectedGame)) {
            System.out.println("Game updated successfully.");
            refreshGameList();
        } else {
            System.out.println("Failed to update game.");
        }
    }

    private void deleteGame() {
        String selectedGameName = gameListView.getSelectionModel().getSelectedItem();
        if (selectedGameName == null) {
            System.out.println("Select a game to delete!");
            return;
        }

        GameDAO gameDAO = new GameDAO();
        Game gameToDelete = gameDAO.getAllGames().stream()
                .filter(g -> g.getGameName().equals(selectedGameName))
                .findFirst()
                .orElse(null);

        if (gameToDelete == null) {
            System.out.println("Game not found!");
            return;
        }

        if (gameDAO.deleteGame(gameToDelete)) {
            System.out.println("Game deleted successfully.");
            clearForm();
            selectedGame = null;
            refreshGameList();
        } else {
            System.out.println("Failed to delete game.");
        }
    }
}





