package com.better_mining.player;

import java.util.HashMap;

public class Stats {
    private HashMap<String, Stat> stats;
    private HashMap<String, Integer> statValues;

    public Stats () {
        this.stats = new HashMap<>();
        this.statValues = new HashMap<>();
    }

    public void addSource (String source, HashMap<String, Integer> statMap) {
        removeSource(source);
        for (String stat : statMap.keySet()) {
            if (!stats.containsKey(stat)) {
                stats.put(stat, new Stat());
            }
            stats.get(stat).setSourceStat(source, statMap.get(stat));
            updateStatValues(stat);
        }
    }
    public void removeSource (String source) {
        for (String stat : stats.keySet()) {
            stats.get(stat).removeSource(source);
            updateStatValues(stat);
        }
    }
    private void updateStatValues (String stat) {
        statValues.put(stat, stats.get(stat).getStat());
    }
    public HashMap<String, Integer> getStats () {
        return statValues;
    }
    public int getStat (String stat) {
        return statValues.get(stat);
    }
}
