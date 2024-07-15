import java.util.Objects;

public class LexicalCategoryBiGram {

    private final LexicalCategory category0;
    private final LexicalCategory category1;

    public LexicalCategoryBiGram(LexicalCategory category0, LexicalCategory category1) {
        this.category0 = category0;
        this.category1 = category1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LexicalCategoryBiGram other)) {
            return false;
        }
        return category0().equals(other.category0()) && category1().equals(other.category1());
    }

    public LexicalCategory category0() {
        return category0;
    }

    public LexicalCategory category1() {
        return category1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category0, category1);
    }

    @Override
    public String toString() {
        return "LexicalCategoryBiGram[" +
                "category0=" + category0 + ", " +
                "category1=" + category1 + ']';
    }
}
