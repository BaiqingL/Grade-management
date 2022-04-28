package utils;

public class Student {
    private final String name;
    private final int BUID;

    public Student(String name, int BUID) {
        this.name = name;
        this.BUID = BUID;
    }

    public String getName() {
        return name;
    }

    public int getBUID() {
        return BUID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", BUID=" + BUID +
                '}';
    }
}
