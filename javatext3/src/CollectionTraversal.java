import java.util.*;

public class CollectionTraversal {
    public static void main(String[] args) {
        // 将数组类型改为 Integer[]
        Integer[] array = {1, 2, 3, 4, 5}; // 使用包装类
        List<String> list = Arrays.asList("A", "B", "C");
        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        map.put("Two", 2);

        foreach(array, list, map);
        Plus_foreach(array, list, map);
        interator(array, list, map);
    }

    public static void foreach(Integer[] arry, List<String> li, Map<String, Integer> m) {
        for (int i=0;i<arry.length;i++) {
            System.out.print(arry[i]+" ");
        }
        System.out.println();

        for (int i = 0; i < li.size(); i++) {
            System.out.print(li.get(i) + " ");
        }
        System.out.println();

        for (String key : m.keySet()) {
            Integer value = m.get(key); // 自动解包
            System.out.println(key + ": " + value + " ");
        }
        System.out.println();
    }

    public static void interator(Integer[] arry, List<String> list, Map<String, Integer> map) {
        for (Integer num : arry) { // 使用自动解包
            System.out.print(num + " ");
        }
        System.out.println();

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        Iterator<Map.Entry<String, Integer>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, Integer> entry = mapIterator.next();
            System.out.print(entry.getKey() + ": " + entry.getValue() + " "); // 自动解包
        }
        System.out.println();
    }

    public static void Plus_foreach(Integer[] arry, List<String> li, Map<String, Integer> m) {
        for (Integer num : arry) { // 使用自动解包
            System.out.print(num + " ");
        }
        System.out.println();

        for (Map.Entry<String, Integer> num : m.entrySet()) {
            String key = num.getKey();
            Integer in = num.getValue(); // 自动解包
            System.out.println(key + ":" + in + " ");
        }
        System.out.println();
    }
}
