package org.diorite.impl.pipelines.event.entity;

import org.diorite.event.entity.EntityDamageByEntityEvent;
import org.diorite.event.pipelines.event.entity.EntityDamageByEntityPipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class EntityDamageByEntityPipelineImpl extends SimpleEventPipeline<EntityDamageByEntityEvent> implements EntityDamageByEntityPipeline
{
    @Override
    public void reset_()
    {
        this.addLast("Diorite|Health", (evt, pipeline) ->
        {
            if (evt.isCancelled())
            {
                return;
            }

            evt.getEntity().setHealth(evt.getEntity().getHealth() - evt.getDamage());
        });
    }
}
