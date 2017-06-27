# ConfEager
ConfEager is a configuration library for Java, designed to be as simple and lightweight as possible, to allow easy integration, and to maximaze dynamic capabilities.

ConfEager data is eagerly loaded, hence it's name, making your configuration super fast and up-to-date at runtime, since everything is loaded on startup, and is stored in memory.

ConfEager library is designed to be easily extended and customized to enable working with whatever technologies you like to use to store configuration. It provides very little out-of-the-box library support, thus it has no dependencies, and it adds tiny footprint to your project.

## Getting Started

### The Main Entities
ConfEager library provides 3 main entities:
- Configuration Class (`ConfEager`) which declare the properties we want to read into memory.
- Configuration Property (`ConfEagerProperty`) which declares the actual name and type of each property.
- Configuration Source (`ConfEagerSource`) which connects to a data source and populates the data into the configuration class properties.

To show this in action, let's start with super simple example in which we would like to read and validate 2 configuration properties from the process System Properties.

First, let's define the properties we would like to read by declaring our Configuration Class, which contains the two Configuration Properties:

```java
public class LocalConfiguration extends ConfEager {
	public final ConfEagerPropertyBoolean enableLogs = new ConfEagerPropertyBoolean();
	public final ConfEagerPropertyString logDirectory = new ConfEagerPropertyString();
}
```

This declares the properties we would like to read, their type and their property names. Everything here is fully customizable, and we've used `boolean` and `String` out-of-the-box properties (more on that later).
Now in order to load them from the process System Properties, to that end, we use the out-of-the-box System Properties configuration source:

```java
LocalConfiguration localConfiguration = ConfEagerSourceSystemProperties.INSTANCE.bind(LocalConfiguration.class);
```

This will look for properties named `enableLogs` and `logDirectory` in the System Properties, if one of them is not found, it will throw a `ConfEagerPropertiesMissingException`. Then it maps the extracted values into types `boolean` and `String`, if one of them fails to parse due to illegal value, it will throw a `ConfEagerIllegalPropertyValueException`. Then it binds the `localConfiguration` instance to the source (in this case - `ConfEagerSourceSystemProperties` instance). This means that if the source changes, the in-memory values of `localConfiguration` properties gets immidietely updated.

Then in order to read the configuration:

```java
boolean enableLogs = localConfiguration.enableLogs.get();
String logDirectory = localConfiguration.logDirectory.get();
```

Few notes before moving on:
- Note the hierarchy of the main 3 entities:
	- We declare a Configuration Class (which inherits `ConfEager`)
		- A configuration class contains Configuration Property fields (which inherit `ConfProperty`)
	- Then we use a Configuration Source (which inherits `ConfEagerSource`) to bind and populate the configuration class instance.
- Property names may be overridden (e.g. field `enableLogs` may be read from property named `enable_logs`), more on that in ConfEagerProperty section.
- Properties are required by default, but may be optional. [More on that in ConfEagerProperty section](#h6-required-vs-optional-properties "More on that in ConfEagerProperty section").
- Many other out-of-the-box property types are available, but also may easily be created. more on that in ConfEagerProperty section.
- A few out-of-the-box configuration sources (other than SystemProperties) are available, but also may easily be created. more on that in ConfEagerSource section.

### Customization
As mentioned above, by default, ConfEager provides `ConfEagerSource` implementations which read system properties and environment variables. In order to read configuration from MySQL, for example, we may use an implementation like this one:
```java
public class ConfEagerMySQLSource extends ConfEagerSource {

    private final Map<String, String> data;

    public ConfEagerMySQLSource(String dbURL, String username, String password) {
        data = new HashMap<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            String sql;
            sql = "SELECT * FROM `configuration`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                data.put(rs.getString("key"), rs.getString("value"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch(SQLException se){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getValueOrNull(String propertyName) {
        return data.get(propertyName);
    }

}
```

Then in order to use it we can do:

```java
ConfEagerMySQLSource source = new ConfEagerMySQLSource("mysql://example.com", "username", "password");
LocalConfiguration localConfiguration = source.bind(LocalConfiguration.class);
```

Few notes before moving on:
- The same way, we may use any other source, such as Consul KV, ZooKeeper, or any other local or remote source we like.
- This is just an example implementation, it may be implemented using an async client, it may move the initialization phase from the constructor to an init method, or it may have any other form, as long as we fully initialize it before calling `bind` method.
- Note that the url, username and password needed to connect, may be taken from some local configuration source, e.g. system properties, local configuration file, etc. We just need to define another configuration class with some local configuration source, initialize it, and then use the resulted configuration to connect to the remove configuration source. This actually is considered a good practice, in which we seperate our local configuration (i.e. paths on current machine, machine identity, etc...) from cluster configuration (i.e. business logic parameters, or other configuration that relates to other nodes in the cluster).
- Note that this implementation does not support updates. This means that if the data changes on the remote SQL tables, it will not get updated. To support that we need to choose update mechanism (i.e. periodic read, pub/sub, etc...), and then notify each update by calling the inherited `notifyUpdate()` method. more on that in ConfEagerSource section.

## In More Details

### Configuration Class
A ConfEager configuration class is denoted by the class `ConfEager`. The porpuse of this class is to declare the properties we want to read.

This is done by extending `ConfEager`, and adding property class fields. Prefferably, property fields should be declared `public final` so that they will be read-only, and thus no getters will be needed and no outside modification may be possible.

###### Field Filter
When binding a configuration instance to a configuration source, the configuration instance is scanned for all fields which inherit `ConfEagerProperty` and are *not static* to be initialized. The not-static filter may be changed, by overriding the `defaultFieldFilter` method, which receive a field and either approve or disapprove it.

###### Environment
Additionally, all the configuration properties may receive a textual prefix to their property name. This allows for the support of environments, i.e. we can make a configuration class add `staging/` to all property names, thus controlling which environment should be loaded. This is done by overriding the `defaultEnvironment` method and returning the prefix string which by default is an empty string.

### Configuration Property
A ConfEager configuration property is denoted by the class `ConfEagerProperty` The porpuse of this class is to declare the actual name and type of each property.

###### Required vs. Optional Properties
By default, a configuration property is required, and if missing from the source, a `ConfEagerPropertiesMissingException` will be thrown. To denote a property as optional, we need to pass a default values to it's constructor using the `defaultValue` method:

```java
public class Configuration extends ConfEager {
	public final ConfEagerPropertyBoolean enableLogs = new ConfEagerPropertyBoolean(defaultValue(true));
}
```
Note that the `defaultValue` method is inherited from `ConfEager`.
At this point, if `enableLogs` property is missing from the source, it will receive the value of `true`.

### Configuration Source
A ConfEager configuration source is denoted by the class `ConfEagerSource` The porpuse of this class is to connect to a data source and to populate the data into the configuration class properties.