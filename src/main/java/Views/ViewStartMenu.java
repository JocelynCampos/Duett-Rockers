package Views;

import javafx.application.Platform;
import javafx.scene.control.Button;
import org.example.duetrockers.DAO.StaffDAO;

public class ViewStartMenu extends View
{

    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 60;

    private StaffDAO staffDAO;
    private Button enter, exit;

    public ViewStartMenu(int width, int height, ViewManager manager)
    {
        super(width, height, manager);
    }

    @Override
    protected void initializeView()
    {
        staffDAO = new StaffDAO();

        enter = new Button("Enter");
        exit = new Button("Exit");

        enter.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        enter.setLayoutX(width / 2 - enter.getPrefWidth() / 2);
        enter.setLayoutY((height / 2 - enter.getPrefHeight() / 2) - BUTTON_HEIGHT);

        exit.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        exit.setLayoutX(width / 2 - exit.getPrefWidth() / 2);
        exit.setLayoutY((height / 2 - exit.getPrefHeight() / 2) + BUTTON_HEIGHT);

        enter.setOnAction(e ->{
            manager.switchView(ViewManager.ViewType.MAIN_APP);
        });

        exit.setOnAction(e ->{
            Platform.exit();
        });

        root.getChildren().addAll(enter, exit);
    }
}
