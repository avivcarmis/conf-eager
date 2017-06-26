# ConfEager
ConfEager is a configuration library for Java, designed to be as simple and lightweight as possible, to allow easy integration, and to maximaze dynamic capabilities.

ConfEager data is eagerly loaded, hence it's name, making your configuration super fast and up-to-date at runtime, since everything is loaded on startup, and is stored in memory.

ConfEager library is designed to be easily extended and customized to enable working with whatever technologies you like to use to store configuration. It provides very little out-of-the-box library support, thus it has no dependencies, and it adds tiny footprint to your project.

## The Main Entities
ConfEager library provides 3 main entities:
- Configuration Classes (`ConfEager`) which declare what properties we want to manage.
- Configuration Properties (`ConfEagerProperty`) which declares the name and type of each property.
- Configuration Source (`ConfEagerSource`) which connects to a data source and populates the data into the configuration class properties.

To show this in action, let's start with super simple example in which we would like to read and validate 2 configuration properties from the process System Properties.

First, let's define the properties we would like to read by declaring our Configuration Class, which contains the two Configuration Properties:

```java
public class LocalConfiguration extends ConfEager {
        private final ConfEagerPropertyBoolean enableLogs = new ConfEagerPropertyBoolean();
        private final ConfEagerPropertyString logDirectory = new ConfEagerPropertyString();
}
```

This declares the properties we would like to read, their type and their property names. Everything here is fully customizable, and we've used `boolean` and `String` out-of-the-box properties (more on that later).
Now in order to load them from the process System Properties, to that end, we use the out-of-the-box System Properties configuration source:

```java
LocalConfiguration localConfiguration = ConfEagerSourceSystemProperties.INSTANCE.bind(LocalConfiguration.class);
```

This will look for properties named `enableLogs` and `logDirectory` in the System Properties, if one of them is not found, it will throw a `ConfEagerPropertiesMissingException`. Then it maps the extracted values into types `boolean` and `String`, if one of them fails to parse due to illegal value, it will throw a `ConfEagerIllegalPropertyValue` exception. Then it binds the `localConfiguration` instance to the source (in this case - `ConfEagerSourceSystemProperties` instance). This means that if the source changes, the in-memory values of `localConfiguration` properties gets immidietely updated.

Few notes before moving on:
- Note the hierarchy of the main 3 entities:
	- We declare a Configuration Class (which inherits `ConfEager`)
		- A configuration class contains Configuration Property fields (which inherit `ConfProperty`)
	- Then we use a Configuration Source (which inherits `ConfEagerSource`) to bind and populate the configuration class instance.
- Property names may be overridden (e.g. field `enableLogs` may be read from property named `enable_logs`), more on that in ConfEagerProperty section.
- Properties are required by default, but may be optional. more on that in ConfEagerProperty section.
- Many other out-of-the-box property types are available, but also may easily be created. more on that in ConfEagerProperty section.
- A few out-of-the-box configuration sources (other than SystemProperties) are available, but also may easily be created. more on that in ConfEagerSource section.