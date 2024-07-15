import java.util.*;

public class Main {

    private static final String TEXT_SAMPLE = "Time flies like an arrow";

    private static final LexicalCategory BEGINNING = new LexicalCategory("Beginning");
    private static final LexicalCategory DET = new LexicalCategory("Det");
    private static final LexicalCategory NOUN = new LexicalCategory("Noun");
    private static final LexicalCategory VERB = new LexicalCategory("Verb");
    private static final LexicalCategory PARTICLE = new LexicalCategory("Particle");

    private static final Word WORD_BEGINNING = new Word("", BEGINNING);
    private static final Word WORD_DET_AN = new Word("an", DET);
    private static final Word WORD_NOUN_AN = new Word("an", NOUN);
    private static final Word WORD_NOUN_FLIES = new Word("flies", NOUN);
    private static final Word WORD_NOUN_TIMES = new Word("time", NOUN);
    private static final Word WORD_NOUN_ARROW = new Word("arrow", NOUN);
    private static final Word WORD_NOUN_LIKE = new Word("like", NOUN);
    private static final Word WORD_VERB_FLIES = new Word("flies", VERB);
    private static final Word WORD_VERB_TIMES = new Word("time", VERB);
    private static final Word WORD_VERB_LIKE = new Word("like", VERB);
    private static final Word WORD_PARTICLE_LIKE = new Word("like", PARTICLE);

    private static final LexicalCategoryBiGram BEGINNING_DET = new LexicalCategoryBiGram(BEGINNING, DET);
    private static final LexicalCategoryBiGram BEGINNING_NOUN = new LexicalCategoryBiGram(BEGINNING, NOUN);
    private static final LexicalCategoryBiGram DET_NOUN = new LexicalCategoryBiGram(DET, NOUN);
    private static final LexicalCategoryBiGram NOUN_VERB = new LexicalCategoryBiGram(NOUN, VERB);
    private static final LexicalCategoryBiGram NOUN_NOUN = new LexicalCategoryBiGram(NOUN, NOUN);
    private static final LexicalCategoryBiGram NOUN_PARTICLE = new LexicalCategoryBiGram(NOUN, PARTICLE);
    private static final LexicalCategoryBiGram VERB_NOUN = new LexicalCategoryBiGram(VERB, NOUN);
    private static final LexicalCategoryBiGram VERB_DET = new LexicalCategoryBiGram(VERB, DET);
    private static final LexicalCategoryBiGram VERB_PARTICLE = new LexicalCategoryBiGram(VERB, PARTICLE);
    private static final LexicalCategoryBiGram PARTICLE_DET = new LexicalCategoryBiGram(PARTICLE, DET);
    private static final LexicalCategoryBiGram PARTICLE_NOUN = new LexicalCategoryBiGram(PARTICLE, NOUN);

    public static void main(String[] args) {
        HashSet<Word> dictionary = new HashSet<>();
        dictionary.add(WORD_DET_AN);
        dictionary.add(WORD_NOUN_AN);
        dictionary.add(WORD_NOUN_FLIES);
        dictionary.add(WORD_VERB_FLIES);
        dictionary.add(WORD_NOUN_TIMES);
        dictionary.add(WORD_VERB_TIMES);
        dictionary.add(WORD_NOUN_ARROW);
        dictionary.add(WORD_NOUN_LIKE);
        dictionary.add(WORD_VERB_LIKE);
        dictionary.add(WORD_PARTICLE_LIKE);

        // 単語出力確率
        HashMap<Word, Double> probWordOutput = new HashMap<>();
        probWordOutput.put(WORD_DET_AN, 0.36);
        probWordOutput.put(WORD_NOUN_AN, 0.001);
        probWordOutput.put(WORD_NOUN_FLIES, 0.025);
        probWordOutput.put(WORD_VERB_FLIES, 0.076);
        probWordOutput.put(WORD_NOUN_TIMES, 0.063);
        probWordOutput.put(WORD_VERB_TIMES, 0.012);
        probWordOutput.put(WORD_NOUN_ARROW, 0.076);
        probWordOutput.put(WORD_NOUN_LIKE, 0.012);
        probWordOutput.put(WORD_VERB_LIKE, 0.1);
        probWordOutput.put(WORD_PARTICLE_LIKE, 0.068);

        // バイグラムの遷移確率
        HashMap<LexicalCategoryBiGram, Double> probBiGramTransition = new HashMap<>();
        probBiGramTransition.put(BEGINNING_DET, 0.71);
        probBiGramTransition.put(BEGINNING_NOUN, 0.29);
        probBiGramTransition.put(DET_NOUN, 1.0);
        probBiGramTransition.put(NOUN_VERB, 0.32);
        probBiGramTransition.put(NOUN_NOUN, 0.1);
        probBiGramTransition.put(NOUN_PARTICLE, 0.33);
        probBiGramTransition.put(VERB_NOUN, 0.37);
        probBiGramTransition.put(VERB_DET, 0.54);
        probBiGramTransition.put(VERB_PARTICLE, 0.09);
        probBiGramTransition.put(PARTICLE_DET, 0.62);
        probBiGramTransition.put(PARTICLE_NOUN, 0.38);

        // 確率を最大化する品詞列を求める
        String[] text = TEXT_SAMPLE.toLowerCase().split(" ");
        List<Node> currentNodes = new ArrayList<>();
        currentNodes.add(new Node(WORD_BEGINNING, 1.0));
        int count = 0;

        for (String phrase : text) {
            List<Node> next = new ArrayList<>();
            for (Word word : dictionary) {
                if (!word.getWord().equals(phrase)) {
                    continue;
                }
                for (Node node : currentNodes) {
                    LexicalCategoryBiGram biGram = new LexicalCategoryBiGram(node.getWord().getCategory(), word.getCategory());
                    double transitionProbability = probBiGramTransition.getOrDefault(biGram, 0.0);
                    double wordProbability = probWordOutput.get(word);
                    Node newNode = new Node(word, node.getMaxProbability() * transitionProbability * wordProbability);
                    node.getNext().add(newNode);
                    newNode.getPrevious().add(node);
                    next.add(newNode);
                    count++;
                }
            }
            currentNodes = next;
        }
        System.out.println(count);

        // 結果を表示
        Stack<Node> best = new Stack<>();
        Node current = currentNodes.stream().max(Comparator.comparingDouble(Node::getMaxProbability)).orElseThrow();
        while (!current.getPrevious().isEmpty()) {
            best.push(current);
            current = current.getPrevious().stream().max(Comparator.comparingDouble(Node::getMaxProbability)).orElseThrow();
        }
        while (!best.isEmpty()) {
            Node node = best.pop();
            System.out.println(node.toString());
        }
    }
}
