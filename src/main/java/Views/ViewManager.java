package Views;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.duetrockers.entities.Staff;

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
                ViewStartMenu menuView = new ViewStartMenu(width, height, this);
                previousView = currentView;
                currentView = menuView;
                break;
            case MAIN_APP:
                ViewMainApp mainAppView = new ViewMainApp(width, height, this);
                previousView = currentView;
                currentView = mainAppView;
                break;
            case GAMES:
                ViewGames gamesView = new ViewGames(width, height, this);
                previousView = currentView;
                currentView = gamesView;
                break;
            case PLAYERS:
                ViewPlayers playersView = new ViewPlayers(width, height, this);
                previousView = currentView;
                currentView = playersView;
                break;
            case PLAYER_MATCHES:
                ViewPlayerMatches playerMatchesView = new ViewPlayerMatches(width, height, this);
                previousView = currentView;
                currentView = playerMatchesView;
                break;
            case STAFF:
                ViewStaff staffView = new ViewStaff(width, height, this);
                previousView = currentView;
                currentView = staffView;
                break;
            case TEAMS:
                ViewTeams teamsView = new ViewTeams(width, height, this);
                previousView = currentView;
                currentView = teamsView;
                break;
            case TEAM_MATCHES:
                ViewTeamMatches teamMatchesView = new ViewTeamMatches(width, height, this);
                previousView = currentView;
                currentView = teamMatchesView;
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
        MAIN_APP,
        GAMES,
        PLAYERS,
        PLAYER_MATCHES,
        STAFF,
        TEAMS,
        TEAM_MATCHES
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
