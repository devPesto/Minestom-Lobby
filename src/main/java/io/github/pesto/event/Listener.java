package io.github.pesto.event;

import net.minestom.server.MinecraftServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Interface for event listener implementations
 */
public interface Listener {

    default void register() {
        var eventHandler = MinecraftServer.getGlobalEventHandler();
        var methods = this.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Listen.class)) {
                var annotation = method.getAnnotation(Listen.class);
                var event = annotation.event();

                eventHandler.addListener(event, e -> {
                    try {
                        method.invoke(this, e);
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
    }
}