package my.task.manager;

import my.task.manager.model.TaskElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    Stream<TaskElement> tasks;

    Stream<TaskElement> init() {
        return Stream.of(
                new TaskElement(1L, TaskElement.Status.IN_PROGRESS),
                new TaskElement(2L, TaskElement.Status.OPEN),
                new TaskElement(3L, TaskElement.Status.IN_PROGRESS),
                new TaskElement(4L, TaskElement.Status.CLOSE),
                new TaskElement(5L, TaskElement.Status.OPEN)
        );
    }

    @Test
    @DisplayName("Список задач по статусу")
    @Order(1)
    void test1() {
        tasks = init();
        long[] ids = tasks
                .filter(e -> e.status() == TaskElement.Status.OPEN)
                .mapToLong(TaskElement::id)
                .toArray();
        assertArrayEquals(new long[]{2, 5}, ids);
    }

    @Test
    @DisplayName("Наличия задачи с указанным ID")
    @Order(2)
    void test2() {
        tasks = init();
        assertTrue(tasks.anyMatch(e -> e.id() == 1L));
        tasks = init();
        assertFalse(tasks.anyMatch(e -> e.id() == 6L));
    }

    @Test
    @DisplayName("Получение списка задач в отсортированном по статусу")
    @Order(3)
    void test3() {
        tasks = init();
        long[] ids = tasks
                .sorted(Comparator.comparing(TaskElement::status).thenComparing(TaskElement::id))
                .mapToLong(TaskElement::id)
                .toArray();
        assertArrayEquals(new long[]{2, 5, 1, 3, 4}, ids);
    }

    @Test
    @DisplayName("Подсчет количества задач по определенному статусу")
    @Order(4)
    void test4() {
        tasks = init();
        assertEquals(2, tasks.filter(e -> e.status() == TaskElement.Status.OPEN).count());
        tasks = init();
        assertEquals(2, tasks.filter(e -> e.status() == TaskElement.Status.IN_PROGRESS).count());
        tasks = init();
        assertEquals(1, tasks.filter(e -> e.status() == TaskElement.Status.CLOSE).count());
    }
}
