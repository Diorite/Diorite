package org.diorite.chat.placeholder;

import org.diorite.Diorite;
import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Entity;
import org.diorite.entity.Player;

public class PlaceholderWorker
{

    {
        PlaceholderType<Entity> e = new PlaceholderType<>("entity", Entity.class);
        PlaceholderType<Player> p = new PlaceholderType<>("player", Player.class, e); // zamiast tworzyc powinno byc jakos pobrane

        p.registerItem(new PlaceholderItem<>(p, "wc", player -> new TextComponent(player.getName())));
        final Player player = Diorite.getPlayer("df");
        p.getItem("wc").apply(player);
    }
}
