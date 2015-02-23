package com.mojang.authlib.minecraft;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class InsecureTextureException extends RuntimeException
{
    public InsecureTextureException(final String message)
    {
        super(message);
    }

    public static class OutdatedTextureException extends InsecureTextureException
    {
        private final Date     validFrom;
        private final Calendar limit;

        public OutdatedTextureException(final Date validFrom, final Calendar limit)
        {
            super("valid from: " + validFrom + ", limit: " + limit);
            this.validFrom = validFrom;
            this.limit = limit;
        }

        public Date getValidFrom()
        {
            return this.validFrom;
        }

        public Calendar getLimit()
        {
            return this.limit;
        }
    }

    public static class WrongTextureOwnerException
            extends InsecureTextureException
    {
        private final GameProfile expected;
        private final UUID        resultId;
        private final String      resultName;

        public WrongTextureOwnerException(final GameProfile expected, final UUID resultId, final String resultName)
        {
            super("exoected: " + expected + ", id: " + resultId + ", name: " + resultName);
            this.expected = expected;
            this.resultId = resultId;
            this.resultName = resultName;
        }

        public GameProfile getExpected()
        {
            return this.expected;
        }

        public UUID getResultId()
        {
            return this.resultId;
        }

        public String getResultName()
        {
            return this.resultName;
        }
    }

    public static class MissingTextureException
            extends InsecureTextureException
    {
        public MissingTextureException(final String message)
        {
            super(message);
        }
    }
}
