import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;

public class Node {

    private final HashSet<Node> next;
    private final HashSet<Node> previous;

    private final Word word;

    private final double maxProbability;

    public Node(Word word, double maxProbability) {
        this.word = word;
        this.maxProbability = maxProbability;
        this.next = new HashSet<>();
        this.previous = new HashSet<>();
    }

    public HashSet<Node> getNext() {
        return next;
    }

    public HashSet<Node> getPrevious() {
        return previous;
    }

    public Word getWord() {
        return word;
    }

    public double getMaxProbability() {
        return maxProbability;
    }

    @Override
    public String toString() {
        return word + ", " +
                "確率 = " + BigDecimal.valueOf(maxProbability).multiply(BigDecimal.valueOf(100.0)).setScale(10, RoundingMode.HALF_EVEN) + "%";
    }
}
