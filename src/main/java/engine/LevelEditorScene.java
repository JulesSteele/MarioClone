package engine;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditorScene extends Scene
{
    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene()
    {
    }

    @Override
    public void init()
    {
        this.loadResources();

        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        // Red
        obj1 = new GameObject("object1", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        obj1.addComponent(new SpriteRenderer(new Vector4f(1f, 0f, 0f, 1f)));
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;

        // Green
        GameObject obj2 = new GameObject("object2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 4);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources()
    {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject obj : this.gameObjects)
        {
            obj.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui()
    {
        ImGui.begin("Test Window");
        ImGui.text("TESTING");
        ImGui.end();
    }
}
