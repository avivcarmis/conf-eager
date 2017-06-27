package io.github.avivcarmis.confEager.tests;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.ConfEagerSource;
import io.github.avivcarmis.confEager.exceptions.ConfEagerIllegalPropertyValueException;
import io.github.avivcarmis.confEager.properties.*;
import org.junit.Assert;
import org.junit.Test;

public class TestOutOfTheBoxProperties {

    @Test
    public void testBooleanSuccess() {
        TestConfEager confEager = bind("booleanProperty", "true");
        Assert.assertEquals(confEager.booleanProperty.get(), true);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testBooleanFailure() {
        bind("booleanProperty", "illegal value");
    }

    @Test
    public void testBooleanArraySuccess() {
        TestConfEager confEager = bind("booleanArrayProperty", "true, false");
        Assert.assertEquals(confEager.booleanArrayProperty.get().length, 2);
        Assert.assertEquals(confEager.booleanArrayProperty.get()[0], true);
        Assert.assertEquals(confEager.booleanArrayProperty.get()[1], false);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testBooleanArrayFailure() {
        bind("booleanArrayProperty", "illegal value");
    }

    @Test
    public void testDoubleSuccess() {
        TestConfEager confEager = bind("doubleProperty", "156");
        Assert.assertEquals(confEager.doubleProperty.get(), 156, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testDoubleFailure() {
        bind("doubleProperty", "illegal value");
    }

    @Test
    public void testDoubleArraySuccess() {
        TestConfEager confEager = bind("doubleArrayProperty", "172, 3");
        Assert.assertEquals(confEager.doubleArrayProperty.get().length, 2);
        Assert.assertEquals(confEager.doubleArrayProperty.get()[0], 172, 0);
        Assert.assertEquals(confEager.doubleArrayProperty.get()[1], 3, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testDoubleArrayFailure() {
        bind("doubleArrayProperty", "illegal value");
    }

    @Test
    public void testFloatSuccess() {
        TestConfEager confEager = bind("floatProperty", "156");
        Assert.assertEquals(confEager.floatProperty.get(), 156, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testFloatFailure() {
        bind("floatProperty", "illegal value");
    }

    @Test
    public void testFloatArraySuccess() {
        TestConfEager confEager = bind("floatArrayProperty", "172, 3");
        Assert.assertEquals(confEager.floatArrayProperty.get().length, 2);
        Assert.assertEquals(confEager.floatArrayProperty.get()[0], 172, 0);
        Assert.assertEquals(confEager.floatArrayProperty.get()[1], 3, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testFloatArrayFailure() {
        bind("floatArrayProperty", "illegal value");
    }

    @Test
    public void testIntegerSuccess() {
        TestConfEager confEager = bind("integerProperty", "156");
        Assert.assertEquals(confEager.integerProperty.get(), 156, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testIntegerFailure() {
        bind("integerProperty", "illegal value");
    }

    @Test
    public void testIntegerArraySuccess() {
        TestConfEager confEager = bind("integerArrayProperty", "172, 3");
        Assert.assertEquals(confEager.integerArrayProperty.get().length, 2);
        Assert.assertEquals(confEager.integerArrayProperty.get()[0], 172, 0);
        Assert.assertEquals(confEager.integerArrayProperty.get()[1], 3, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testIntegerArrayFailure() {
        bind("integerArrayProperty", "illegal value");
    }

    @Test
    public void testLongSuccess() {
        TestConfEager confEager = bind("longProperty", "156");
        Assert.assertEquals(confEager.longProperty.get(), 156, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testLongFailure() {
        bind("longProperty", "illegal value");
    }

    @Test
    public void testLongArraySuccess() {
        TestConfEager confEager = bind("longArrayProperty", "172, 3");
        Assert.assertEquals(confEager.longArrayProperty.get().length, 2);
        Assert.assertEquals(confEager.longArrayProperty.get()[0], 172, 0);
        Assert.assertEquals(confEager.longArrayProperty.get()[1], 3, 0);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testLongArrayFailure() {
        bind("longArrayProperty", "illegal value");
    }

    @Test
    public void testStringSuccess() {
        TestConfEager confEager = bind("stringProperty", "some text");
        Assert.assertEquals(confEager.stringProperty.get(), "some text");
    }

    @Test
    public void testStringArraySuccess() {
        TestConfEager confEager = bind("stringArrayProperty", "value1, value2");
        Assert.assertEquals(confEager.stringArrayProperty.get().length, 2);
        Assert.assertEquals(confEager.stringArrayProperty.get()[0], "value1");
        Assert.assertEquals(confEager.stringArrayProperty.get()[1], "value2");
    }

    @Test
    public void testEnumSuccess() {
        TestConfEager confEager = bind("enumProperty", CustomEnum.VALUE1.name());
        Assert.assertEquals(confEager.enumProperty.get(), CustomEnum.VALUE1);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testEnumFailure() {
        bind("enumProperty", CustomEnum.VALUE1.name().toLowerCase());
    }

    @Test
    public void testEnumCISuccess() {
        TestConfEager confEager = bind("enumCIProperty", CustomEnum.VALUE1.name().toLowerCase());
        Assert.assertEquals(confEager.enumCIProperty.get(), CustomEnum.VALUE1);
    }

    @Test(expected = ConfEagerIllegalPropertyValueException.class)
    public void testEnumCIFailure() {
        bind("enumCIProperty", "illegal value");
    }

    public static class TestConfEager extends ConfEager {

        private final ConfEagerPropertyBoolean booleanProperty = new ConfEagerPropertyBoolean(defaultValue(false));

        private final ConfEagerPropertyBooleanArray booleanArrayProperty = new ConfEagerPropertyBooleanArray(defaultValue(new Boolean[0]));

        private final ConfEagerPropertyDouble doubleProperty = new ConfEagerPropertyDouble(defaultValue(0D));

        private final ConfEagerPropertyDoubleArray doubleArrayProperty = new ConfEagerPropertyDoubleArray(defaultValue(new Double[0]));

        private final ConfEagerPropertyFloat floatProperty = new ConfEagerPropertyFloat(defaultValue(0F));

        private final ConfEagerPropertyFloatArray floatArrayProperty = new ConfEagerPropertyFloatArray(defaultValue(new Float[0]));

        private final ConfEagerPropertyInteger integerProperty = new ConfEagerPropertyInteger(defaultValue(0));

        private final ConfEagerPropertyIntegerArray integerArrayProperty = new ConfEagerPropertyIntegerArray(defaultValue(new Integer[0]));

        private final ConfEagerPropertyLong longProperty = new ConfEagerPropertyLong(defaultValue(0L));

        private final ConfEagerPropertyLongArray longArrayProperty = new ConfEagerPropertyLongArray(defaultValue(new Long[0]));

        private final ConfEagerPropertyString stringProperty = new ConfEagerPropertyString(defaultValue(""));

        private final ConfEagerPropertyStringArray stringArrayProperty = new ConfEagerPropertyStringArray(defaultValue(new String[0]));

        private final ConfEagerPropertyEnum<CustomEnum> enumProperty = new ConfEagerPropertyEnum<>(CustomEnum.class, true, defaultValue(CustomEnum.VALUE1));

        private final ConfEagerPropertyEnum<CustomEnum> enumCIProperty = new ConfEagerPropertyEnum<>(CustomEnum.class, false, defaultValue(CustomEnum.VALUE1));

    }

    private TestConfEager bind(String key, String value) {
        return new Source(key, value).bind(TestConfEager.class);
    }

    public enum CustomEnum {

        VALUE1

    }

    private static class Source extends ConfEagerSource {

        private final String _key;

        private final String _value;

        private Source(String key, String value) {
            _key = key;
            _value = value;
        }

        @Override
        public String getValueOrNull(String propertyName) {
            return propertyName.equals(_key) ? _value : null;
        }

    }

}
