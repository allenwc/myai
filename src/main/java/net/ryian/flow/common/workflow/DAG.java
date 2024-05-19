package net.ryian.flow.common.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author allenwc
 * @date 2024/5/5 10:35
 */
public class DAG {
    private Set<String> nodes = new HashSet<>();
    private Map<String, Set<String>> edges = new HashMap<>();

    public void addNode(String node) {
        nodes.add(node);
        edges.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(String start, String end) {
        addNode(start);
        addNode(end);
        edges.get(start).add(end);
    }

    public List<String> getParents(String node) {
        List<String> parents = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : edges.entrySet()) {
            if (entry.getValue().contains(node)) {
                parents.add(entry.getKey());
            }
        }
        return parents;
    }

    public List<String> getChildren(String node) {
        return new ArrayList<>(edges.get(node));
    }

    public Set<String> getAllNodes() {
        return nodes;
    }

    public List<String> topologicalSort() {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String node : nodes) {
            inDegree.put(node, 0);
        }

        for (Set<String> ends : edges.values()) {
            for (String end : ends) {
                inDegree.put(end, inDegree.get(end) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String node = queue.poll();
            result.add(node);
            for (String child : edges.get(node)) {
                inDegree.put(child, inDegree.get(child) - 1);
                if (inDegree.get(child) == 0) {
                    queue.add(child);
                }
            }
        }

        if (result.size() != nodes.size()) {
            throw new IllegalArgumentException("The graph contains cycles");
        }

        return result;
    }
}
