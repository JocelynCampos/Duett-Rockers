package Views;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class View
{
    protected Scene scene;
    protected AnchorPane root;
    protected ViewManager manager;
    protected int height, width;

    public View(int width, int height, ViewManager manager)
    {
        root = new AnchorPane();
        scene = new Scene(root, width, height);

        this.width = width;
        this.height = height;
        this.manager = manager;

        initializeView();
    }

    protected abstract void initializeView();

    public Scene getScene()
    {
        return scene;
    }
}
