package org.nuxeo.ecm.platform.importer.queue.consumer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImportStat implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final Map<String, Long> statMap;

    public ImportStat() {
        statMap = new HashMap<String, Long>();
    }

    public ImportStat(Map<String, Long> initMap) {
        statMap = initMap;
    }

    public void increaseStat(String key, long count) {
        if (statMap.containsKey(key)) {
            long previousCount = statMap.get(key);
            count += previousCount;
        }
        statMap.put(key, count);
    }

    public boolean containsKey(String key) {
        return statMap.containsKey(key);
    }

    public Set<String> keySet() {
        return statMap.keySet();
    }

    public long getStat(String key) {
        return statMap.get(key);
    }

    public void merge(ImportStat other) {
        for (String key : other.keySet()) {
            if (statMap.containsKey(key)) {
                increaseStat(key, other.getStat(key));
            } else {
                statMap.put(key, other.getStat(key));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Import stat :\n");
        for (String key : statMap.keySet()) {
            sb.append("[");
            sb.append(key);
            sb.append("]: ");
            sb.append(statMap.get(key));
            sb.append("\n");
        }

        return sb.toString();
    }

    public Map<String,Long> getStatMap()
    {
        return new HashMap<String, Long>(statMap);
    }
}
