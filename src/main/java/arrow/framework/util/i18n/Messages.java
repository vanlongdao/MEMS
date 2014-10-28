package arrow.framework.util.i18n;


import java.io.Serializable;
import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;


public class Messages implements Serializable {

  public static String get(final String msgKey, final Object... params) {
    try {
      final String message = ArrowResourceBundle.getBundle().getString(msgKey);
      return MessageFormat.format(message, params);
    } catch (final MissingResourceException e) {
      return msgKey;
    }
  }

  @Produces
  @Named("messages")
  @RequestScoped
  Map<String, String> produceMessageMap() {
    final java.util.ResourceBundle bundle = ArrowResourceBundle.getBundle();

    return new AbstractMap<String, String>() {
      @Override
      public String get(final Object key) {
        if (key instanceof String) {
          final String resourceKey = (String) key;
          try {
            return bundle.getString(resourceKey);
          } catch (final MissingResourceException mre) {
            return resourceKey;
          }
        } else
          return null;
      }

      @Override
      public Set<Map.Entry<String, String>> entrySet() {
        final Set<Map.Entry<String, String>> entrySet = new HashSet<Map.Entry<String, String>>();

        final Enumeration<String> keys = bundle.getKeys();

        while (keys.hasMoreElements()) {
          final String key = keys.nextElement();

          entrySet.add(new Map.Entry<String, String>() {
            @Override
            public String getKey() {
              return key;
            }

            @Override
            public String getValue() {
              return get(key);
            }

            @Override
            public String setValue(final String arg0) {
              throw new UnsupportedOperationException();
            }
          });
        }

        return entrySet;
      }
    };
  }


  public static class Message {
    private final String key;
    private final Object[] params;

    public Message(final String key, final Object... params) {
      this.key = key;
      this.params = params;
    }

    public String getKey() {
      return this.key;
    }

    public Object[] getParams() {
      return this.params;
    }
  }
}
