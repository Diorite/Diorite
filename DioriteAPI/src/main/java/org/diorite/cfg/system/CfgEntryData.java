package org.diorite.cfg.system;

public interface CfgEntryData
{
    <T> T getOption(FieldOptions option);

    <T> T getOption(FieldOptions option, T def);

    String getHeader();

    String getFooter();
}
