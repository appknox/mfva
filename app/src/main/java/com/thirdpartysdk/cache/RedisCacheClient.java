package com.thirdpartysdk.cache;

import android.util.Log;

import redis.clients.jedis.Jedis;

public final class RedisCacheClient {

    private RedisCacheClient() {
    }

    public static void connect() {
        Log.d("RedisCacheClient", "Initialising bundled cache client...");
        Jedis jedis = new Jedis("localhost");
        jedis.close();
    }
}
