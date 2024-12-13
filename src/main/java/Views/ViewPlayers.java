package Views;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.PersonDAO;
import org.example.duetrockers.DAO.PlayerDAO;
import org.example.duetrockers.DAO.TeamDAO;
import org.example.duetrockers.entities.Person;
import org.example.duetrockers.entities.Player;
import org.example.duetrockers.entities.Team;

import java.util.List;

public class ViewPlayers extends View {

    private ListView<String> playerListView;
    private TextField firstNameField, lastNameField, nicknameField, emailField;
    private ComboBox<String> teamDropdown;
    private Button addButton, updateButton, deleteButton, returnButton;

    private Player selectedPlayer;

    public ViewPlayers(int width, int height, ViewManager manager) {
        super(width, height, manager);
    }

    @Override
    protected void initializeView() {
        Label titleLabel = new Label("Player Management");
        titleLabel.setFont(new Font(24));

        playerListView = new ListView<>();
        refreshPlayerList();

        playerListView.setPrefHeight(height / 2);
        playerListView.setOnMouseClicked(event -> {
            String selected = playerListView.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null && selected != null && selectedPlayer.getPerson().getNickname().equals(selected.split(" - ")[0])) {
                // Om samma spelare är markerad igen, avmarkera och töm formuläret
                playerListView.getSelectionModel().clearSelection();
                clearForm();
                selectedPlayer = null;
            } else {
                fillFormWithPlayerData(selected);
            }
        });


        VBox formBox = createForm();

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        returnButton = new Button("Return");

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, returnButton);

        addButton.setOnAction(e -> addPlayer());
        updateButton.setOnAction(e -> updatePlayer());
        deleteButton.setOnAction(e -> deletePlayer());
        returnButton.setOnAction(e -> manager.switchToPreviousView());

        VBox layout = new VBox(10, titleLabel, playerListView, formBox, buttonBox);
        layout.setLayoutX(20);
        layout.setLayoutY(20);

        root.getChildren().add(layout);
    }

    private VBox createForm() {
        Label firstNameLabel = new Label("First Name:");
        firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();

        Label nicknameLabel = new Label("Nickname:");
        nicknameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label teamLabel = new Label("Team:");
        teamDropdown = new ComboBox<>();
        populateTeamDropdown();

        VBox formBox = new VBox(10, firstNameLabel, firstNameField, lastNameLabel, lastNameField, nicknameLabel, nicknameField, emailLabel, emailField, teamLabel, teamDropdown);
        return formBox;
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        nicknameField.clear();
        emailField.clear();
        teamDropdown.setValue(null);
    }

    private void refreshPlayerList() {
        playerListView.getItems().clear();
        PlayerDAO playerDAO = new PlayerDAO();
        List<Player> players = playerDAO.getAllPlayers();

        for (Player player : players) {
            Person person = player.getPerson();
            if (person != null) {
                String teamName = player.getTeam() != null ? player.getTeam().getTeamName() : "No Team";
                playerListView.getItems().add(person.getNickname() + " - " + teamName);
            }
        }
    }

    private void populateTeamDropdown() {
        teamDropdown.getItems().clear();
        TeamDAO teamDAO = new TeamDAO();
        List<Team> teams = teamDAO.getAllTeams();

        for (Team team : teams) {
            if (team.getTeamName() != null && !team.getTeamName().isEmpty()) {
                teamDropdown.getItems().add(team.getTeamName());
            }
        }
        teamDropdown.getItems().add("No Team"); // För att kunna hantera spelare utan team
    }

    private void fillFormWithPlayerData(String selectedPlayerInfo) {
        if (selectedPlayerInfo == null) return;

        String[] playerInfo = selectedPlayerInfo.split(" - ");
        String playerNickname = playerInfo[0];

        PlayerDAO playerDAO = new PlayerDAO();
        Player player = null;

        for (Player p : playerDAO.getAllPlayers()) {
            if (p.getPerson().getNickname().equals(playerNickname)) {
                player = p;
                break;
            }
        }

        if (player != null) {
            this.selectedPlayer = player;

            Person person = player.getPerson();
            firstNameField.setText(person.getFirstName());
            lastNameField.setText(person.getLastName());
            nicknameField.setText(person.getNickname());
            emailField.setText(person.getEmail());

            if (player.getTeam() != null) {
                teamDropdown.setValue(player.getTeam().getTeamName());
            } else {
                teamDropdown.setValue("No Team");
            }
        }
    }


    private void addPlayer() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String selectedTeam = teamDropdown.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || nickname.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        // Skapa och spara Person
        Person person = new Person(firstName, lastName, nickname, null, null, null, null, email);
        PersonDAO personDAO = new PersonDAO();
        if (!personDAO.savePerson(person)) {
            System.out.println("Failed to create person. Ensure email and nickname are unique.");
            return;
        }

        // Skapa och spara Player
        Player player = new Player();
        player.setPerson(person);

        if (selectedTeam != null && !selectedTeam.equals("No Team")) {
            TeamDAO teamDAO = new TeamDAO();
            Team team = teamDAO.getAllTeams().stream()
                    .filter(t -> t.getTeamName().equals(selectedTeam))
                    .findFirst()
                    .orElse(null);
            player.setTeam(team);
        }

        PlayerDAO playerDAO = new PlayerDAO();
        if (playerDAO.savePlayers(player)) {
            System.out.println("Player added successfully.");
            refreshPlayerList();
        } else {
            System.out.println("Failed to add player.");
        }
    }

    private void updatePlayer() {
        if (selectedPlayer == null) {
            System.out.println("Select a player to update!");
            return;
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String selectedTeam = teamDropdown.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || nickname.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        // Uppdatera Person
        Person personToUpdate = selectedPlayer.getPerson();
        personToUpdate.setFirstName(firstName);
        personToUpdate.setLastName(lastName);
        personToUpdate.setNickname(nickname);
        personToUpdate.setEmail(email);

        PersonDAO personDAO = new PersonDAO();
        if (!personDAO.updatePerson(personToUpdate)) {
            System.out.println("Failed to update person.");
            return;
        }

        // Uppdatera Team
        if (selectedTeam != null && !selectedTeam.equals("No Team")) {
            TeamDAO teamDAO = new TeamDAO();
            Team team = teamDAO.getAllTeams().stream()
                    .filter(t -> t.getTeamName().equals(selectedTeam))
                    .findFirst()
                    .orElse(null);
            selectedPlayer.setTeam(team);
        } else {
            selectedPlayer.setTeam(null);
        }

        PlayerDAO playerDAO = new PlayerDAO();
        if (playerDAO.updatePlayer(selectedPlayer)) {
            System.out.println("Player updated successfully.");

            // Rensa urval och formulär
            clearForm();
            selectedPlayer = null;
            playerListView.getSelectionModel().clearSelection();

            // Uppdatera spelarlistan
            refreshPlayerList();
        } else {
            System.out.println("Failed to update player.");
        }
    }


    private void deletePlayer() {
        String selectedPlayerInfo = playerListView.getSelectionModel().getSelectedItem();
        if (selectedPlayerInfo == null) {
            System.out.println("Select a player to delete!");
            return;
        }

        // Extrahera smeknamn (nickname) från den valda spelaren
        String[] playerInfo = selectedPlayerInfo.split(" - "); // Dela upp på " - " (Nickname - Team)
        String playerNickname = playerInfo[0]; // Första delen är smeknamnet (nickname)

        PlayerDAO playerDAO = new PlayerDAO();
        PersonDAO personDAO = new PersonDAO();

        Player playerToDelete = null;
        for (Player player : playerDAO.getAllPlayers()) {
            Person person = player.getPerson();
            if (person != null && person.getNickname().equals(playerNickname)) {
                playerToDelete = player;
                break;
            }
        }

        if (playerToDelete == null) {
            System.out.println("Player not found!");
            return;
        }

        // Ta bort Player (relaterad entitet)
        if (playerDAO.deletePlayer(playerToDelete)) {
            System.out.println("Player deleted successfully.");
        } else {
            System.out.println("Failed to delete player.");
            return;
        }

        // Ta bort Person efter att Player har tagits bort
        Person personToDelete = playerToDelete.getPerson();
        if (personToDelete != null) {
            boolean personDeleted = personDAO.deletePerson(personToDelete);
            if (personDeleted) {
                System.out.println("Person deleted successfully.");
            } else {
                System.out.println("Failed to delete person.");
            }
        }

        // Uppdatera spelarlistan i GUI
        refreshPlayerList();
    }

}
