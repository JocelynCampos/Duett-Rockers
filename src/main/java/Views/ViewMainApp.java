package Views;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ViewMainApp extends View
{
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 60;


    private Button add, update, returnButton, exit;

    private Label staffLabel;

    public ViewMainApp(int width, int height, ViewManager manager)
    {
        super(width, height, manager);
    }

    @Override
    protected void initializeView()
    {
        add = new Button("Add");
        update = new Button("Update");
        returnButton = new Button("Return");
        exit = new Button("Exit");

        add.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        update.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        returnButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        add.setLayoutX(width / 2 - add.getPrefWidth() / 2);
        add.setLayoutY((height / 4 - add.getPrefHeight() / 2) + BUTTON_HEIGHT);

        update.setLayoutX(width / 2 - update.getPrefWidth() / 2);
        update.setLayoutY((height / 4 - update.getPrefHeight() / 2) + (BUTTON_HEIGHT * 2) + 10);

        returnButton.setLayoutX(width / 2 - returnButton.getPrefWidth() / 2);
        returnButton.setLayoutY((height / 4 - returnButton.getPrefHeight() / 2) + (BUTTON_HEIGHT * 3) + 20);

        exit.setLayoutX(width / 2 - exit.getPrefWidth() / 2);
        exit.setLayoutY((height / 4 - exit.getPrefHeight() / 2) + (BUTTON_HEIGHT * 4) + 30);

        returnButton.setOnAction(e -> {
           manager.switchToPreviousView();
        });

        exit.setOnAction(e -> {
            Platform.exit();
        });

        staffLabel = new Label(manager.getCurrentStaff());

        staffLabel.setFont(new Font(20));

        staffLabel.setLayoutX(width / 8 - staffLabel.getPrefWidth() / 2);
        staffLabel.setLayoutY((height / 8 - staffLabel.getPrefHeight() / 2) - 30);

        root.getChildren().addAll(add, update, returnButton, exit, staffLabel);
    }

}
