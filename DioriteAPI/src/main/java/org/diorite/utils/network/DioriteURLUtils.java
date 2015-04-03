package org.diorite.utils.network;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public final class DioriteURLUtils
{
    private DioriteURLUtils()
    {
    }

    public static String encodeUTF8(final String str)
    {
        if (str == null)
        {
            return "";
        }
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        } catch (final UnsupportedEncodingException e)
        { // should never be thrown
            throw new RuntimeException(e);
        }
    }

    public static String createQueryElement(final String key, final String value)
    {
        String result = encodeUTF8(key);
        if (value != null)
        {
            result += "=" + encodeUTF8(value);
        }
        return result;
    }

    public static StringBuilder addQueryElement(final String key, final String value, final StringBuilder builder)
    {
        builder.append(encodeUTF8(key));
        if (value != null)
        {
            builder.append('=');
            builder.append(encodeUTF8(value));
        }
        return builder;
    }

    public static String buildQuery(final Map<String, Object> query)
    {
        if ((query == null) || query.isEmpty())
        {
            return "";
        }
        return query.entrySet().stream().map(entry -> createQueryElement(entry.getKey(), (entry.getValue() == null) ? null : entry.getValue().toString())).reduce((s1, s2) -> s1 + "&" + s2).orElse("");
    }

    public static URL createURL(final String address)
    {
        try
        {
            return new URL(address);
        } catch (final MalformedURLException e)
        {
            throw new RuntimeException("Can't create URL object for: " + address, e);
        }
    }

    public static URL createURL(final URL url, final String query)
    {
        try
        {
            if ((url.getQuery() != null) && (! url.getQuery().isEmpty()))
            {
                return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "&" + query);
            }
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "?" + query);
        } catch (final MalformedURLException ex)
        {
            throw new RuntimeException("Can't create URL object for query: " + query + ", URL: " + url, ex);
        }
    }
}
