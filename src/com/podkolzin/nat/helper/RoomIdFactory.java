package com.podkolzin.nat.helper;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 10.03.14
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public final class RoomIdFactory {
    private static Integer currentId = 0;
    public static int nextId() {
         synchronized (currentId) {
             return ++currentId;
         }
    }
}
