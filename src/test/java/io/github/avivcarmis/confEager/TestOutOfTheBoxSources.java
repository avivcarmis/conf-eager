package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.exceptions.ConfEagerPropertiesMissingException;
import io.github.avivcarmis.confEager.properties.ConfEagerPropertyString;
import io.github.avivcarmis.confEager.sources.ConfEagerSourceCombinator;
import io.github.avivcarmis.confEager.sources.ConfEagerSourceEnvironmentVariables;
import io.github.avivcarmis.confEager.sources.ConfEagerSourceSystemProperties;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestOutOfTheBoxSources {

    private static final String TESTED_VALUE = "value";

    @Test
    public void testSystemPropertiesSuccess() {
        System.setProperty("success", TESTED_VALUE);
        SuccessConfEager confEager = ConfEagerSourceSystemProperties.INSTANCE.bind(SuccessConfEager.class);
        Assert.assertEquals(confEager.success.get(), TESTED_VALUE);
    }

    @Test(expected = ConfEagerPropertiesMissingException.class)
    public void testSystemPropertiesFailure() {
        ConfEagerSourceSystemProperties.INSTANCE.bind(FailureConfEager.class);
    }

    @Test(expected = ConfEagerPropertiesMissingException.class)
    public void testEnvironmentVariablesFailure() {
        ConfEagerSourceEnvironmentVariables.INSTANCE.bind(FailureConfEager.class);
    }

    @Test
    public void testCustomSourceSuccess() {
        System.setProperty("success", TESTED_VALUE);
        SuccessConfEager confEager = new CustomSource().bind(SuccessConfEager.class);
        Assert.assertEquals(confEager.success.get(), TESTED_VALUE);
    }

    @Test(expected = ConfEagerPropertiesMissingException.class)
    public void testCustomSourceFailure() {
        new CustomSource().bind(FailureConfEager.class);
    }

    @Test
    public void testSourceCombinator() {
        ConfEagerSourceCombinator source = new ConfEagerSourceCombinator(
                ConfEagerSourceSystemProperties.INSTANCE,
                ConfEagerSourceEnvironmentVariables.INSTANCE,
                new CustomSource(),
                new FailureCustomSource()
        );
        FailureConfEager confEager = source.bind(FailureConfEager.class);
        Assert.assertEquals(confEager.failure.get(), TESTED_VALUE);
    }

    public static class SuccessConfEager extends ConfEager {

        public final ConfEagerPropertyString success = new ConfEagerPropertyString();

    }

    public static class FailureConfEager extends ConfEager {

        public final ConfEagerPropertyString failure = new ConfEagerPropertyString();

    }

    public static class CustomSource extends ConfEagerSource {

        private final Map<String, String> _values;

        public CustomSource() {
            _values = new HashMap<>();
            _values.put("success", TESTED_VALUE);
        }

        @Override
        public String getValueOrNull(String propertyName) {
            return _values.get(propertyName);
        }

    }

    public static class FailureCustomSource extends ConfEagerSource {

        private final Map<String, String> _values;

        public FailureCustomSource() {
            _values = new HashMap<>();
            _values.put("failure", TESTED_VALUE);
        }

        @Override
        public String getValueOrNull(String propertyName) {
            return _values.get(propertyName);
        }

    }

}
