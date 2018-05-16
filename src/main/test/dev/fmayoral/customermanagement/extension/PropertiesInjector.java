package dev.fmayoral.customermanagement.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertiesInjector implements ParameterResolver {

	private static Properties TEST_PROPERTIES = null;

	@Override public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return parameterContext.getParameter().getType() == Properties.class;
	}

	@Override public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
			throws ParameterResolutionException {
		if (Objects.isNull(TEST_PROPERTIES)) {
			TEST_PROPERTIES = new Properties();
			try {
				TEST_PROPERTIES.load(new FileReader(new File("src/main/test-resources/test.properties")));
			} catch (final IOException e) {
				throw new ParameterResolutionException("Failed to load test properties", e);
			}
		}
		return TEST_PROPERTIES;
	}
}
