import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudent() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String message = new String();

        for (int i = 0; i < 10000; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne > 0.75) {
                sad.addFirst(i);
                ads.addFirst(i);

            } else if (numberBetweenZeroAndOne > 0.5) {
                sad.addLast(i);
                ads.addLast(i);
                message += "addLast(" + i + ")\n";
            } else if (!sad.isEmpty() && !ads.isEmpty()) {
                if (numberBetweenZeroAndOne > 0.25) {
                    Integer a = sad.removeFirst();
                    Integer b = ads.removeFirst();
                    assertEquals(message + "removeFirst(), student was "
                            + a + ", correct was" + b + "\n", a, b);
                    message += "removeFirst()\n";
                } else {
                    Integer a = sad.removeLast();
                    Integer b = ads.removeLast();
                    assertEquals(message + "removeLast(), student was "
                            + a + ", correct was" + b + "\n", a, b);
                    message += "removeLast()\n";
                }
            }
        }
    }
}
