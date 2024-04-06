package my.task.manager.dao;

import my.task.manager.model.TaskElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskDAO {
    private final List<TaskElement> tasks = new ArrayList<>();

    public TaskDAO(List<TaskElement> tasks) {
        this.tasks.addAll(tasks);
    }

    public List<TaskElement> getAllByStatus(TaskElement.Status status) {
        return tasks.stream()
                .filter(e -> e.status() == status)
                .collect(Collectors.toList());
    }

    public boolean isExists(Long id) {
        return tasks.stream().anyMatch(t -> Objects.equals(t.id(), id));
    }

    public List<TaskElement> getTasksSorted() {
        return tasks.stream()
                .sorted(Comparator.comparingLong(TaskElement::id))
                .sorted(Comparator.comparingInt(o -> o.status().ordinal()))
                .collect(Collectors.toList());
    }

    public long countByStatus(TaskElement.Status status) {
        return tasks.stream()
                .filter(task -> task.status() == status)
                .count();
    }
}
