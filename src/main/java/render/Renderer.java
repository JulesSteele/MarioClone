package render;

import components.SpriteRenderer;
import engine.GameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Renderer
{
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer()
    {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject obj)
    {
        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null)
        {
            add(sprite);
        }
    }

    public void add(SpriteRenderer sprite)
    {
        boolean added = false;

        for (RenderBatch batch : batches)
        {
            if (batch.hasRoom() && batch.zIndex() == sprite.gameObject.zIndex() )
            {
                Texture texture = sprite.getTexture();
                if (texture == null || (batch.hasTexture(texture) || batch.hasTextureRoom()))
                {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added)
        {
            RenderBatch batch = new RenderBatch(MAX_BATCH_SIZE, sprite.gameObject.zIndex());
            batch.start();
            batches.add(batch);
            batch.addSprite(sprite);
            Collections.sort(batches);
        }
    }

    public void render()
    {
        for (RenderBatch batch : batches)
        {
            batch.render();
        }
    }
}
