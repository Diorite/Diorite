package org.diorite.impl.input;

import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.input.SenderCommandEvent;
import org.diorite.event.input.SenderTabCompleteEvent;
import org.diorite.event.player.PlayerChatEvent;

public enum InputActionType
{
    CHAT
            {
                @Override
                public boolean checkAction(final InputAction action)
                {
                    return ! ((action.getSender() == null) || (action.getMsg() == null) || ! (action.getSender() instanceof Player));
                }

                @Override
                public void doAction(final InputAction action)
                {
                    EventType.callEvent(new PlayerChatEvent((Player) action.getSender(), new TextComponent(action.getMsg())));
                }
            },
    COMMAND
            {
                @Override
                public boolean checkAction(final InputAction action)
                {
                    return ! ((action.getMsg() == null) || action.getMsg().isEmpty() || (action.getSender() == null));
                }

                @Override
                public void doAction(final InputAction action)
                {
                    EventType.callEvent(new SenderCommandEvent(action.getSender(), action.getMsg()));
                }
            },
    TAB_COMPLETE
            {
                @Override
                public boolean checkAction(final InputAction action)
                {
                    return ! ((action.getSender() == null) || (action.getMsg() == null));
                }

                @Override
                public void doAction(final InputAction action)
                {
                    EventType.callEvent(new SenderTabCompleteEvent(action.getSender(), action.getMsg()));
                }
            };

    public abstract boolean checkAction(InputAction action);

    public abstract void doAction(InputAction action);
}
