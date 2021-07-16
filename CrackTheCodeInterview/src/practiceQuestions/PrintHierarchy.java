package practiceQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
Input:
mammal, dog, poodle
mammal, dog, bulldog
mammal, dog, terrier
mammal, cat

Output:
mammal
	dog
		poodle
		bulldog
		terrier
	cat
*/
public class PrintHierarchy {

    List<Node> roots;

    public PrintHierarchy() {
        roots = new LinkedList<>();
    }

    public static void main(String[] args) {
        List<String[]> inputs = new LinkedList<>(
                Arrays.asList(new String[] { "mammal", "dog", "poodle" }, new String[] { "mammal", "cat" },
                        new String[] { "mammal", "dog", "bulldog" }, new String[] { "mammal", "dog", "terrier" }));

        PrintHierarchy result = new PrintHierarchy();
        result.parse(inputs);
        System.out.println(result);
    }

    public void parse(List<String[]> inputs) {
        for (String[] input : inputs) {
            List<Node> current = roots;
            for (int i = 0; i < input.length; i++) {
                Node tmp = new Node(input[i]);
                if (current.contains(tmp)) {
                    current = current.get(current.indexOf(tmp)).children;
                } else {
                    current.add(tmp);
                    current = tmp.children;
                }
            }
        }
    }

    @Override
    public String toString() {
        for (Node root : roots) {
            printChildren(root, 0);
        }

        return "";
    }

    public void printChildren(Node node, int level) {
        String prefix = "";
        for (int i = 0; i < level; i++) {
            prefix += "\t";
        }
        System.out.println(prefix + node.value);
        for (Node n : node.children) {
            printChildren(n, level + 1);
        }
    }
}

class Node {
    String value;
    List<Node> children;

    public Node(String value) {
        this.value = value;
        children = new ArrayList<>();
    }

    @Override
    public boolean equals(Object node) {
        return value.equals(((Node) node).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

/*
 * Input: dog, poodle mammal, dog mammal, cat dog, bulldog dog, terrier
 * 
 * Output: mammal dog poodle bulldog terrier cat
 */
/*
 * public static void main(String[] args) { String[][] strs = { { "dog",
 * "poodle" }, { "mammal", "dog" }, { "mammal", "cat" }, { "dog", "bulldog" }, {
 * "dog", "terrier" } }; solve(strs); }
 * 
 * private static void solve(String[][] strs) { Map<String, Set<String>> map =
 * new HashMap<>(); Map<String, Integer> cntMap = new HashMap<>(); for(String[]
 * ss : strs) { map.putIfAbsent(ss[0], new HashSet<>());
 * map.get(ss[0]).add(ss[1]); cntMap.putIfAbsent(ss[0], 0); cntMap.put(ss[1],
 * cntMap.getOrDefault(ss[1], 0) + 1); } for(Map.Entry<String, Integer> e :
 * cntMap.entrySet()) { if(e.getValue() == 0){ dfs(map, e.getKey(), ""); } } }
 * 
 * private static void dfs(Map<String, Set<String>> map, String key, String
 * space) { System.out.println(space + key); for(String nei :
 * map.getOrDefault(key, new HashSet<>())) { dfs(map, nei, space + "    "); } }
 */