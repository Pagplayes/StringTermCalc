package DiffentStuff;

public class Main {
    public static void main(String[] args) {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();

        // Test: Adding elements
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("After adding elements: " + list);

        // Test: Removing elements by index
        list.remove(1);  // should remove "20"
        System.out.println("After removing element at index 1: " + list.toString());

        // Test: Adding more elements
        list.add(4);
        list.add(5);
        System.out.println("After adding more elements: " + list);

        // Test: Removing first element
        list.remove(0);
        System.out.println("After removing element '0': " + list);
        list.add(6);
        list.add(7);
        list.remove(2);
        System.out.println("After removing element on index '2'"+list);
        // Test: Checking empty case
        list.clear();
        System.out.println("After clearing the list: " + list);
    }
}