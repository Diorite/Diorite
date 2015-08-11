package org.diorite.command;

import java.util.Optional;

public interface ExceptionHandler
{
    <T extends Exception> Optional<T> handle(T exception);
}
