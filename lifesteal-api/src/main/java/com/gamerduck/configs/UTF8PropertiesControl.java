package com.gamerduck.configs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UTF8PropertiesControl extends ResourceBundle.Control {
	public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload) throws IOException {
		final String resourceName = toResourceName(toBundleName(baseName, locale), "properties");
		ResourceBundle bundle = null;
		InputStream stream = null;
		if (reload) {
			final URL url = loader.getResource(resourceName);
			if (url != null) {
				final URLConnection connection = url.openConnection();
				if (connection != null) {
					connection.setUseCaches(false);
					stream = connection.getInputStream();
				}
			}
		} else {stream = loader.getResourceAsStream(resourceName);}
		if (stream != null) {
			try {bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));}
			finally {stream.close();}
		}
		return bundle;
	}
}
