package org.diorite.cfg.system;

/**
 * Simple interface for classes contains basic informations about config field.
 */
public interface CfgEntryData
{
    /**
     * Returns value of selected {@link FieldOptions} from this config field.
     * May return null.
     *
     * @param option option to get.
     * @param <T>    type of value.
     *
     * @return value of option or null.
     */
    <T> T getOption(FieldOptions option);

    /**
     * Returns value of selected {@link FieldOptions} from this config field.
     *
     * @param option option to get.
     * @param def    default value of option.
     * @param <T>    type of value.
     *
     * @return value of option or default one.
     */
    <T> T getOption(FieldOptions option, T def);

    /**
     * @return header comment of node, may be null.
     */
    String getHeader();

    /**
     * @return footer comment of node, may be null.
     */
    String getFooter();
}
