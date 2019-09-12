package org.redcastlemedia.multitallented.physicalcurrency.orders;

import redis.clients.jedis.Jedis;

public class ConnectRedis {
    public static Jedis execute() {
        return new Jedis();
    }
}
