package com.meehoo.biz.common.util;

public class GuuidUtil {

    /**
     * 单例模式创建雪花算法对象 *
     */
    private enum SnowFlakeSingleton {
        Singleton;
        private SnowflakeIdWorker snowflakeIdWorker;

        SnowFlakeSingleton() {
            snowflakeIdWorker = new SnowflakeIdWorker(2, 1, 1606660865000L, 1, 1);
        }

        public SnowflakeIdWorker getInstance() {
            return snowflakeIdWorker;
        }}

    public static long getUUID() {
        return SnowFlakeSingleton.Singleton.getInstance().nextId();
    }
}
