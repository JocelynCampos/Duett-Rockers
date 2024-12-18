package Views;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.GameDAO;
import org.example.duetrockers.DAO.TeamDAO;
import org.example.duetrockers.entities.Game;
import org.example.duetrockers.entities.Team;

import java.util.List;

public class ViewTeams extends View {

    private ListView<String> teamListView;
    private TextField teamNameField;
    private ComboBox<String> gameDropdown;
    private Button addButton, updateButton, deleteButton, returnButton;

    private Team selectedTeam;

    public ViewTeams(int width, int height, ViewManager manager) {
        super(width, height, manager);
    }

    @Override
    protected void initializeView() {
        Label titleLabel = new Label("Team Management");
        titleLabel.setFont(new Font(24));

        teamListView = new ListView<>();
        refreshTeamList();

        teamListView.setPrefHeight(height / 2);
        teamListView.setOnMouseClicked(event -> {
            String selected = teamListView.getSelectionModel().getSelectedItem();
            if (selectedTeam != null && selected != null && selectedTeam.getTeamName().equals(selected.split(" - ")[0])) {
                // Om samma team är markerat igen, avmarkera och töm formuläret
                teamListView.getSelectionModel().clearSelection();
                clearForm();
                selectedTeam = null;
            } else {
                fillFormWithTeamData(selected);
            }
        });

        VBox formBox = createForm();

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);

        addButton.setOnAction(e -> addTeam());
        updateButton.setOnAction(e -> updateTeam());
        deleteButton.setOnAction(e -> deleteTeam());
        returnButton.setOnAction(e -> manager.switchToPreviousView());

        VBox layout = new VBox(10, titleLabel, teamListView, formBox, buttonBox);
        layout.setLayoutX(20);
        layout.setLayoutY(20);

        root.getChildren().add(layout);
    }

    private VBox createForm() {
        Label teamNameLabel = new Label("Team Name:");
        teamNameField = new TextField();

        Label gameLabel = new Label("Game:");
        gameDropdown = new ComboBox<>();
        populateGameDropdown();

        VBox formBox = new VBox(10, teamNameLabel, teamNameField, gameLabel, gameDropdown);
        return formBox;
    }

    private void clearForm() {
        teamNameField.clear();
        gameDropdown.setValue(null);
    }

    private void refreshTeamList() {
        teamListView.getItems().clear();
        TeamDAO teamDAO = new TeamDAO();
        List<Team> teams = teamDAO.getAllTeams();

        for (Team team : teams) {
            teamListView.getItems().add(team.getTeamName());
        }
    }

    private void populateGameDropdown() {
        gameDropdown.getItems().clear();
        GameDAO gameDAO = new GameDAO();
        List<Game> games = gameDAO.getAllGames();

        for (Game game : games) {
            gameDropdown.getItems().add(game.getGameName());
        }
    }

    private void fillFormWithTeamData(String selectedTeamInfo) {
        String[] teamInfo = selectedTeamInfo.split(" - ");
        String teamName = teamInfo[0];

        TeamDAO teamDAO = new TeamDAO();
        Team team = null;

        for (Team t : teamDAO.getAllTeams()) {
            if (t.getTeamName().equals(teamName)) {
                team = t;
                break;
            }
        }

        if (team != null) {
            this.selectedTeam = team;
            teamNameField.setText(team.getTeamName());
            gameDropdown.setValue(team.getGame() != null ? team.getGame().getGameName() : null);
        }
    }

    private void addTeam() {
        String teamName = teamNameField.getText();
        String selectedGame = gameDropdown.getValue();

        if (teamName.isEmpty() || selectedGame == null) {
            System.out.println("All fields are required!");
            return;
        }

        GameDAO gameDAO = new GameDAO();
        Game game = gameDAO.getAllGames().stream()
                .filter(g -> g.getGameName().equals(selectedGame))
                .findFirst()
                .orElse(null);

        if (game == null) {
            System.out.println("Game not found!");
            return;
        }

        Team team = new Team();
        team.setTeamName(teamName);
        team.setGame(game);

        TeamDAO teamDAO = new TeamDAO();
        if (teamDAO.saveTeam(team)) {
            System.out.println("Team added successfully.");
            refreshTeamList();
            clearForm();
        } else {
            System.out.println("Failed to add team.");
        }
    }

    private void updateTeam() {
        if (selectedTeam == null) {
            System.out.println("Select a team to update!");
            return;
        }

        int selectedIndex = teamListView.getSelectionModel().getSelectedIndex();

        String teamName = teamNameField.getText();
        String selectedGame = gameDropdown.getValue();

        if (teamName.isEmpty() || selectedGame == null) {
            System.out.println("All fields are required!");
            return;
        }

        GameDAO gameDAO = new GameDAO();
        Game game = gameDAO.getAllGames().stream()
                .filter(g -> g.getGameName().equals(selectedGame))
                .findFirst()
                .orElse(null);

        if (game == null) {
            System.out.println("Game not found!");
            return;
        }

        selectedTeam.setTeamName(teamName);
        selectedTeam.setGame(game);

        TeamDAO teamDAO = new TeamDAO();
        if (teamDAO.updateTeam(selectedTeam)) {
            System.out.println("Team updated successfully.");
            refreshTeamList();

            teamListView.getSelectionModel().select(selectedIndex);
        } else {
            System.out.println("Failed to update team.");
        }
    }

    private void deleteTeam() {
        if (selectedTeam == null) {
            System.out.println("Select a team to delete!");
            return;
        }

        TeamDAO teamDAO = new TeamDAO();
        if (teamDAO.deleteTeam(selectedTeam)) {
            System.out.println("Team deleted successfully.");
            refreshTeamList();
            clearForm();
        } else {
            System.out.println("Failed to delete team.");
        }
    }
}