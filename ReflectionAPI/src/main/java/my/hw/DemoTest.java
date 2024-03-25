package my.hw;

import my.hw.annotations.After;
import my.hw.annotations.Before;
import my.hw.annotations.Test;

/**
 * Класс для проверки работоспособности собственных аннотаций
 */
@Test
public class DemoTest {

    private int global;

    @Before
    public void setUp() {
        print("---\nBefore test (" + global + ")");
    }

    @After
    public void complete() {
        print("After test (" + global + ")\n---");
    }

    @Test(order = 2)
    public void test1() {
        global++;
        print("Test method 1");
    }

    @Test(order = 1)
    public void test2() {
        print("Test method2");
        global++;
        throw new RuntimeException("Unexpected behavior!");
    }

    // Не является тестом из-за private
    private void print(String message) {
        System.out.println(message);
    }

}
