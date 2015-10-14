package org.diorite.utils.function.eval;

/**
 * Simple object {@link java.util.function.Supplier}-like class, but use "eval" as method name for simpler reflection usage.
 */
@FunctionalInterface
public interface ObjectEvaluator extends Evaluator<Object>
{
    @Override
    Object eval();
}
