package net.superscary.toasts;

public class Toasts {
    private Toasts() {} // Prevent instantiation

    public static CustomToast.Builder builder() {
        return new CustomToast.Builder();
    }

    public static void show(CustomToast toast) {
        ToastManager.getInstance().show(toast);
    }

    public static void show(String id) {
        ToastManager.getInstance().show(id);
    }

    public static void register(CustomToast toast) {
        ToastManager.getInstance().registerToast(toast);
    }
} 