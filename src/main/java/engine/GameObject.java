package engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    private String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;

    public GameObject(String name)
    {
        init(name, new ArrayList<>(), new Transform(), 0);
    }

    public GameObject(String name, Transform transform, int zIndex)
    {
        init(name, new ArrayList<>(), transform, zIndex);
    }

    public void init(String name, ArrayList<Component> components, Transform transform, int zIndex)
    {
        this.name = name;
        this.zIndex = zIndex;
        this.components = components;
        this.transform = transform;
    }

    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for (Component c : components)
        {
            if (componentClass.isAssignableFrom(c.getClass()))
            {
                try
                {
                    return componentClass.cast(c);
                }
                catch (ClassCastException e)
                {
                    e.printStackTrace();
                    assert false : "ERROR: Casting component";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for (int i = 0; i < components.size(); i++)
        {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass()))
            {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c)
    {
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float dt)
    {
        for (Component component : components)
        {
            component.update(dt);
        }
    }

    public void start()
    {
        for (Component component : components)
        {
            component.start();
        }
    }

    public void imgui()
    {
        for (Component c : components)
        {
            c.imgui();
        }
    }

    public int zIndex()
    {
        return this.zIndex;
    }
}
