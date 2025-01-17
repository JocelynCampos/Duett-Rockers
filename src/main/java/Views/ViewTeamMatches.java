package Views;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    private Team Team1, Team2;
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
        setupSearchField();

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

        //Formulär Team1
        Label teamOneLabel = new Label("Team One: ");
        teamOneComboBox.getItems().addAll(availableTeamsListView.getItems());
        teamOneComboBox.setOnAction(e -> {
            String selectedTeam = teamOneComboBox.getValue();
            if (selectedTeam != null) {
                Team1 = teamDAO.getTeamByName(selectedTeam);
            }
        });

        //Formulär Team2
        Label teamTwoLabel = new Label("Team Two: ");
        teamTwoComboBox.getItems().addAll(availableTeamsListView.getItems());
        teamTwoComboBox.setOnAction(e -> {
            String selectedTeam = teamTwoComboBox.getValue();
            if (selectedTeam != null) {
                Team2 = teamDAO.getTeamByName(selectedTeam);
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
        Team1 = null;
        Team2 = null;
    }

    private int getMatchID(String matchText) {
        return matchIdMap.getOrDefault(matchText, -1);
    }

    private void loadAvailableTeams() {
        List<Team> teams = teamDAO.getAllTeams();

        for (Team team : teams) {
            String teamName = team.getTeamName();
            availableTeamsListView.getItems().add(teamName);
            teamOneComboBox.getItems().add(teamName);
            teamTwoComboBox.getItems().add(teamName);
        }
    }

    private void handleAddMatch() {
        if (Team1 != null && Team2 != null) {
            MatchTeam newMatch = new MatchTeam();
            newMatch.setTeam1(Team1);
            newMatch.setTeam2(Team2);
            matchTeamDAO.saveMatchTeam(newMatch);

            teamListView.getItems().add(Team1.getTeamName() + " vs " + Team2.getTeamName());
            resetSelection();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Match added!");
            alert.setHeaderText(null);
            alert.setContentText("Match between " + newMatch.getTeam1().getTeamName() + " and " + newMatch.getTeam2().getTeamName() + " has succsessfully been created!");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error..");
            alert.setHeaderText(null);
            alert.setContentText("\"Choose two teams before creating a match!\"");
            alert.showAndWait();
        }
    }

    private void handleUpdateMatch() {
        String selectedMatch = teamListView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            MatchTeam match = matchTeamDAO.getMatchTeamById(getMatchID(selectedMatch));
            match.setTeam1(Team1);
            match.setTeam2(Team2);
            matchTeamDAO.updateMatchTeam(match);
            loadTeamMatches();
            resetSelection();
            System.out.println("Updating team: " + selectedMatch);
        }
    }

    private void handleDeleteMatch() {
        String selectedMatch = teamListView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            int matchId = getMatchID(selectedMatch);
            if (matchId != 1) {
                matchTeamDAO.deleteMatchTeam(matchId);
                teamListView.getItems().remove(selectedMatch);
                System.out.println("Match deleted: " + selectedMatch);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Could not find the selected match to delete");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error.");
            alert.setHeaderText(null);
            alert.setContentText("No match selected to delete.");
            alert.showAndWait();
        }
    }
}
