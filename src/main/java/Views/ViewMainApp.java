package Views;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.*;
import org.example.duetrockers.entities.*;

import java.util.List;

public class ViewMainApp extends View
{
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 60;


    private Button manage, returnButton, exit;

    private Label staffLabel;

    private ListView<String> tableList;

    private ViewManager.ViewType currentTable;


    public ViewMainApp(int width, int height, ViewManager manager)
    {
        super(width, height, manager);

        currentTable = null;
    }

    @Override
    protected void initializeView()
    {
        manage = new Button("Manage");
        returnButton = new Button("Return");
        exit = new Button("Exit");

        manage.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        returnButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        manage.setLayoutX((width / 2 - manage.getPrefWidth() / 2) + 150);
        manage.setLayoutY((height / 6 - manage.getPrefHeight() / 2) + BUTTON_HEIGHT);

        returnButton.setLayoutX((width / 2 - returnButton.getPrefWidth() / 2)+ 150);
        returnButton.setLayoutY((height / 6 - returnButton.getPrefHeight() / 2) + (BUTTON_HEIGHT * 2) + 10);

        exit.setLayoutX((width / 2 - exit.getPrefWidth() / 2)+ 150);
        exit.setLayoutY((height / 6 - exit.getPrefHeight() / 2) + (BUTTON_HEIGHT * 3) + 20);

        manage.setOnAction(e->{

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

        tableList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null)
            {

                switch(newValue)
                {
                    case "Staff":
                        currentTable = ViewManager.ViewType.STAFF;
                        break;
                    case "Games":
                        currentTable = ViewManager.ViewType.GAMES;
                        break;
                    case "Players":
                        currentTable = ViewManager.ViewType.PLAYERS;
                        break;
                    case "Teams":
                        currentTable = ViewManager.ViewType.TEAMS;
                        break;
                    case "Team vs Team-matches":
                        currentTable = ViewManager.ViewType.TEAM_MATCHES;
                        break;
                    case "Player vs Player-matches":
                        currentTable = ViewManager.ViewType.PLAYER_MATCHES;
                        break;
                }
            }

        });

        root.getChildren().addAll(manage, returnButton, exit, staffLabel, tableList);
    }
}
