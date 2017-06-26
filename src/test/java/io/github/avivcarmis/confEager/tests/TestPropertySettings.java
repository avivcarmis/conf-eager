package io.github.avivcarmis.confEager.tests;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.ConfEagerSource;
import io.github.avivcarmis.confEager.exceptions.ConfEagerPropertiesMissingException;
import io.github.avivcarmis.confEager.properties.ConfEagerPropertyBoolean;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestPropertySettings {

    @Test
    public void testNoSettings() {
        TestConfEagerNoSettings confEager = bind("withNoSettings", "true", TestConfEagerNoSettings.class);
        Assert.assertEquals(confEager.withNoSettings.get(), true);
    }

    @Test
    public void testDefaultValue() {
        TestConfEagerDefaultValue confEager = bind(new HashMap<>(), TestConfEagerDefaultValue.class);
        Assert.assertEquals(confEager.withDefaultValue.get(), true);
    }

    @Test(expected = ConfEagerPropertiesMissingException.class)
    public void testPropertyNameFailure() {
        bind("withPropertyName", "true", TestConfEagerPropertyName.class);
    }

    @Test
    public void testPropertyName() {
        TestConfEagerPropertyName confEager = bind("propertyName", "true", TestConfEagerPropertyName.class);
        Assert.assertEquals(confEager.withPropertyName.get(), true);
    }

    @Test
    public void testBoth1Failure() {
        TestConfEagerBoth1 confEager = bind("withBoth1", "false", TestConfEagerBoth1.class);
        Assert.assertEquals(confEager.withBoth1.get(), true);
    }

    @Test
    public void testBoth1() {
        TestConfEagerBoth1 confEager = bind("propertyName2", "false", TestConfEagerBoth1.class);
        Assert.assertEquals(confEager.withBoth1.get(), false);
    }

    @Test
    public void testBoth2Failure() {
        TestConfEagerBoth2 confEager = bind("withBoth2", "false", TestConfEagerBoth2.class);
        Assert.assertEquals(confEager.withBoth2.get(), true);
    }

    @Test
    public void testBoth2() {
        TestConfEagerBoth2 confEager = bind("propertyName3", "false", TestConfEagerBoth2.class);
        Assert.assertEquals(confEager.withBoth2.get(), false);
    }

    public static class TestConfEagerNoSettings extends ConfEager {

        private final ConfEagerPropertyBoolean withNoSettings = new ConfEagerPropertyBoolean();

    }

    public static class TestConfEagerDefaultValue extends ConfEager {

        private final ConfEagerPropertyBoolean withDefaultValue = new ConfEagerPropertyBoolean(defaultValue(true));

    }

    public static class TestConfEagerPropertyName extends ConfEager {

        private final ConfEagerPropertyBoolean withPropertyName = new ConfEagerPropertyBoolean(propertyName("propertyName"));

    }

    public static class TestConfEagerBoth1 extends ConfEager {

        private final ConfEagerPropertyBoolean withBoth1 = new ConfEagerPropertyBoolean(defaultValue(true), propertyName("propertyName2"));

    }

    public static class TestConfEagerBoth2 extends ConfEager {

        private final ConfEagerPropertyBoolean withBoth2 = new ConfEagerPropertyBoolean(propertyName("propertyName3"), defaultValue(true));

    }

    private <T extends ConfEager> T bind(String key, String value, Class<T> tClass) {
        return new Source(key, value).bind(tClass);
    }

    private <T extends ConfEager> T bind(Map<String, String> map, Class<T> tClass) {
        return new Source(map).bind(tClass);
    }

    private static class Source extends ConfEagerSource {

        private final Map<String, String> _map;

        private Source(Map<String, String> map) {
            _map = map;
        }

        private Source(String key, String value) {
            _map = new HashMap<>();
            _map.put(key, value);
        }

        @Override
        public String getValueOrNull(String propertyName) {
            return _map.get(propertyName);
        }

    }

}
