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

    private ListView <String> teamListView;
    private Button addButton, updateButton, deleteButton, returnButton;
    private ListView<String> availableTeamsListView;
    private ListView<String> selectedTeamsListView;
    private TextField searchField;
    private Button selectTeam1Button, getSelectTeam2Button;
    private MatchTeam Team1, Team2;


    public ViewTeamMatches(int width, int height, ViewManager manager)
    {
        super(width, height, manager);
    }

    private MatchTeamDAO matchTeamDAO;

    @Override
    protected void initializeView()
    {
        root = new AnchorPane();


        matchTeamDAO = new MatchTeamDAO();

        Label titleLabel = new Label("Team Matches");
        titleLabel.setFont(new Font(24));

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");
        teamListView = new ListView<>();
        loadTeamMatches();


        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);


        //addButton.setOnAction();
        //updateButton.setOnAction();
        //deleteButton.setOnAction();
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

    private void addTeam1 () {

    }

    private void addTeam2() {


    }

}
