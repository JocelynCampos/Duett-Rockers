package Views;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.GameDAO;
import org.example.duetrockers.DAO.MatchPlayerDAO;
import org.example.duetrockers.DAO.PlayerDAO;
import org.example.duetrockers.DAO.TeamDAO;
import org.example.duetrockers.entities.Game;
import org.example.duetrockers.entities.MatchPlayer;
import org.example.duetrockers.entities.Player;
import org.example.duetrockers.entities.Team;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ViewPlayerMatches extends View {


    private TableView<MatchPlayer> matchPlayerTable;
    private Button addButton, updateButton, deleteButton, returnButton;
    private Label titleLabel;

    private ComboBox<Team> teamOne, teamTwo;
    private ComboBox<Player> playerOne, playerTwo, winner;
    private ComboBox<Game> games;
    private ComboBox<Boolean> completed;
    private ComboBox<Integer> hourComboBox, minuteComboBox;

    private DatePicker datePicker;
    private MatchPlayerDAO matchPlayerDAO;
    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    private GameDAO gameDAO;

    private Player selectedPlayerOne, selectedPlayerTwo, selectedWinner;
    private Game selectedGame;
    private LocalDateTime selectedDateTime;
    private LocalDate selectedDate;
    private Integer selectedHour, selectedMinute;
    private boolean isCompleted;
    private MatchPlayer selectedMatchPlayer;

    public ViewPlayerMatches(int width, int height, ViewManager manager)
    {

        super(width, height, manager);
    }

    @Override
    protected void initializeView()
    {
        titleLabel = new Label("Player Matches Management");
        titleLabel.setFont(new Font(24));

        matchPlayerTable = new TableView();
        matchPlayerDAO = new MatchPlayerDAO();
        teamDAO = new TeamDAO();
        playerDAO = new PlayerDAO();
        gameDAO = new GameDAO();

        selectedPlayerOne = null;
        selectedPlayerTwo = null;

        teamOne = new ComboBox();
        teamTwo = new ComboBox();

        playerOne = new ComboBox();
        playerTwo = new ComboBox();

        completed = new ComboBox();
        winner = new ComboBox();

        games = new ComboBox();

        hourComboBox = new ComboBox();
        minuteComboBox = new ComboBox();

        datePicker = new DatePicker();

        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");
        updateButton = new Button("Update");

        initTableView();
        refreshMatchPlayerList();
        initComboBoxes();
        initDatePicker();
        initButtons();
    }

    private void initTableView()
    {
        //Skapa kolumner för TableView
        TableColumn<MatchPlayer, String> gameColumn = new TableColumn<>("Game");
        gameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGame().toString()));

        TableColumn<MatchPlayer, String> player1Column = new TableColumn<>("Player 1");
        player1Column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPlayer1().toString()));

        TableColumn<MatchPlayer, String> player2Column = new TableColumn<>("Player 2");
        player2Column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPlayer2().toString()));

        TableColumn<MatchPlayer, String> matchDateColumn = new TableColumn<>("Match Date");
        matchDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMatchDate().toString()));

        TableColumn<MatchPlayer, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isCompleted()));

        TableColumn<MatchPlayer, String> winnerColumn = new TableColumn<>("Winner");
        winnerColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                        cellData.getValue().getWinner() != null ? cellData.getValue().getWinner().toString() : "None"));

        matchPlayerTable.getColumns().addAll(gameColumn, player1Column, player2Column, matchDateColumn, completedColumn, winnerColumn);

        matchPlayerTable.setLayoutX(10);
        matchPlayerTable.setLayoutY(10);

        matchPlayerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if(newValue != null)
            {
                selectedMatchPlayer = newValue;
                winner.getItems().clear();
                winner.getItems().addAll(selectedMatchPlayer.getPlayer1(), selectedMatchPlayer.getPlayer2());
            }
        });

        root.getChildren().add(matchPlayerTable);
    }

    private void refreshMatchPlayerList()
    {
        matchPlayerTable.getItems().clear();
        ObservableList<MatchPlayer> matchPlayers = FXCollections.observableArrayList(matchPlayerDAO.getAllMatchPlayers());
        matchPlayerTable.setItems(matchPlayers);
    }

    private void initComboBoxes()
    {
        games.setLayoutX(matchPlayerTable.getLayoutX());
        games.setLayoutY(matchPlayerTable.getLayoutY() + 405);

        games.setOnAction(event -> {
           if(games.getSelectionModel().getSelectedItem() != null)
           {
               populateTeamOne(games.getSelectionModel().getSelectedItem());
               populateTeamTwo(games.getSelectionModel().getSelectedItem());
               selectedGame = games.getSelectionModel().getSelectedItem();
               winner.getItems().clear();
           }
        });

        teamOne.setLayoutX(matchPlayerTable.getLayoutX());
        teamOne.setLayoutY(matchPlayerTable.getLayoutY() + 435);

        teamOne.setOnAction(event -> {

            if(teamOne.getSelectionModel().getSelectedItem() != null)
            {
                populatePlayerOne(teamOne.getSelectionModel().getSelectedItem());
            }
        });

        teamTwo.setLayoutX(matchPlayerTable.getLayoutX() + 155);
        teamTwo.setLayoutY(matchPlayerTable.getLayoutY() + 435);

        teamTwo.setOnAction(event -> {

            if(teamTwo.getSelectionModel().getSelectedItem() != null)
            {
                populatePlayerTwo(teamTwo.getSelectionModel().getSelectedItem());
            }
        });

        playerOne.setLayoutX(matchPlayerTable.getLayoutX());
        playerOne.setLayoutY(matchPlayerTable.getLayoutY() + 460);

        playerOne.setOnAction(event -> {

            if(playerOne.getSelectionModel().getSelectedItem() != null)
            {
                selectedPlayerOne = playerOne.getSelectionModel().getSelectedItem();
                updateWinner(selectedPlayerOne);
            }
        });

        playerTwo.setLayoutX(matchPlayerTable.getLayoutX() + 155);
        playerTwo.setLayoutY(matchPlayerTable.getLayoutY() + 460);

        playerTwo.setOnAction(event -> {
           if(playerTwo.getSelectionModel().getSelectedItem() != null)
           {
               selectedPlayerTwo = playerTwo.getSelectionModel().getSelectedItem();
               updateWinner(selectedPlayerTwo);
           }
        });

        winner.setLayoutX(matchPlayerTable.getLayoutX());
        winner.setLayoutY(matchPlayerTable.getLayoutY() + 485);

        winner.getItems().add(null);

        winner.setOnAction(event -> {
           if(winner.getSelectionModel().getSelectedItem() != null)
           {
               selectedWinner = winner.getSelectionModel().getSelectedItem();
           }
        });

        completed.setLayoutX(matchPlayerTable.getLayoutX() + 155);
        completed.setLayoutY(matchPlayerTable.getLayoutY() + 485);

        completed.getItems().addAll(true, false);

        if(completed.getSelectionModel().getSelectedItem() != null)
        {
            isCompleted = completed.getSelectionModel().getSelectedItem();
        }

        populateGames();

        root.getChildren().addAll(teamOne, teamTwo, playerOne, playerTwo, winner, completed, games);
    }

    private void initDatePicker()
    {
        datePicker.setLayoutX(teamTwo.getLayoutX() + 150);
        datePicker.setLayoutY(teamTwo.getLayoutY());

        hourComboBox.setLayoutX(datePicker.getLayoutX());
        hourComboBox.setLayoutY(datePicker.getLayoutY()+25);
        minuteComboBox.setLayoutX(datePicker.getLayoutX()+70);
        minuteComboBox.setLayoutY(datePicker.getLayoutY()+25);

        hourComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        hourComboBox.setValue(12);

        minuteComboBox.getItems().addAll(0, 15, 30, 45);
        minuteComboBox.setValue(0);

        hourComboBox.setOnAction(event -> {
           if(hourComboBox.getSelectionModel().getSelectedItem() != null)
           {
               selectedHour = hourComboBox.getSelectionModel().getSelectedItem();
           }
        });

        minuteComboBox.setOnAction(event -> {
           if(minuteComboBox.getSelectionModel().getSelectedItem() != null)
           {
               selectedMinute = minuteComboBox.getSelectionModel().getSelectedItem();
           }
        });

        datePicker.setOnAction(event -> {

            if(datePicker.getValue() != null)
            {
                selectedDate = datePicker.getValue();
            }

        });

        root.getChildren().addAll(datePicker, hourComboBox, minuteComboBox);
    }

    private void populateTeamOne(Game game)
    {
        teamOne.getItems().clear();

        List<Team> teams = teamDAO.getAllTeams();

        for(Team team: teams)
        {
            if(team.getGame().getGameName().equals(game.getGameName()))
            {
                teamOne.getItems().add(team);
            }
        }

        if(teamOne.getItems().size() > 0)
        {
            teamOne.setValue(teamOne.getItems().get(0));
            populatePlayerOne((Team)teamOne.getItems().get(0));
        }

    }

    private void populateTeamTwo(Game game)
    {
        teamTwo.getItems().clear();

        List<Team> teams = teamDAO.getAllTeams();

        for(Team team: teams)
        {
            if(team.getGame().getGameName().equals(game.getGameName()))
            {
                teamTwo.getItems().add(team);
            }
        }

        if(teamTwo.getItems().size() > 0)
        {
            teamTwo.setValue(teamTwo.getItems().get(0));
            populatePlayerTwo((Team)teamTwo.getItems().get(0));
        }

    }

    private void populatePlayerOne(Team comboTeam)
    {
        playerOne.getItems().clear();

        List<Player> players = playerDAO.getAllPlayers();

        for(Player player: players)
        {
            if(player.getTeam().getTeamName().equals(comboTeam.getTeamName()))
            {
                playerOne.getItems().add(player);
            }
        }
    }

    private void populatePlayerTwo(Team comboTeam)
    {
        playerTwo.getItems().clear();

        List<Player> players = playerDAO.getAllPlayers();

        for (Player player : players)
        {
            if (player.getTeam().getTeamName().equals(comboTeam.getTeamName())) {
                playerTwo.getItems().add(player);
            }
        }
    }

    private void populateGames()
    {
        games.getItems().clear();

        List<Game> gameList = gameDAO.getAllGames();

        for(Game game: gameList)
        {
            games.getItems().add(game);
        }

        games.setValue(games.getItems().get(0));
        populateTeamOne(gameList.get(0));
        populateTeamTwo(gameList.get(0));
    }

    private void updateWinner(Player player)
    {
        if(!winner.getItems().contains(player))
        {
            winner.getItems().add(player);
        }
    }

    private void initButtons()
    {
        addButton.setLayoutX(winner.getLayoutX());
        addButton.setLayoutY(winner.getLayoutY()+25);

        addButton.setOnAction(event -> {

            if(addMatch())
            {
                System.out.println("Succefully added a new match!");
            }
        });

        deleteButton.setLayoutX(addButton.getLayoutX() + 50);
        deleteButton.setLayoutY(addButton.getLayoutY());

        deleteButton.setOnAction(event -> {
            if(deleteMatch())
            {
                System.out.println("Succefully deleted a match!");
            }
        });

        updateButton.setLayoutX(deleteButton.getLayoutX() + 60);
        updateButton.setLayoutY(deleteButton.getLayoutY());

        updateButton.setOnAction(event -> {
           if(updateMatch())
           {
               System.out.println("Succefully updated a match!");
           }
        });

        returnButton.setLayoutX(updateButton.getLayoutX() + 60);
        returnButton.setLayoutY(updateButton.getLayoutY());

        returnButton.setOnAction(event -> {
           manager.switchToPreviousView();
        });

        root.getChildren().addAll(addButton, deleteButton, updateButton, returnButton);
    }

    private boolean addMatch()
    {
        boolean result = false;

        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
        selectedDateTime = selectedDate.atTime(selectedTime);

        MatchPlayer newMatch = new MatchPlayer(selectedGame, selectedPlayerOne, selectedPlayerTwo, selectedDateTime, isCompleted);

        if(matchPlayerDAO.saveMatchPlayer(newMatch))
        {
            refreshMatchPlayerList();
            result = true;
        }

        return result;
    }

    private boolean deleteMatch()
    {
        boolean result = false;

        if(matchPlayerDAO.deleteMatchPlayer(selectedMatchPlayer))
        {
            refreshMatchPlayerList();
            result = true;
        }
        return result;
    }

    private boolean updateMatch()
    {
        boolean result = false;

        if(selectedHour != null && selectedMinute != null)
        {
            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
            selectedDateTime = selectedDate.atTime(selectedTime);
            selectedMatchPlayer.setMatchDate(selectedDateTime);
        }

        if(selectedPlayerOne != null)
        {
            selectedMatchPlayer.setPlayer1(selectedPlayerOne);
        }

        if(selectedPlayerTwo != null)
        {
            selectedMatchPlayer.setPlayer2(selectedPlayerTwo);
        }

        if(selectedWinner != null)
        {
            selectedMatchPlayer.setWinner(selectedWinner);
        }

        if(selectedGame != null)
        {
            selectedMatchPlayer.setGame(selectedGame);
        }

        if(matchPlayerDAO.updateMatchPlayer(selectedMatchPlayer))
        {
            refreshMatchPlayerList();
            result = true;
        }

        return result;
    }
}
