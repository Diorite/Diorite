package org.diorite.impl.pipelines.system;

import org.diorite.impl.DioriteCore;

/**
 * System pipeline element.
 *
 * @param <T> type of additional data if used.
 */
public interface SystemPipelineHandler<T>
{
    /**
     * Main method, invoked for every element of pipeline.
     *
     * @param core     core object.
     * @param pipeline pipeline reference.
     */
    void handle(DioriteCore core, SystemPipeline<T> pipeline, T data);
}
