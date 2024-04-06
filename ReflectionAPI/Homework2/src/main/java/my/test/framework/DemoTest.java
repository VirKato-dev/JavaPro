package my.test.framework;

import my.test.framework.annotations.*;

/**
 * Класс для проверки работоспособности собственных аннотаций
 */
@Test
public class DemoTest {

    private int global;

    @BeforeSuite
    public void initTest() {
        print("before all");
    }

    @AfterSuite
    public void finishTest() {
        print("finish");
    }

    @Before
    public void beforeEach() {
        print("---\nBefore test (" + global + ")");
    }

    @After
    public void afterEach() {
        print("After test (" + global + ")\n---");
    }

    @Test(priority = 2)
    public void test1() {
        global++;
        print("Test method 1");
    }

    @Test(priority = 1)
    public void test2() {
        print("Test method2");
        global++;
        throw new RuntimeException("Unexpected behavior!");
    }

    @ThrowsException(exception = IllegalStateException.class)
    public void testWithException() {
        print("Test with exception");
        throw new IllegalStateException();
    }

    // Не является тестом из-за private
    private void print(String message) {
        System.out.println(message);
    }

}
