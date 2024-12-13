package Views;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.*;
import org.example.duetrockers.entities.*;

import java.util.List;

public class ViewMainApp extends View
{
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 60;


    private Button add, update, returnButton, exit, delete;

    private Label staffLabel;

    private ListView<String> tableList;

    private ListView<String> dataList;

    private ViewManager.ViewType currentTable;

    public ViewMainApp(int width, int height, ViewManager manager)
    {
        super(width, height, manager);

        currentTable = null;
    }

    @Override
    protected void initializeView()
    {
        add = new Button("Add");
        update = new Button("Update");
        returnButton = new Button("Return");
        exit = new Button("Exit");
        delete = new Button("Delete");

        add.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        update.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        returnButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        delete.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        add.setLayoutX((width / 2 - add.getPrefWidth() / 2) + 150);
        add.setLayoutY((height / 5 - add.getPrefHeight() / 2) + BUTTON_HEIGHT);

        update.setLayoutX((width / 2 - update.getPrefWidth() / 2)+ 150);
        update.setLayoutY((height / 5 - update.getPrefHeight() / 2) + (BUTTON_HEIGHT * 2) + 10);

        delete.setLayoutX((width / 2 - delete.getPrefWidth() / 2)+ 150);
        delete.setLayoutY((height / 5 - delete.getPrefHeight() / 2) + (BUTTON_HEIGHT*3) + 20);

        returnButton.setLayoutX((width / 2 - returnButton.getPrefWidth() / 2)+ 150);
        returnButton.setLayoutY((height / 5 - returnButton.getPrefHeight() / 2) + (BUTTON_HEIGHT * 4) + 30);

        exit.setLayoutX((width / 2 - exit.getPrefWidth() / 2)+ 150);
        exit.setLayoutY((height / 5 - exit.getPrefHeight() / 2) + (BUTTON_HEIGHT * 5) + 40);

        add.setOnAction(e->{

            if(tableList.getSelectionModel().selectedItemProperty() != null && currentTable != null)
            {
                manager.switchView(currentTable);
            }
        });

        update.setOnAction(e->
        {
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
                        currentTable = ViewManager.ViewType.STAFF;
                        addStaffToDataList();
                        break;
                    case "Games":
                        currentTable = ViewManager.ViewType.GAMES;
                        addGamesToDataList();
                        break;
                    case "Players":
                        currentTable = ViewManager.ViewType.PLAYERS;
                        addPlayersToDataList();
                        break;
                    case "Teams":
                        currentTable = ViewManager.ViewType.TEAMS;
                        addTeamsToDataList();
                        break;
                }
            }

        });

        dataList.setPrefHeight(dataList.getItems().size() * 24 + 2);

        root.getChildren().addAll(add, update, returnButton, exit, staffLabel, delete, tableList, dataList);
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
            dataList.getItems().add(player.getNickname());
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
}
