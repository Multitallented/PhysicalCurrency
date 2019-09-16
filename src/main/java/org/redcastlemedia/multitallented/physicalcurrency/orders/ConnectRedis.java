package org.redcastlemedia.multitallented.physicalcurrency.orders;

import redis.clients.jedis.Jedis;

public final class ConnectRedis {
    private ConnectRedis() {

    }
    public static Jedis execute() {
        return new Jedis();
    }
}
