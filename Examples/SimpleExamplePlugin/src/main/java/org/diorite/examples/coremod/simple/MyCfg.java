package org.diorite.examples.coremod.simple;

import java.util.HashMap;
import java.util.Map;

import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

@CfgComment("Welcome in configuration file!")
@CfgDelegateDefault("new MyCfg()")
public class MyCfg
{
    @CfgComment("This is some option with comment on it.")
    private String               message = "Default message";
    private Map<int[], double[]> map     = new HashMap<>(2);

    {
        this.map.put(new int[]{1, 2, 3}, new double[]{0.1, 0.2, 0.3});
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public Map<int[], double[]> getMap()
    {
        return this.map;
    }

    public void setMap(final Map<int[], double[]> map)
    {
        this.map = map;
    }
}
