package engine;

import imgui.ImGui;
import render.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene
{
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject = null;

    public Scene()
    {
    }

    public void init()
    {
    }

    public void start()
    {
        for (GameObject obj : gameObjects)
        {
            obj.start();
            this.renderer.add(obj);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject obj)
    {
        if (!isRunning)
        {
            gameObjects.add(obj);
        }
        else
        {
            gameObjects.add(obj);
            obj.start();
            this.renderer.add(obj);
        }
    }

    public abstract void update(float dt);

    public Camera camera()
    {
        return this.camera;
    }

    public void sceneImgui()
    {
        if (activeGameObject != null)
        {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    public void imgui()
    {
    }
}
