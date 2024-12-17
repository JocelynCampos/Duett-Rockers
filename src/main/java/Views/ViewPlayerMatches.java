package Views;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.MatchPlayerDAO;
import org.example.duetrockers.entities.MatchPlayer;
import org.example.duetrockers.entities.Player;
import org.example.duetrockers.entities.Match;

import java.util.List;

public class ViewPlayerMatches extends View {

    private ListView<String> matchPlayerListView;
    private TextField matchIDField, playerNicknameField, scoreField, matchStatusField;
    private Button addButton, updateButton, deleteButton, returnButton;

    private MatchPlayer selectedMatchPlayer;

    public ViewPlayerMatches(int width, int height, ViewManager manager) {

        super(width, height, manager);
    }

    @Override
    protected void initializeView() {
        Label titleLabel = new Label("Player Matches Management");
        titleLabel.setFont(new Font(24));

        matchPlayerListView = new ListView<>();
        refreshMatchPlayerList();

        matchPlayerListView.setPrefHeight(height / 2.75);
        matchPlayerListView.setOnMouseClicked(mouseEvent -> {
            String selected = matchPlayerListView.getSelectionModel().getSelectedItem();

            if (selectedMatchPlayer != null && selected != null &&
                    selectedMatchPlayer.getMatch().getId() == Integer.parseInt(selected.split("-")[0])) {
                matchPlayerListView.getSelectionModel().clearSelection();
                clearForm();
                selectedMatchPlayer = null;
            } else {
                fillFormWithMatchPlayerData(selected);
            }
        });

        VBox formBox = createForm();

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);

        addButton.setOnAction(e -> addMatchPlayer());
        updateButton.setOnAction(e -> updateMatchPlayer());
        deleteButton.setOnAction(e -> deleteMatchPlayer());
        returnButton.setOnAction(e -> manager.switchToPreviousView());

        VBox layout = new VBox(10, titleLabel, matchPlayerListView, formBox, buttonBox);
        layout.setLayoutX(20);
        layout.setLayoutY(20);

        root.getChildren().add(layout);
    }

    private VBox createForm() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(5);

        Label matchIDLabel = new Label("Match ID:");
        matchIDField = new TextField();

        Label playerNicknameLabel = new Label("Player Nickname:");
        playerNicknameField = new TextField();

        Label scoreLabel = new Label("Score:");
        scoreField = new TextField();

        Label matchStatusLabel = new Label("Match Status:");
        matchStatusField = new TextField();

        gridPane.add(matchIDLabel, 0, 0);
        gridPane.add(matchIDField, 0, 1);

        gridPane.add(playerNicknameLabel, 1, 0);
        gridPane.add(playerNicknameField, 1, 1);

        gridPane.add(scoreLabel, 0, 2);
        gridPane.add(scoreField, 0, 3);

        gridPane.add(matchStatusLabel, 1, 2);
        gridPane.add(matchStatusField, 1, 3);

        return new VBox(20, gridPane);
    }

    private void clearForm() {
        matchIDField.clear();
        playerNicknameField.clear();
        scoreField.clear();
        matchStatusField.clear();
    }

    private void refreshMatchPlayerList() {
        matchPlayerListView.getItems().clear();
        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        List<MatchPlayer> matchPlayers = matchPlayerDAO.getAllMatchPlayers();

        for (MatchPlayer mp : matchPlayers) {
            matchPlayerListView.getItems().add(
                    mp.getMatch().getId() + " - Player: " + mp.getPlayer().getNickname() +
                            ", Score: " + mp.getScore() + ", Status: " + mp.getMatch().getStatus());
        }
    }

    private void fillFormWithMatchPlayerData(String selectedMatchPlayerInfo) {
        if (selectedMatchPlayerInfo == null || selectedMatchPlayerInfo.isEmpty()) {
            clearForm();
            return;
        }

        int selectedMatchID = Integer.parseInt(selectedMatchPlayerInfo.split("-")[0].trim());

        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        selectedMatchPlayer = matchPlayerDAO.getAllMatchPlayers().stream()
                .filter(mp -> mp.getMatch().getId() == selectedMatchID)
                .findFirst()
                .orElse(null);

        if (selectedMatchPlayer != null) {
            matchIDField.setText(String.valueOf(selectedMatchPlayer.getMatch().getId()));
            playerNicknameField.setText(selectedMatchPlayer.getPlayer().getNickname());
            scoreField.setText(String.valueOf(selectedMatchPlayer.getScore()));
            matchStatusField.setText(selectedMatchPlayer.getMatch().getStatus());
        }
    }

    private void addMatchPlayer() {
        String matchIDText = matchIDField.getText().trim();
        String playerNicknameText = playerNicknameField.getText().trim();
        String scoreText = scoreField.getText().trim();
        String matchStatusText = matchStatusField.getText().trim();

        if (matchIDText.isEmpty() || playerNicknameText.isEmpty() || scoreText.isEmpty() || matchStatusText.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        Match match = new Match();
        match.setId(Integer.parseInt(matchIDText));
        match.setStatus(matchStatusText);

        Player player = new Player();
        player.setNickname(playerNicknameText);

        MatchPlayer matchPlayer = new MatchPlayer();
        matchPlayer.setMatch(match);
        matchPlayer.setPlayer(player);
        matchPlayer.setScore(Integer.parseInt(scoreText));

        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        if (matchPlayerDAO.saveMatchPlayer(matchPlayer)) {
            System.out.println("MatchPlayer added successfully.");
            refreshMatchPlayerList();
            clearForm();
        } else {
            System.out.println("Failed to add MatchPlayer.");
        }
    }

    private void updateMatchPlayer() {
        if (selectedMatchPlayer == null) {
            System.out.println("Select a match player to update!");
            return;
        }

        String scoreText = scoreField.getText().trim();
        String matchStatusText = matchStatusField.getText().trim();

        if (scoreText.isEmpty() || matchStatusText.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        selectedMatchPlayer.setScore(Integer.parseInt(scoreText));
        selectedMatchPlayer.getMatch().setStatus(matchStatusText);

        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        if (matchPlayerDAO.updateMatchPlayer(selectedMatchPlayer)) {
            System.out.println("MatchPlayer updated successfully.");
            refreshMatchPlayerList();
        } else {
            System.out.println("Failed to update MatchPlayer.");
        }
    }

    private void deleteMatchPlayer() {
        String selectedMatchPlayerInfo = matchPlayerListView.getSelectionModel().getSelectedItem();
        if (selectedMatchPlayerInfo == null) {
            System.out.println("Select a match player to delete!");
            return;
        }

        int selectedMatchID = Integer.parseInt(selectedMatchPlayerInfo.split("-")[0].trim());

        MatchPlayerDAO matchPlayerDAO = new MatchPlayerDAO();
        MatchPlayer matchPlayerToDelete = matchPlayerDAO.getAllMatchPlayers().stream()
                .filter(mp -> mp.getMatch().getId() == selectedMatchID)
                .findFirst()
                .orElse(null);

        if (matchPlayerToDelete == null) {
            System.out.println("MatchPlayer not found!");
            return;
        }

        if (matchPlayerDAO.deleteMatchPlayer(matchPlayerToDelete)) {
            System.out.println("MatchPlayer deleted successfully.");
            clearForm();
            refreshMatchPlayerList();
        } else {
            System.out.println("Failed to delete MatchPlayer.");
        }
    }
}

