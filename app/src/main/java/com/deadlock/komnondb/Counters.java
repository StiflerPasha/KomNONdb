package com.deadlock.komnondb;

import java.util.HashMap;
import java.util.Map;

public class Counters {

    float hwDb, cwDb, t1Db, t2Db, t3Db;

    public Counters() {

    }

    public Counters(int hwDb, int cwDb, int t1Db, int t2Db, int t3Db) {
        this.hwDb = hwDb;
        this.cwDb = cwDb;
        this.t1Db = t1Db;
        this.t2Db = t2Db;
        this.t3Db = t3Db;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> hashResult = new HashMap<>();
        hashResult.put("Горячая вода", hwDb);
        hashResult.put("Холодная вода", cwDb);
        hashResult.put("T1", t1Db);
        hashResult.put("T2", t2Db);
        hashResult.put("T3", t3Db);
        return hashResult;
    }
}
