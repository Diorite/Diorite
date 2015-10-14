package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.CoreMain;
import org.diorite.event.pipelines.event.player.InteractPipeline;
import org.diorite.event.player.PlayerInteractEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class InteractPipelineImpl extends SimpleEventPipeline<PlayerInteractEvent> implements InteractPipeline
{
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|InteractDebug", ((event, pipeline) -> {
            //Just a debug
            CoreMain.debug(event);
        }));
    }
}
