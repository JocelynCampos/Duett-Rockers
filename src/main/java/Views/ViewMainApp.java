package Views;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.*;
import org.example.duetrockers.entities.*;

import java.time.LocalDate;
import java.util.List;

public class ViewMainApp extends View
{
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 60;


    private Button edit, returnButton, exit, delete;

    private Label staffLabel;

    private ListView<String> tableList;

    private ListView<String> dataList;

    private TableView<MatchPlayer> playerMatchTable;
    private TableView<MatchTeam> teamMatchTable;

    private ViewManager.ViewType currentTable;


    public ViewMainApp(int width, int height, ViewManager manager)
    {
        super(width, height, manager);

        currentTable = null;
    }

    @Override
    protected void initializeView()
    {
        edit = new Button("Edit");
        returnButton = new Button("Return");
        exit = new Button("Exit");

        edit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        returnButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        edit.setLayoutX((width / 2 - edit.getPrefWidth() / 2) + 150);
        edit.setLayoutY((height / 5 - edit.getPrefHeight() / 2) + BUTTON_HEIGHT);

        returnButton.setLayoutX((width / 2 - returnButton.getPrefWidth() / 2)+ 150);
        returnButton.setLayoutY((height / 5 - returnButton.getPrefHeight() / 2) + (BUTTON_HEIGHT * 4) + 30);

        exit.setLayoutX((width / 2 - exit.getPrefWidth() / 2)+ 150);
        exit.setLayoutY((height / 5 - exit.getPrefHeight() / 2) + (BUTTON_HEIGHT * 5) + 40);

        edit.setOnAction(e->{

            if(tableList.getSelectionModel().selectedItemProperty() != null && currentTable != null)
            {
                manager.switchView(currentTable);
            }
        });

        returnButton.setOnAction(e -> {
            manager.switchToPreviousView();
        });

        exit.setOnAction(e -> {
            Platform.exit();
        });

        staffLabel = new Label("Current staff: "+ manager.getCurrentStaff());

        staffLabel.setFont(new Font(20));

        staffLabel.setLayoutX(width / 8 - staffLabel.getPrefWidth() / 2);
        staffLabel.setLayoutY((height / 8 - staffLabel.getPrefHeight() / 2) - 30);

        tableList = new ListView();

        tableList.setLayoutX(width / 8 - tableList.getPrefWidth() / 2);
        tableList.setLayoutY(height / 8 - tableList.getPrefHeight() / 2);

        tableList.getItems().addAll("Staff");
        tableList.getItems().addAll("Games");
        tableList.getItems().addAll("Players");
        tableList.getItems().addAll("Teams");
        tableList.getItems().addAll("Team vs Team-matches");
        tableList.getItems().addAll("Player vs Player-matches");

        tableList.setPrefHeight(tableList.getItems().size() * 24 + 2);

        dataList = new ListView();

        dataList.setLayoutX(tableList.getLayoutX());
        dataList.setLayoutY(tableList.getLayoutY() + 200);

        tableList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null)
            {
                dataList.getItems().clear();

                switch(newValue)
                {
                    case "Staff":
                        dataList.setVisible(true);
                        teamMatchTable.setVisible(false);
                        playerMatchTable.setVisible(false);
                        currentTable = ViewManager.ViewType.STAFF;
                        addStaffToDataList();
                        break;
                    case "Games":
                        dataList.setVisible(true);
                        teamMatchTable.setVisible(false);
                        playerMatchTable.setVisible(false);
                        currentTable = ViewManager.ViewType.GAMES;
                        addGamesToDataList();
                        break;
                    case "Players":
                        dataList.setVisible(true);
                        teamMatchTable.setVisible(false);
                        playerMatchTable.setVisible(false);
                        currentTable = ViewManager.ViewType.PLAYERS;
                        addPlayersToDataList();
                        break;
                    case "Teams":
                        dataList.setVisible(true);
                        teamMatchTable.setVisible(false);
                        playerMatchTable.setVisible(false);
                        currentTable = ViewManager.ViewType.TEAMS;
                        addTeamsToDataList();
                        break;
                    case "Team vs Team-matches":
                        dataList.setVisible(false);
                        teamMatchTable.setVisible(true);
                        playerMatchTable.setVisible(false);
                        currentTable = ViewManager.ViewType.TEAM_MATCHES;
                        addTeamMatchesToTableView();
                        break;
                    case "Player vs Player-matches":
                        dataList.setVisible(false);
                        playerMatchTable.setVisible(true);
                        teamMatchTable.setVisible(false);
                        currentTable = ViewManager.ViewType.PLAYER_MATCHES;
                        addPlayerMatchesToTableView();
                        break;
                }
            }

        });

        dataList.setPrefHeight(dataList.getItems().size() * 24 + 2);
        dataList.setMaxHeight(250);

        playerMatchTable = new TableView<>();
        teamMatchTable = new TableView<>();

        playerMatchTable.setLayoutX(dataList.getLayoutX() - 50);
        playerMatchTable.setLayoutY(dataList.getLayoutY() - 40);

        teamMatchTable.setLayoutX(dataList.getLayoutX() - 50);
        teamMatchTable.setLayoutY(dataList.getLayoutY() - 40);

        //Define columns for playerMatchTable
        TableColumn<MatchPlayer, String> gameColumn = new TableColumn<>("Game");
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("game_id"));

        TableColumn<MatchPlayer, String> playerOneColumn = new TableColumn<>("Player One");
        playerOneColumn.setCellValueFactory(new PropertyValueFactory<>("player1_id"));

        TableColumn<MatchPlayer, String> playerTwoColumn = new TableColumn<>("Player Two");
        playerTwoColumn.setCellValueFactory(new PropertyValueFactory<>("player2_id"));

        TableColumn<MatchPlayer, String> winnerColumn = new TableColumn<>("Winner");
        winnerColumn.setCellValueFactory(new PropertyValueFactory<>("winner_id"));

        //Add columns for playerMatchTable
        playerMatchTable.getColumns().addAll(gameColumn, playerOneColumn, playerTwoColumn, winnerColumn);

        playerMatchTable.setVisible(false);
        teamMatchTable.setVisible(false);

        root.getChildren().addAll(edit, returnButton, exit, staffLabel, tableList, dataList, playerMatchTable, teamMatchTable);
    }

    private void addStaffToDataList()
    {
        StaffDAO staffDAO = new StaffDAO();
        List<Staff> staffList = staffDAO.getAllStaff();

        for(Staff staff : staffList)
        {
            dataList.getItems().add(staff.getPerson().getFirstName() + " " + staff.getPerson().getLastName());
        }

        dataList.setPrefHeight(dataList.getItems().size() * 24 + 2);
    }

    private void addGamesToDataList()
    {
        GameDAO gameDAO = new GameDAO();
        List<Game> gameList = gameDAO.getAllGames();

        for(Game game: gameList)
        {
            dataList.getItems().add(game.getGameName());
        }

        dataList.setPrefHeight(dataList.getItems().size() * 24 + 2);
    }

    private void addPlayersToDataList()
    {
        PlayerDAO playerDAO = new PlayerDAO();
        List<Player> playerList = playerDAO.getAllPlayers();

        for(Player player : playerList)
        {
            dataList.getItems().add(player.getPerson().getNickname());
        }

        dataList.setPrefHeight(dataList.getItems().size() * 24 + 2);
    }

    private void addTeamsToDataList()
    {
        TeamDAO teamDAO = new TeamDAO();
        List<Team> teamList = teamDAO.getAllTeams();

        for(Team team : teamList)
        {
            dataList.getItems().add(team.getTeamName());
        }

        dataList.setPrefHeight(dataList.getItems().size() * 24 + 2);
    }

    private void addTeamMatchesToTableView()
    {

    }

    private void addPlayerMatchesToTableView()
    {

    }
}