package Views;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    private Button selectTeam1Button, getSelectTeam2Button;
    private Team Team1, Team2;
    private Map<String, Integer> matchIdMap = new HashMap<>();

    private MatchTeamDAO matchTeamDAO;
    private TeamDAO teamDAO;

    public ViewTeamMatches(int width, int height, ViewManager manager) {
        super(width, height, manager);
    }


    @Override
    protected void initializeView() {
        root = new AnchorPane();

        matchTeamDAO = new MatchTeamDAO();
        teamDAO = new TeamDAO();

        //Skapa titel
        Label titleLabel = new Label("Team Matches");
        titleLabel.setFont(new Font(24));

        //Knappar
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");

        //ListViews
        availableTeamsListView = new ListView<>();
        teamListView = new ListView<>();

        //Ladda data
        loadAvailableTeams();

        selectTeam();

        loadTeamMatches();


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);

        addButton.setOnAction(e -> handleAddMatch());
        updateButton.setOnAction(e -> handleUpdateMatch());
        deleteButton.setOnAction(e -> handleDeleteMatch());
        returnButton.setOnAction(e -> manager.switchToPreviousView());


        root.getChildren().addAll(titleLabel, teamListView, buttonBox);

        titleLabel.setLayoutX(20);
        titleLabel.setLayoutY(20);

        teamListView.setLayoutX(20);
        teamListView.setLayoutY(60);


    }

    private void loadTeamMatches() {
        List<MatchTeam> matches = matchTeamDAO.getAllMatchTeams();
        for (MatchTeam match : matches) {
            String displayText = match.getTeam1().getTeamName() + " vs " + match.getTeam2().getTeamName();
            teamListView.getItems().add(displayText);
            matchIdMap.put(displayText, match.getId());
        }
    }

    private int getMatchID(String matchText) {
        return matchIdMap.getOrDefault(matchText, -1);
    }

    private void loadAvailableTeams() {
        TeamDAO teamDAO = new TeamDAO();
        List<Team> teams = teamDAO.getAllTeams();

        for (Team team : teams) {
            availableTeamsListView.getItems().add(team.getTeamName());
        }
    }

    private void selectTeam() {
        availableTeamsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (Team1 == null) {
                    Team1 = teamDAO.getTeamByName(newValue);
                } else if (Team2 == null) {
                    Team2 = teamDAO.getTeamByName(newValue);
                }
                availableTeamsListView.getItems().remove(newValue);
            }
        });
    }

    private void handleAddMatch() {
        if (Team1 != null && Team2 != null) {
            MatchTeam newMatch = new MatchTeam();
            newMatch.setTeam1(Team1);
            newMatch.setTeam2(Team2);
            matchTeamDAO.saveMatchTeam(newMatch);

            teamListView.getItems().add(Team1.getTeamName() + " vs " + Team2.getTeamName());
            Team1 = null;
            Team2 = null;
            availableTeamsListView.getItems().remove(Team1.getTeamName());
            availableTeamsListView.getItems().remove(Team2.getTeamName());
            loadTeamMatches();

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
        } else { Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error.");
            alert.setHeaderText(null);
            alert.setContentText("No match selected to delete.");
            alert.showAndWait();
        }
    }
}


