package Views;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import org.example.duetrockers.DAO.StaffDAO;

public class ViewStartMenu extends View
{

    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 60;

    private StaffDAO staffDAO;
    private Button enter, exit;
    private ListView list;
    private Label staffLabel;

    public ViewStartMenu(int width, int height, ViewManager manager)
    {
        super(width, height, manager);
    }

    @Override
    protected void initializeView()
    {
        staffDAO = new StaffDAO();

        initButtons();

        list = new ListView();

        list.setLayoutX(width / 8 - list.getPrefWidth() / 2);
        list.setLayoutY(height / 8 - list.getPrefHeight() / 2);

        /* Använd staffDAO för att hämta hem en lista av all personal, sen iterera igenom listen och lägg till dem i listView:en*/

        list.getItems().add("Item 1");
        list.getItems().add("Item 2");
        list.getItems().add("Item 3");

        staffLabel = new Label("Staff");

        staffLabel.setFont(new Font(20));

        staffLabel.setLayoutX(width / 8 - staffLabel.getPrefWidth() / 2);
        staffLabel.setLayoutY((height / 8 - staffLabel.getPrefHeight() / 2) - 30);

        root.getChildren().addAll(enter, exit, list, staffLabel);
    }


    private void initButtons()
    {
        enter = new Button("Enter");
        exit = new Button("Exit");

        enter.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        enter.setLayoutX((width / 4 - enter.getPrefWidth() / 2) + BUTTON_WIDTH * 3);
        enter.setLayoutY((height / 2 - enter.getPrefHeight() / 2) - BUTTON_HEIGHT);

        exit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        exit.setLayoutX((width / 4 - exit.getPrefWidth() / 2) + BUTTON_WIDTH * 3);
        exit.setLayoutY((height / 2 - exit.getPrefHeight() / 2) + BUTTON_HEIGHT);

        enter.setOnAction(e ->{

            if(list.getSelectionModel().selectedItemProperty().get() != null)
            {
                manager.switchView(ViewManager.ViewType.MAIN_APP);
            }
        });

        exit.setOnAction(e ->{
            Platform.exit();
        });
    }
}

