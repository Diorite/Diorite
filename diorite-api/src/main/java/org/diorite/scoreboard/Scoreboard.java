package org.diorite.scoreboard;

public interface Scoreboard
{
    String getName();

    void setName();

    ScoreboardPosition getPosition();

    void setPosition(ScoreboardPosition position);
}
