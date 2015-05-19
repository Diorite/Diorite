package org.diorite.event;

/**
 * Event priority used in {@link org.diorite.event.pipelines.EventPipeline}
 * <p>
 * Higher priority means that this action will be performed later, after
 * actions with lower priority.
 * <p>
 * LOWEST {@literal ->} LOWER {@literal ->} LOW {@literal ->} BELOW_NORMAL {@literal ->} NORMAL {@literal ->} ABOVE_NORMAL {@literal ->} HIGH {@literal ->} HIGHER {@literal ->} HIGHEST
 */
public enum EventPriority
{
    HIGHEST("*EventPriority_HIGHEST*"),
    HIGHER("*EventPriority_HIGHER*"),
    HIGH("*EventPriority_HIGH*"),
    ABOVE_NORMAL("*EventPriority_ABOVE_NORMAL*"),
    NORMAL("*EventPriority_NORMAL*"),
    BELOW_NORMAL("*EventPriority_BELOW_NORMAL*"),
    LOW("*EventPriority_LOW*"),
    LOWER("*EventPriority_LOWER*"),
    LOWEST("*EventPriority_LOWEST*");

    private final String pipelineName;

    EventPriority(final String pipelineName)
    {
        this.pipelineName = pipelineName;
    }

    /**
     * @return name used in pipelines.
     */
    public String getPipelineName()
    {
        return this.pipelineName;
    }

    /**
     * Check if this priority is higher than given one.
     *
     * @param other other priority.
     *
     * @return true if this priority is higher.
     */
    public boolean isHigherThan(final EventPriority other)
    {
        return this.ordinal() > other.ordinal();
    }

    /**
     * Check if this priority is lower than given one.
     *
     * @param other other priority.
     *
     * @return true if this priority is lower.
     */
    public boolean isLowerThan(final EventPriority other)
    {
        return this.ordinal() < other.ordinal();
    }

    /**
     * @return higher priority than current one or this same if it is the highest one.
     */
    public EventPriority getHigher()
    {
        switch (this)
        {
            case HIGHER:
                return HIGHEST;
            case HIGH:
                return HIGHER;
            case ABOVE_NORMAL:
                return HIGH;
            case NORMAL:
                return HIGH;
            case BELOW_NORMAL:
                return NORMAL;
            case LOW:
                return BELOW_NORMAL;
            case LOWER:
                return LOW;
            case LOWEST:
                return LOWER;
            case HIGHEST:
            default:
                return this;
        }
    }

    /**
     * @return lower priority than current one or this same if it is the lowest one.
     */
    public EventPriority getLower()
    {
        switch (this)
        {
            case HIGHEST:
                return HIGHER;
            case HIGHER:
                return HIGH;
            case HIGH:
                return ABOVE_NORMAL;
            case ABOVE_NORMAL:
                return NORMAL;
            case NORMAL:
                return BELOW_NORMAL;
            case BELOW_NORMAL:
                return LOW;
            case LOW:
                return LOWER;
            case LOWER:
                return LOWEST;
            case LOWEST:
            default:
                return this;
        }
    }
}
