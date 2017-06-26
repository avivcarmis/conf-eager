package io.github.avivcarmis.confEager.tests;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.ConfEagerSource;
import io.github.avivcarmis.confEager.properties.ConfEagerPropertyString;
import org.junit.Assert;
import org.junit.Test;

public class TestSourceUpdate {

    private static final String VALUE1 = "val1";

    private static final String VALUE2 = "val2";

    @Test
    public void testNoSettings() {
        Source source = new Source(VALUE1);
        TestConfEager confEager = source.bind(TestConfEager.class);
        Assert.assertEquals(confEager.key.get(), VALUE1);
        source.update(VALUE2);
        Assert.assertEquals(confEager.key.get(), VALUE2);
    }

    public static class TestConfEager extends ConfEager {

        private final ConfEagerPropertyString key = new ConfEagerPropertyString();

    }

    private static class Source extends ConfEagerSource {

        private String _value;

        private Source(String value) {
            _value = value;
        }

        private void update(String value) {
            _value = value;
            notifyUpdate();
        }

        @Override
        public String getValueOrNull(String propertyName) {
            return _value;
        }

    }

}
