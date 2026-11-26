package com.thirdpartysdk.cache;

import android.util.Log;

public final class RedisCacheClient {

    private RedisCacheClient() {
    }

    public static void connect() {
        Log.d("RedisCacheClient", "Initialising bundled cache client...");
    }
}
