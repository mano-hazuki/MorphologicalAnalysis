import java.util.Objects;

public class Word {

    private final String word;
    private final LexicalCategory category;

    public Word(String word, LexicalCategory category) {
        this.word = word;
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Word other)) {
            return false;
        }
        return other.word.equals(this.word) && other.category.equals(this.category);
    }

    public String getWord() {
        return word;
    }

    public LexicalCategory getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, category);
    }

    @Override
    public String toString() {
        return word + " / " + category.getName();
    }
}
