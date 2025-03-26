package net.superscary.toasts;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ToastManager {
    private static final ToastManager INSTANCE = new ToastManager();
    private final Map<String, CustomToast> toastRegistry = new HashMap<>();
    private final Queue<CustomToast> toastQueue = new ConcurrentLinkedQueue<>();
    private CustomToast currentToast;
    private long currentToastStartTime;
    private boolean isShowingToast;

    private ToastManager() {}

    public static ToastManager getInstance() {
        return INSTANCE;
    }

    public void registerToast(CustomToast toast) {
        toastRegistry.put(toast.getId(), toast);
    }

    public void show(CustomToast toast) {
        toastQueue.offer(toast);
        if (!isShowingToast) {
            showNextToast();
        }
    }

    public void show(String id) {
        CustomToast toast = toastRegistry.get(id);
        if (toast != null) {
            show(toast);
        }
    }

    public void showNextToast() {
        if (toastQueue.isEmpty()) {
            isShowingToast = false;
            return;
        }

        currentToast = toastQueue.poll();
        currentToastStartTime = System.currentTimeMillis();
        isShowingToast = true;
        // Platform-specific rendering will be handled by the platform-specific classes
    }

    public void update() {
        if (!isShowingToast || currentToast == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - currentToastStartTime >= currentToast.getDuration() * 50L) {
            showNextToast();
        }
    }

    public CustomToast getCurrentToast() {
        return currentToast;
    }

    public boolean isShowingToast() {
        return isShowingToast;
    }

    public Map<String, CustomToast> getToastRegistry() {
        return new HashMap<>(toastRegistry);
    }
} 