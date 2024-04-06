package my.task.manager.model;

public record TaskElement(Long id, Status status) {
    public enum Status {
        OPEN("ОТКРЫТА"), IN_PROGRESS("В РАБОТЕ"), CLOSE("ЗАКРЫТА");
        final String ru;

        Status(String ru) {
            this.ru = ru;
        }
    }

    @Override
    public String toString() {
        return String.format("Задача: id=%d\tстатус='%s'", id, status.ru);
    }
}
