package com.floatingmuseum.mocloud.data.bus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Floatingmuseum on 2017/2/16.
 */

public class EventBusManager {

    private static EventBus bus = EventBus.getDefault();

    public static void register(Object object) {
        if (!bus.isRegistered(object)) {
            bus.register(object);
        }
    }

    public static void unRegister(Object object) {
        if (bus.isRegistered(object)) {
            bus.unregister(object);
        }
    }
}
