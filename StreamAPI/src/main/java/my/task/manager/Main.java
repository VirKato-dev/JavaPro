package my.task.manager;

import my.task.manager.dao.TaskDAO;
import my.task.manager.model.TaskElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<TaskElement> tasks = new ArrayList<>();
        Random random = new Random();
        for (long i = 1; i <= 5; i++) {
            int index = random.nextInt(TaskElement.Status.values().length);
            TaskElement.Status status = TaskElement.Status.values()[index];
            TaskElement task = new TaskElement(i, status);
            tasks.add(task);
        }
        TaskDAO dao = new TaskDAO(tasks);

        System.out.println("Задач в списке ожидания: " + dao.countByStatus(TaskElement.Status.OPEN));
        print(dao.getAllByStatus(TaskElement.Status.OPEN));
        System.out.println("\nЗадача с id=2 существует:\t" + dao.isExists(2L));
        System.out.println("Задача с id=9 существует:\t" + dao.isExists(9L));
        System.out.println();
        print(dao.getTasksSorted());
    }

    private static void print(List<TaskElement> tasks) {
        tasks.forEach(System.out::println);
    }
}