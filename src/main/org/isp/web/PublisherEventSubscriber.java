package org.isp.web;

import com.pusher.rest.Pusher;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public final class PublisherEventSubscriber {
    private static final String APP_ID = "599557";
    private static final String APP_KEY = "8ced822414ccc92c355c";
    private static final String APP_SECRET = "d12f344d25ea2c8b7e52";


    public static void triggerEvent(String message) {
        Pusher pusher = new Pusher(APP_ID, APP_KEY, APP_SECRET);
        pusher.setEncrypted(true);
        pusher.setCluster("eu");
        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", message));
    }
}
