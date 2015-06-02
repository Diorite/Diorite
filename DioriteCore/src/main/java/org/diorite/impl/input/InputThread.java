package org.diorite.impl.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.others.SenderCommandEvent;
import org.diorite.event.others.SenderTabCompleteEvent;
import org.diorite.event.player.PlayerChatEvent;

public class InputThread
{
    private final Queue<ChatAction> actions = new ConcurrentLinkedQueue<>();
    private final ForkJoinPool pool;

    public InputThread(final ForkJoinPool pool)
    {
        this.pool = pool;
    }

    public void add(final ChatAction action)
    {
        if (action == null)
        {
            return;
        }
        switch (action.getType())
        {
            case CHAT:
                if ((action.getSender() == null) || (action.getMsg() == null) || ! (action.getSender() instanceof Player))
                {
                    return;
                }
                break;
            case COMMAND:
                if ((action.getMsg() == null) || action.getMsg().isEmpty() || (action.getSender() == null))
                {
                    return;
                }
                break;
            case TAB_COMPLETE:
                if ((action.getSender() == null) || (action.getMsg() == null))
                {
                    return;
                }
                break;
        }
        this.pool.submit(() -> {
            switch (action.getType())
            {
                case CHAT:
                    EventType.callEvent(new PlayerChatEvent((Player) action.getSender(), new TextComponent(action.getMsg())));
                    break;
                case COMMAND:
                    EventType.callEvent(new SenderCommandEvent(action.getSender(), action.getMsg()));
                    break;
                case TAB_COMPLETE:
                    EventType.callEvent(new SenderTabCompleteEvent(action.getSender(), action.getMsg()));
                    break;
            }
        });
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
        static AtomicInteger i = new AtomicInteger();

        public InputWorkerThread(final ForkJoinPool pool)
        {
            super(pool);
            this.setName("[Diorite|Input-" + i.getAndIncrement() + "]");
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("actions", this.actions).append("pool", this.pool).toString();
    }
}
