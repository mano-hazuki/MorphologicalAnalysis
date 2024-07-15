import java.util.Objects;

public class LexicalCategory {

    private final String name;

    public LexicalCategory(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LexicalCategory other)) {
            return false;
        }
        return name.equals(other.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "LexicalCategory[" +
                "name=" + name + ']';
    }
}
