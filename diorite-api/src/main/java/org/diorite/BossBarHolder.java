package org.diorite;

import java.util.Collection;

public interface BossBarHolder
{
    /**
     * Adds {@link BossBar} to this holder and for all childs
     *
     * @param bossBar which should be added
     */
    void addBossBar(BossBar bossBar);

    /**
     * Removes {@link BossBar} from this holder
     *
     * @param bossBar which should be removed
     */
    void removeBossBar(BossBar bossBar);

    /**
     * Returns a list of bossbars of this holder
     *
     * @param includeParents should parent's BossBars be included
     * @return list of player's bossbars
     */
    Collection<BossBar> getBossBars(boolean includeParents);

    default Collection<BossBar> getBossBars()
    {
        return this.getBossBars(false);
    }
}
