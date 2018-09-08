package com.deadlock.komnondb;

import java.util.HashMap;
import java.util.Map;

public class Counters {

    private float hwDb, cwDb, t1Db, t2Db, t3Db;

    public Counters() {

    }

    public Counters(int hwDb, int cwDb, int t1Db, int t2Db, int t3Db) {
        this.hwDb = hwDb;
        this.cwDb = cwDb;
        this.t1Db = t1Db;
        this.t2Db = t2Db;
        this.t3Db = t3Db;
    }

    public Map<String, Object> counterToMap() {
        HashMap<String, Object> hashCounter = new HashMap<>();
        hashCounter.put("Горячая вода", hwDb);
        hashCounter.put("Холодная вода", cwDb);
        hashCounter.put("T1", t1Db);
        hashCounter.put("T2", t2Db);
        hashCounter.put("T3", t3Db);
        return hashCounter;
    }
}
