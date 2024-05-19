package net.ryian.flow.common.utils;

import java.util.UUID;

/**
 * @author allenwc
 * @date 2024/5/7 09:17
 */
public class UUIDUtil {

    private UUIDUtil() {
    }

    public static String gen() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
