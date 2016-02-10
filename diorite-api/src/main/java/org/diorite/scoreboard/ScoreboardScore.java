package org.diorite.scoreboard;

public interface ScoreboardScore
{
    String getPlayerName();

    void addScore(int score);

    void removeScore(int score);

    int getScore();

    default void incrementScore()
    {
        this.addScore(1);
    }

    default void decrementScore()
    {
        this.removeScore(1);
    }
}
