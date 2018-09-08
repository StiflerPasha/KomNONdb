package com.deadlock.komnondb;

import java.util.HashMap;
import java.util.Map;

public class Results {

    private float hwResDb, cwResDb, wofResDb, elResDb, allResDb;

    public Results() {

    }

    public Results(float hwResDb, float cwResDb, float wofResDb, float elResDb, float allResDb) {
        this.hwResDb = hwResDb;
        this.cwResDb = cwResDb;
        this.wofResDb = wofResDb;
        this.elResDb = elResDb;
        this.allResDb = allResDb;
    }

    public Map<String, Object> ResultsToMap() {
        HashMap<String, Object> hashResults = new HashMap<>();
        hashResults.put("Горячая вода", hwResDb);
        hashResults.put("Холодная вода", cwResDb);
        hashResults.put("Электричество", elResDb);
        hashResults.put("Водоотвод", wofResDb);
        hashResults.put("Всего", allResDb);
        return hashResults;
    }


}
