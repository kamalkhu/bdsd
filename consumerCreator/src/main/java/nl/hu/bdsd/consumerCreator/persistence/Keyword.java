package nl.hu.bdsd.consumerCreator.persistence;

public class Keyword {

    private String word;
    private Double score;

    public Document(String word, Double score) {
        this.word = word;
        this.score = score;
    }

    public String getWord() { return this.word; }

    public Double getScore() { return this.score; }
}
