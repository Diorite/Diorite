package com.mojang.authlib.yggdrasil.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Response
{
    private String error;
    private String errorMessage;
    private String cause;

    public String getError()
    {
        return this.error;
    }

    protected void setError(final String error)
    {
        this.error = error;
    }

    public String getCause()
    {
        return this.cause;
    }

    protected void setCause(final String cause)
    {
        this.cause = cause;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    protected void setErrorMessage(final String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("error", this.error).append("errorMessage", this.errorMessage).append("cause", this.cause).toString();
    }
}
