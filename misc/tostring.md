import java.util.*;

public class DataStructureToStringDemo {

    public static void main(String[] args) {

        // Stack
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Stack      : " + stack.toString());

        // ArrayList
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Spring");
        list.add("SQL");
        System.out.println("ArrayList  : " + list.toString());

        // HashSet
        Set<Integer> set = new HashSet<>();
        set.add(10);
        set.add(20);
        set.add(30);
        System.out.println("HashSet    : " + set.toString());

        // Queue
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        System.out.println("Queue      : " + queue.toString());

        // HashMap
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 10);
        map.put("B", 20);
        System.out.println("HashMap    : " + map.toString());

        // Array
        int[] arr = {1, 2, 3};
        System.out.println("Array      : " + Arrays.toString(arr));

        // 2D Array
        int[][] matrix = {
            {1, 2},
            {3, 4}
        };
        System.out.println("2D Array   : " + Arrays.deepToString(matrix));
    }
}