package Views;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    private TextField firstNameField, lastNameField, nicknameField, emailField, streetField, postalCodeField, cityField, countryField;
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

        playerListView.setPrefHeight(height / 2.75);
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
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20); // Horizontal gap between groups of fields
        gridPane.setVgap(5); // Vertical gap between rows

        Label firstNameLabel = new Label("First Name:");
        firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();

        Label nicknameLabel = new Label("Nickname:");
        nicknameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label streetLabel = new Label("Street:");
        streetField = new TextField();

        Label postalCodeLabel = new Label("Postal Code:");
        postalCodeField = new TextField();

        Label cityLabel = new Label("City:");
        cityField = new TextField();

        Label countryLabel = new Label("Country:");
        countryField = new TextField();

        Label teamLabel = new Label("Team:");
        teamDropdown = new ComboBox<>();
        populateTeamDropdown();

        // Adding fields in a two-column layout
        gridPane.add(firstNameLabel, 0, 0); // Row 0, Column 0
        gridPane.add(firstNameField, 0, 1); // Row 1, Column 0

        gridPane.add(lastNameLabel, 1, 0); // Row 0, Column 1
        gridPane.add(lastNameField, 1, 1); // Row 1, Column 1

        gridPane.add(nicknameLabel, 0, 2); // Row 2, Column 0
        gridPane.add(nicknameField, 0, 3); // Row 3, Column 0

        gridPane.add(emailLabel, 1, 2); // Row 2, Column 1
        gridPane.add(emailField, 1, 3); // Row 3, Column 1

        gridPane.add(streetLabel, 0, 4); // Row 4, Column 0
        gridPane.add(streetField, 0, 5); // Row 5, Column 0

        gridPane.add(postalCodeLabel, 1, 4); // Row 4, Column 1
        gridPane.add(postalCodeField, 1, 5); // Row 5, Column 1

        gridPane.add(cityLabel, 0, 6); // Row 6, Column 0
        gridPane.add(cityField, 0, 7); // Row 7, Column 0

        gridPane.add(countryLabel, 1, 6); // Row 6, Column 1
        gridPane.add(countryField, 1, 7); // Row 7, Column 1

        gridPane.add(teamLabel, 0, 8); // Row 8, Column 0
        gridPane.add(teamDropdown, 0, 9); // Row 9, Column 0

        VBox formBox = new VBox(20, gridPane); // Wrap gridPane in a VBox for additional spacing
        return formBox;
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        nicknameField.clear();
        emailField.clear();
        streetField.clear();
        postalCodeField.clear();
        cityField.clear();
        countryField.clear();
        teamDropdown.setValue(null);
    }

    private void refreshPlayerList() {
        playerListView.getItems().clear();
        PlayerDAO playerDAO = new PlayerDAO();
        List<Player> players = playerDAO.getAllPlayers();

        for (Player player : players) {
            Person person = player.getPerson();
            if (person != null) {
                playerListView.getItems().add(person.getNickname());
            }
        }
    }

    private void populateTeamDropdown() {
        teamDropdown.getItems().clear();
        TeamDAO teamDAO = new TeamDAO();
        List<Team> teams = teamDAO.getAllTeams();
        teamDropdown.getItems().add("No Team"); // För att kunna hantera spelare utan team

        for (Team team : teams) {
            if (team.getTeamName() != null && !team.getTeamName().isEmpty()) {
                teamDropdown.getItems().add(team.getTeamName());
            }
        }
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
            streetField.setText(person.getStreet());
            postalCodeField.setText(person.getPostalCode());
            cityField.setText(person.getCity());
            countryField.setText(person.getCountry());

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
        String street = streetField.getText();
        String postalCode = postalCodeField.getText();
        String city = cityField.getText();
        String country = countryField.getText();
        String selectedTeam = teamDropdown.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || nickname.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        Person person = new Person(firstName, lastName, nickname, street, postalCode, city, country, email);
        PersonDAO personDAO = new PersonDAO();
        if (!personDAO.savePerson(person)) {
            System.out.println("Failed to create person. Ensure email and nickname are unique.");
            return;
        }

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
            clearForm();
        } else {
            System.out.println("Failed to add player.");
        }
    }


    private void updatePlayer() {
        if (selectedPlayer == null) {
            System.out.println("Select a player to update!");
            return;
        }

        // Spara index för nuvarande markerad spelare
        int selectedIndex = playerListView.getSelectionModel().getSelectedIndex();

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String street = streetField.getText();
        String postalCode = postalCodeField.getText();
        String city = cityField.getText();
        String country = countryField.getText();
        String selectedTeam = teamDropdown.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || nickname.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }

        Person personToUpdate = selectedPlayer.getPerson();
        personToUpdate.setFirstName(firstName);
        personToUpdate.setLastName(lastName);
        personToUpdate.setNickname(nickname);
        personToUpdate.setEmail(email);
        personToUpdate.setStreet(street);
        personToUpdate.setPostalCode(postalCode);
        personToUpdate.setCity(city);
        personToUpdate.setCountry(country);

        PersonDAO personDAO = new PersonDAO();
        if (!personDAO.updatePerson(personToUpdate)) {
            System.out.println("Failed to update person.");
            return;
        }

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
            refreshPlayerList();

            // Återställ markeringen till tidigare index
            playerListView.getSelectionModel().select(selectedIndex);
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

        // Ta bort Player (relaterad entitet först)
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

        clearForm();
        selectedPlayer = null;
        // Uppdatera spelarlistan i GUI
        refreshPlayerList();
    }
}
