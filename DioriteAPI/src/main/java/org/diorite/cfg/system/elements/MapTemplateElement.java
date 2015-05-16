package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.Map;

import org.diorite.cfg.system.CfgEntryData;

@SuppressWarnings("rawtypes")
public class MapTemplateElement extends TemplateElement<Map>
{
    public static final MapTemplateElement INSTANCE = new MapTemplateElement();

    public MapTemplateElement()
    {
        super(Map.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object to Map: " + obj);
        });
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Map element, final int level) throws IOException
    {

    }
}
