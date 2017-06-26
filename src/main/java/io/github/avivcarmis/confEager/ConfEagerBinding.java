package io.github.avivcarmis.confEager;

import java.util.List;

/**
 * Created by Mamot on 6/25/2017.
 */
class ConfEagerBinding {

    final ConfEager _confEagerObject;

    final List<ConfEagerProperty> _properties;

    ConfEagerBinding(ConfEager confEagerObject) {
        _confEagerObject = confEagerObject;
        _properties = ConfEagerReflectionUtils.findProperties(confEagerObject);
    }

}
