package org.diorite.impl.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InputThread
{
    private final Queue<InputAction> actions = new ConcurrentLinkedQueue<>();
    private final ForkJoinPool pool;

    public InputThread(final ForkJoinPool pool)
    {
        this.pool = pool;
    }

    public void add(final InputAction action)
    {
        if ((action == null) || ! action.getType().checkAction(action))
        {
            return;
        }
        this.pool.submit(() -> action.getType().doAction(action));
    }

    public int getActionsSize()
    {
        return this.actions.size();
    }

    public static InputThread start(final int poolSize)
    {
        return new InputThread(new ForkJoinPool(poolSize, InputWorkerThread::new, null, false));
    }


    public static class InputWorkerThread extends ForkJoinWorkerThread
    {
        static final AtomicInteger i = new AtomicInteger();

        public InputWorkerThread(final ForkJoinPool pool)
        {
            super(pool);
            this.setName("{Diorite|Input-" + i.getAndIncrement() + "}");
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("actions", this.actions).append("pool", this.pool).toString();
    }
}
