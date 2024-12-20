package kr.hbcho90.teststudy.common.util;

import java.util.UUID;

public class IdGenerator {

    public static String generateId() {
        return UUID.randomUUID().toString().substring(0, 17).replace("-", "");
    }

}
