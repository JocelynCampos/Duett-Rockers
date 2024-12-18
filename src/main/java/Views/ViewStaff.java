package Views;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.PersonDAO;
import org.example.duetrockers.DAO.StaffDAO;
import org.example.duetrockers.entities.Person;
import org.example.duetrockers.entities.Staff;

import java.util.List;

public class ViewStaff extends View
{
    private TextField firstNameField, lastNameField, countryField, emailField, nicknameField, cityField, postalCodeField, streetField;
    private Label firstNameLabel, lastNameLabel, countryLabel, emailLabel, nicknameLabel, cityLabel, postalCodeLabel, streetLabel, titleLabel;
    private Button add, update, delete, returnButton, exit;

    String firstName, lastName, country, email, nickname, city, postalCode, street;

    private ListView staffListView;

    private StaffDAO staffDAO;
    private Staff pickedStaff;

    public ViewStaff(int width, int height, ViewManager manager)
    {
        super(width, height, manager);
    }

    @Override
    protected void initializeView()
    {
        firstNameField = new TextField();
        lastNameField = new TextField();
        countryField = new TextField();
        emailField = new TextField();
        nicknameField = new TextField();
        cityField = new TextField();
        postalCodeField = new TextField();
        streetField = new TextField();

        firstNameLabel = new Label("First Name:");
        lastNameLabel = new Label("Last Name:");
        countryLabel = new Label("Country:");
        emailLabel = new Label("Email:");
        nicknameLabel = new Label("Nickname:");
        cityLabel = new Label("City:");
        postalCodeLabel = new Label("Postal Code:");
        streetLabel = new Label("Street:");
        titleLabel = new Label("Staff Management");

        add = new Button("Add");
        update = new Button("Update");
        delete = new Button("Delete");
        returnButton = new Button("Return");
        exit = new Button("Exit");

        staffListView = new ListView<>();

        staffDAO = new StaffDAO();

        initFields();
        initLabels();
        initViewList();
        initButtons();
    }

    private void initFields()
    {
        firstNameField.setLayoutX(width / 12 - firstNameLabel.getPrefWidth() / 2);
        firstNameField.setLayoutY(height / 12 - firstNameLabel.getPrefHeight() / 2);
        lastNameField.setLayoutX(width / 12 - lastNameLabel.getPrefWidth() / 2);
        lastNameField.setLayoutY((height / 12 - lastNameLabel.getPrefHeight() / 2) + 50);
        countryField.setLayoutX(width / 12 - countryLabel.getPrefWidth() / 2);
        countryField.setLayoutY((height / 12 - countryLabel.getPrefHeight() / 2) + 100);
        emailField.setLayoutX(width / 12 - emailLabel.getPrefWidth() / 2);
        emailField.setLayoutY((height / 12 - emailLabel.getPrefHeight() / 2) + 150);
        nicknameField.setLayoutX(width / 12 - nicknameLabel.getPrefWidth() / 2);
        nicknameField.setLayoutY((height / 12 - nicknameLabel.getPrefHeight() / 2) + 200);
        cityField.setLayoutX(width / 12 - cityLabel.getPrefWidth() / 2);
        cityField.setLayoutY((height / 12 - cityLabel.getPrefHeight() / 2) + 250);
        postalCodeField.setLayoutX(width / 12 - postalCodeLabel.getPrefWidth() / 2);
        postalCodeField.setLayoutY((height / 12 - postalCodeLabel.getPrefHeight() / 2) + 300);
        streetField.setLayoutX(width / 12 - streetLabel.getPrefWidth() / 2);
        streetField.setLayoutY((height / 12 - streetLabel.getPrefHeight() / 2) + 350);

        root.getChildren().addAll(firstNameField, lastNameField, countryField, emailField, nicknameField, cityField,
                postalCodeField, streetField);
    }

    private void initLabels()
    {
        firstNameLabel.setLayoutX(firstNameField.getLayoutX());
        firstNameLabel.setLayoutY(firstNameField.getLayoutY() - 20);
        lastNameLabel.setLayoutX(lastNameField.getLayoutX());
        lastNameLabel.setLayoutY(lastNameField.getLayoutY() - 20);
        countryLabel.setLayoutX(countryField.getLayoutX());
        countryLabel.setLayoutY(countryField.getLayoutY() - 20);
        emailLabel.setLayoutX(emailField.getLayoutX());
        emailLabel.setLayoutY(emailField.getLayoutY() - 20);
        nicknameLabel.setLayoutX(nicknameField.getLayoutX());
        nicknameLabel.setLayoutY(nicknameField.getLayoutY() - 20);
        cityLabel.setLayoutX(cityField.getLayoutX());
        cityLabel.setLayoutY(cityField.getLayoutY() - 20);
        postalCodeLabel.setLayoutX(postalCodeField.getLayoutX());
        postalCodeLabel.setLayoutY(postalCodeField.getLayoutY() - 20);
        streetLabel.setLayoutX(streetField.getLayoutX());
        streetLabel.setLayoutY(streetField.getLayoutY() - 20);

        titleLabel.setFont(new Font(15));
        titleLabel.setLayoutX(firstNameLabel.getLayoutX());
        titleLabel.setLayoutY(firstNameLabel.getLayoutY() - 30);


        root.getChildren().addAll(firstNameLabel, lastNameLabel, countryLabel, emailLabel, nicknameLabel, cityLabel,
                postalCodeLabel, streetLabel, titleLabel);
    }

    private void initViewList()
    {
        staffListView.setLayoutX(firstNameField.getLayoutX() * 5);
        staffListView.setLayoutY(firstNameField.getLayoutY());

        loadStaffMembers();

        staffListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null)
            {
                pickedStaff = (Staff) newValue;
                loadFieldsWithData();
            }
        });

        root.getChildren().addAll(staffListView);
    }

    private void initButtons()
    {
        add.setLayoutX(streetField.getLayoutX());
        add.setLayoutY(streetField.getLayoutY() + 65);
        update.setLayoutX(streetField.getLayoutX() + 50);
        update.setLayoutY(streetField.getLayoutY() + 65);
        delete.setLayoutX(streetField.getLayoutX() + 110);
        delete.setLayoutY(streetField.getLayoutY() + 65);
        returnButton.setLayoutX(streetField.getLayoutX() + 175);
        returnButton.setLayoutY(streetField.getLayoutY() + 65);
        exit.setLayoutX(streetField.getLayoutX() + 250);
        exit.setLayoutY(streetField.getLayoutY() + 65);

        add.setOnAction(event -> {
            if(addStaff())
            {
                System.out.println("Adding staff was successful");
            }
        });

        update.setOnAction(event -> {
           if(updateStaff())
           {
               System.out.println("Updating staff was successful");
           }
        });

        delete.setOnAction(event -> {
           if(deleteStaff())
           {
               System.out.println("Deleting staff was successful");
           }
        });

        returnButton.setOnAction(event -> {
            manager.switchToPreviousView();
        });

        exit.setOnAction(event -> {
            Platform.exit();
        });

        root.getChildren().addAll(add, update, delete, returnButton, exit);
    }

    private void loadStaffMembers()
    {
        List<Staff> staffList = staffDAO.getAllStaff();

        for(Staff staff : staffList)
        {
            staffListView.getItems().add(staff);
        }
    }

    private void loadFieldsWithData()
    {
        firstNameField.clear();
        lastNameField.clear();
        countryField.clear();
        emailField.clear();
        nicknameField.clear();
        cityField.clear();
        postalCodeField.clear();
        streetField.clear();

        firstNameField.setText(pickedStaff.getPerson().getFirstName());
        lastNameField.setText(pickedStaff.getPerson().getLastName());
        countryField.setText(pickedStaff.getPerson().getCountry());
        emailField.setText(pickedStaff.getPerson().getEmail());
        nicknameField.setText(pickedStaff.getPerson().getNickname());
        cityField.setText(pickedStaff.getPerson().getCity());
        postalCodeField.setText(pickedStaff.getPerson().getPostalCode());
        streetField.setText(pickedStaff.getPerson().getStreet());
    }

    private boolean addStaff()
    {
        boolean result = false;

        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        country = countryField.getText();
        email = emailField.getText();
        nickname = nicknameField.getText();
        city = cityField.getText();
        postalCode = postalCodeField.getText();
        street = streetField.getText();

        Person newPerson = new Person(firstName, lastName, nickname, street, postalCode, city, country, email);
        Staff newStaff = new Staff(newPerson);

        if(staffDAO.saveStaff(newStaff, newPerson))
        {
            result = true;
            staffListView.getItems().add(newStaff);
        }

        return result;
    }

    private boolean updateStaff()
    {
        boolean result = false;

        pickedStaff.getPerson().setFirstName(firstNameField.getText());
        pickedStaff.getPerson().setLastName(lastNameField.getText());
        pickedStaff.getPerson().setCountry(countryField.getText());
        pickedStaff.getPerson().setEmail(emailField.getText());
        pickedStaff.getPerson().setNickname(nicknameField.getText());
        pickedStaff.getPerson().setCity(cityField.getText());
        pickedStaff.getPerson().setPostalCode(postalCodeField.getText());
        pickedStaff.getPerson().setStreet(streetField.getText());

        if(staffDAO.updateStaff(pickedStaff))
        {
            result = true;
        }


        return result;
    }

    private boolean deleteStaff()
    {
        boolean result = false;

        if(staffDAO.deleteStaff(pickedStaff))
        {
            staffListView.getItems().remove(pickedStaff);
            result = true;
        }
        return  result;
    }
}
