package com.example.bakingappproject;

import android.os.Handler;

import androidx.annotation.Nullable;


class MessageDelayer {

    public static final int DEALY_MILLIS = 3000;

    interface DelayerCallback {
        void onDone(String text);
    }

    static void processMessage(final String message, final DelayerCallback callback, @Nullable final SimpleIdlingResource idlingResource) {

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(message);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DEALY_MILLIS);

    }

}
