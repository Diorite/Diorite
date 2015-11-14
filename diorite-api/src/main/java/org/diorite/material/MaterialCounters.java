package org.diorite.material;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * DO NOT USE.
 */
class MaterialCounters
{
    /**
     * Counter for all (with subtypes) item materials.
     */
    protected static final AtomicInteger allItems  = new AtomicInteger();
    /**
     * Counter for all (with subtypes) blocks materials.
     */
    protected static final AtomicInteger allBlocks = new AtomicInteger();
    /**
     * Counter for item materials.
     */
    protected static final AtomicInteger items     = new AtomicInteger();
    /**
     * Counter for blocks materials.
     */
    protected static final AtomicInteger blocks    = new AtomicInteger();

    /**
     * Block with largest id
     */
    protected static BlockMaterialData lastBlock;
}
