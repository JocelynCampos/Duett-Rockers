package Views;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager
{

    private Stage stage;
    private View currentView, previousView;
    private int height, width;
    private String currentStaff;

    public ViewManager(Stage stage, int height, int width)
    {
        currentView = null;
        previousView = null;

        this.stage = stage;
        this.height = height;
        this.width = width;
    }

    public void switchView(ViewType type)
    {
        switch (type)
        {
            case START_MENU:
                ViewStartMenu menu = new ViewStartMenu(width, height, this);
                previousView = currentView;
                currentView = menu;
                break;
            case MAIN_APP:
                ViewMainApp mainApp = new ViewMainApp(width, height, this);
                previousView = currentView;
                currentView = mainApp;
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }

        stage.setScene(currentView.getScene());
    }

    public void switchToPreviousView()
    {
        currentView = previousView;
        stage.setScene(currentView.getScene());
    }

    public enum ViewType
    {
        START_MENU,
        MAIN_APP
    }

    public String getCurrentStaff()
    {
        return currentStaff;
    }

    public void setCurrentStaff(String currentStaff)
    {
        this.currentStaff = currentStaff;
    }
}
