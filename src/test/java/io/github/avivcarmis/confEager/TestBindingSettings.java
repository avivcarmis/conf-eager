package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.exceptions.ConfEagerPropertiesMissingException;
import io.github.avivcarmis.confEager.properties.ConfEagerPropertyBoolean;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestBindingSettings {

    @Test
    public void testBasic() {
        Basic confEager = bind("property", "true", Basic.class);
        Assert.assertEquals(confEager.property.get(), true);
    }

    @Test
    public void testFieldFilter() {
        bind(new HashMap<>(), FieldFilter.class);
    }

    @Test
    public void testEnv() {
        Env confEager = bind("staging/property", "true", Env.class);
        Assert.assertEquals(confEager.property.get(), true);
    }

    @Test(expected = ConfEagerPropertiesMissingException.class)
    public void testEnvFailure() {
        bind("property", "true", Env.class);
    }

    public static class Basic extends ConfEager {

        private final ConfEagerPropertyBoolean property = new ConfEagerPropertyBoolean();

    }

    public static class FieldFilter extends ConfEager {

        private final ConfEagerPropertyBoolean ignored = new ConfEagerPropertyBoolean();

        @Override
        protected ConfEagerFieldFilter defaultFieldFilter() {
            return field -> false;
        }

    }

    public static class Env extends ConfEager {

        private final ConfEagerPropertyBoolean property = new ConfEagerPropertyBoolean();

        @Override
        protected String defaultEnvironment() {
            return "staging/";
        }

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
