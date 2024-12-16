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

import java.util.List;

public class ViewTeamMatches extends View {

    private ListView<String> teamListView;
    private Button addButton, updateButton, deleteButton, returnButton;
    private ListView<String> availableTeamsListView;
    private ListView<String> selectedTeamsListView;
    private TextField searchField;
    private Button selectTeam1Button, getSelectTeam2Button;
    private Team Team1, Team2;

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

        Label titleLabel = new Label("Team Matches");
        titleLabel.setFont(new Font(24));

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");
        teamListView = new ListView<>();
        loadTeamMatches();


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);


        addButton.setOnAction(e -> {
            if (Team1 != null && Team2 != null) {
                MatchTeam newMatch = new MatchTeam();
                newMatch.setTeam1(Team1);
                newMatch.setTeam2(Team2);
                matchTeamDAO.addMatchTeam(newMatch);

                teamListView.getItems().add(Team1.getTeamName() + " vs " + Team2.getTeamName());
                Team1 = null;
                Team2 = null;
                selectedTeamsListView.getItems().clear();
            }

        });
        updateButton.setOnAction(e -> {
            String selectedMatch = teamListView.getSelectionModel().getSelectedItem();
            if (selectedMatch != null) {

            }
        });




        deleteButton.setOnAction(e -> {
            String selectecMatch = teamListView.getSelectionModel().getSelectedItem();
            if (selectecMatch != null) {
                teamListView.getItems().remove(selectecMatch);
            }
        });


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
        }
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

    private void addMatchTeam() {
        for ()
    }
}


