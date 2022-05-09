package utils;

public class ClassPathTuple {
    private final String className;
    private final String path;

    public ClassPathTuple(String className, String path) {
        this.className = className;
        this.path = path;
    }

    public String getClassName() {
        return className;
    }

    public String getPath() {
        return path;
    }
}
