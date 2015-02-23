package diorite.command;

public interface SubCommand extends Command
{
    Command getParent();
}
