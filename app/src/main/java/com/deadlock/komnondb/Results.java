package com.deadlock.komnondb;

import java.util.HashMap;
import java.util.Map;

public class Results {

    private float hwResDb, hwLitrDb, cwResDb, cwLitrDb, wofResDb, wofLitrDb, elResDb, allResDb;

    public Results() {

    }

    public Results(float hwResDb, float hwLitrDb, float cwResDb, float cwLitrDb,
                   float wofResDb, float wofLitrDb, float elResDb, float allResDb) {
        this.hwResDb = hwResDb;
        this.hwLitrDb = hwLitrDb;
        this.cwResDb = cwResDb;
        this.cwLitrDb = cwLitrDb;
        this.wofResDb = wofResDb;
        this.wofLitrDb = wofLitrDb;
        this.elResDb = elResDb;
        this.allResDb = allResDb;
    }

    public Map<String, Object> ResultsToMap() {
        HashMap<String, Object> hashResults = new HashMap<>();
        hashResults.put("Горячая вода", hwResDb);
        hashResults.put("Горячая вода*", hwLitrDb);
        hashResults.put("Холодная вода", cwResDb);
        hashResults.put("Холодная вода*", cwLitrDb);
        hashResults.put("Электричество", elResDb);
        hashResults.put("Водоотвод", wofResDb);
        hashResults.put("Водоотвод*", wofLitrDb);
        hashResults.put("Всего", allResDb);
        return hashResults;
    }


}
