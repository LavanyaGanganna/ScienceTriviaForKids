package my.awesome.lavanya.sciencetriviaforkids;

/**
 * Created by lavanya on 2/26/17.
 */

public class QuizObject {
    String question;
    String choicea;
    String choiceb;
    String choicec;
    String choiced;
    String correct;
    public QuizObject(String question,String choicea,String choiceb,String choicec,String choiced,String correct){
        this.question=question;
        this.choicea=choicea;
        this.choiceb=choiceb;
        this.choicec=choicec;
        this.choiced=choiced;
        this.correct=correct;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoicea() {
        return choicea;
    }

    public String getChoiceb() {
        return choiceb;
    }

    public String getChoicec() {
        return choicec;
    }

    public String getChoiced() {
        return choiced;
    }

    public String getCorrect() {
        return correct;
    }
}
