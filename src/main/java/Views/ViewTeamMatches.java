package Views;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.MatchTeamDAO;
import org.example.duetrockers.DAO.TeamDAO;
import org.example.duetrockers.entities.MatchTeam;
import org.example.duetrockers.entities.Team;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewTeamMatches extends View {

    private ListView<String> teamListView;
    private Button addButton, updateButton, deleteButton, returnButton;
    private ListView<String> availableTeamsListView;
    private ListView<String> selectedTeamsListView;
    private TextField searchField;
    private ComboBox<String> teamOneComboBox;
    private ComboBox<String> teamTwoComboBox;
    private Button selectTeam1Button, selectTeam2Button;
    private Team team1, team2;
    private Map<String, Integer> matchIdMap;

    private MatchTeamDAO matchTeamDAO;
    private TeamDAO teamDAO;


    public ViewTeamMatches(int width, int height, ViewManager manager) {
        super(width, height, manager);
        initializeView();
    }

    @Override
    protected void initializeView() {

        matchTeamDAO = new MatchTeamDAO();
        teamDAO = new TeamDAO();
        teamOneComboBox = new ComboBox<>();
        teamTwoComboBox = new ComboBox<>();

        matchIdMap = new HashMap<>();

        //Skapa titel
        Label titleLabel = new Label("Team Matches");
        titleLabel.setFont(new Font(24));

        //Knappar
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");

        //Sökfält
        searchField = new TextField("Search for teams...");

        //ListViews
        availableTeamsListView = new ListView<>();
        teamListView = new ListView<>();
        selectedTeamsListView = new ListView<>();

        selectTeam1Button = new Button("Select Team 1");
        selectTeam2Button = new Button("Select Team 2");

        //Ladda data
        loadAvailableTeams();
        loadTeamMatches();
        System.out.println("Team list contains :" + teamListView.getItems().size() + " items.");
        setupSearchField();

        teamListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Match selected: " + newValue);
            if (newValue != null) {
                autofillComboBoxesWhenUpdating(newValue);
            } else {
                resetSelection();
            }
        });

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);

        addButton.setOnAction(e -> handleAddMatch());
        updateButton.setOnAction(e -> handleUpdateMatch());
        deleteButton.setOnAction(e -> handleDeleteMatch());
        returnButton.setOnAction(e -> manager.switchToPreviousView());


        root.getChildren().addAll(titleLabel, availableTeamsListView, teamListView, buttonBox);

        titleLabel.setLayoutX(20);
        titleLabel.setLayoutY(20);

        availableTeamsListView.setLayoutX(20);
        availableTeamsListView.setLayoutY(100);
        availableTeamsListView.setPrefSize(200, 300);

        teamListView.setLayoutX(250);
        teamListView.setLayoutY(100);
        teamListView.setPrefSize(300, 300);

        buttonBox.setLayoutX(20);
        buttonBox.setLayoutY(420);

        VBox form = createForm();
        form.setLayoutX(600);
        form.setLayoutY(100);
        root.getChildren().add(form);
    }

    private VBox createForm() {
        VBox formBox = new VBox(15);

        Label formTitle = new Label("Select Teams for Match");
        formTitle.setFont(new Font(20));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //Formulär team1
        Label teamOneLabel = new Label("Team One: ");
        teamOneComboBox.getItems().addAll(availableTeamsListView.getItems());
        teamOneComboBox.setOnAction(e -> {
            String selectedTeam = teamOneComboBox.getValue();
            if (selectedTeam != null) {
                team1 = teamDAO.getTeamByName(selectedTeam);
            }
        });

        //Formulär Team2
        Label teamTwoLabel = new Label("Team Two: ");
        teamTwoComboBox.getItems().addAll(availableTeamsListView.getItems());
        teamTwoComboBox.setOnAction(e -> {
            String selectedTeam = teamTwoComboBox.getValue();
            if (selectedTeam != null) {
                team2 = teamDAO.getTeamByName(selectedTeam);
            }
        });

        gridPane.add(teamOneLabel, 0, 0);
        gridPane.add(teamOneComboBox, 1, 0);
        gridPane.add(teamTwoLabel, 0, 1);
        gridPane.add(teamTwoComboBox, 1, 1);

        formBox.getChildren().addAll(formTitle, gridPane);
        return formBox;
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<String> filteredTeams = availableTeamsListView.getItems()
                    .stream()
                    .filter(team -> team.toLowerCase().contains(newValue.toLowerCase()))
                    .toList();
            availableTeamsListView.getItems().setAll(filteredTeams);
        });
    }

    private void loadTeamMatches() {
        List<MatchTeam> matches = matchTeamDAO.getAllMatchTeams();
        for (MatchTeam match : matches) {
            String displayText = match.getTeam1().getTeamName() + " vs " + match.getTeam2().getTeamName();
            teamListView.getItems().add(displayText);
            matchIdMap.put(displayText, match.getId());
        }
    }

    private void resetSelection() {
        teamOneComboBox.setValue(null);
        teamTwoComboBox.setValue(null);
        team1 = null;
        team2 = null;
    }

    private int getMatchID(String matchText) {
        return matchIdMap.getOrDefault(matchText, -1);
    }

    private void loadAvailableTeams() {
        availableTeamsListView.getItems().clear();
        teamOneComboBox.getItems().clear();
        teamTwoComboBox.getItems().clear();

        List<Team> teams = teamDAO.getAllTeams();

        for (Team team : teams) {
            String teamName = team.getTeamName();
            availableTeamsListView.getItems().add(teamName);
            teamOneComboBox.getItems().add(teamName);
            teamTwoComboBox.getItems().add(teamName);
        }
    }

    private void handleAddMatch() {
        if (team1 != null && team2 != null) {

            if (team1.getTeamName().equalsIgnoreCase(team2.getTeamName())) {
                showAlert(Alert.AlertType.WARNING,"Invalid selection","Cannot add a match with same team.");
                return;
            }
            MatchTeam newMatch = new MatchTeam();
            newMatch.setTeam1(team1);
            newMatch.setTeam2(team2);

            try {
                matchTeamDAO.saveMatchTeam(newMatch);

                teamListView.getItems().add(team1.getTeamName() + " vs " + team2.getTeamName());
                resetSelection();

                showAlert(Alert.AlertType.INFORMATION,"Match added!","Match between " + newMatch.getTeam1().getTeamName()
                        + " and " + newMatch.getTeam2().getTeamName()
                        + " has been created successfully.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Duplicate match","Choice is not possible. A match between these teams already exist.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING,"Error.","Choose two teams before creating a match!");
        }
    }

    private void handleUpdateMatch() {
        String selectedMatch = teamListView.getSelectionModel().getSelectedItem();
        System.out.println("Selected Match :" + selectedMatch);
        if (selectedMatch != null) {
            int matchId = getMatchID(selectedMatch);
            MatchTeam match = matchTeamDAO.getMatchTeamById(matchId);

            if (teamOneComboBox.getValue() == null || teamTwoComboBox.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Test", "Testing if working");
                return;
            }

            Team updatedTeam1 = teamDAO.getTeamByName(teamOneComboBox.getValue());
            Team updatedTeam2 = teamDAO.getTeamByName(teamTwoComboBox.getValue());

            if (updatedTeam1.equals(updatedTeam2)) {
                showAlert(Alert.AlertType.WARNING, "Invalid selection", "Invalid selection!");
                return;
            }
            try {
                match.setTeam1(updatedTeam1);
                match.setTeam2(updatedTeam2);
                matchTeamDAO.updateMatchTeam(match);
                loadTeamMatches();
                resetSelection();
                showAlert(Alert.AlertType.INFORMATION, "Match Updated.", "Match was successfully updated!");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Duplicate!", "This match already exist.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING,"No selection made.","Select a match to update. ");
        }
    }

    private void handleDeleteMatch() {
        String selectedMatch = teamListView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            int matchId = getMatchID(selectedMatch);
            if (matchId != 1) {
                matchTeamDAO.deleteMatchTeam(matchId);
                teamListView.getItems().remove(selectedMatch);
                showAlert(Alert.AlertType.INFORMATION, "Match deleted.", "Match was successfully deleted.");
                resetSelection();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error.", "Could not delete.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No selection","Select a match to delete.");
        }
    }


    public void autofillComboBoxesWhenUpdating(String selectedMatch) {
        int matchId = getMatchID(selectedMatch);
        MatchTeam match = matchTeamDAO.getMatchTeamById(matchId);
        if (match != null) {
            teamOneComboBox.setValue(match.getTeam1().getTeamName());
            teamTwoComboBox.setValue(match.getTeam2().getTeamName());
            team1 = match.getTeam1();
            team2 = match.getTeam2();
        } else {
            System.out.println("Match not found.." + selectedMatch);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
