package utils;

public class Student {
    private String name;
    private int BUID;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setBUID(int BUID) {
        this.BUID = BUID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", BUID=" + BUID +
                '}';
    }
}
