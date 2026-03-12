package com.better_mining.player;

import java.util.HashMap;
import java.util.Set;

public class Stat {
    private int stat;
    private HashMap<String, Integer> sourceStat;
    public Stat () {
        this.stat = 0;
        this.sourceStat = new HashMap<>();
    }

    public int getStat() {
        return stat;
    }
    public Set<String> getSource () {
        return sourceStat.keySet();
    }
    public boolean hasSource (String source) {
        return sourceStat.containsKey(source);
    }
    public void removeSource (String source) {
        sourceStat.remove(source);
        updateStat();
    }
    public void setSourceStat (String source, int stat) {
        sourceStat.put(source, stat);
        updateStat();
    }

    private void updateStat () {
        stat = 0;
        for (String source : sourceStat.keySet()) {
            stat += sourceStat.get(source);
        }
    }
}
