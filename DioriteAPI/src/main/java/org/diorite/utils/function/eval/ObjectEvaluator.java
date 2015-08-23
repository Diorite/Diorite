package org.diorite.utils.function.eval;

@FunctionalInterface
public interface ObjectEvaluator extends Evaluator<Object>
{
    @Override
    Object eval();
}
