package com.codessquad.qna.domain;

import com.codessquad.qna.exception.IllegalUserAccessException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("id DESC ")
    private List<Answer> answerList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DisplayStatus status = DisplayStatus.OPEN;

    private String title;
    private String contents;
    @Column(columnDefinition = "INTEGER CHECK (COUNT_OF_ANSWER>=0)")
    private int countOfAnswer = 0;

    public void addAnswer(Answer answer) {
        answerList.add(answer);
        countOfAnswer++;
    }

    public void deleteAnswer(Answer answer) {
        answerList.remove(answer);
        countOfAnswer--;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void changeWriter(User writer) {
        this.writer = writer;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public void questionUpdate(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public void checkSameUserFromOpenAnswer() {
        for (Answer answer : answerList) {
            if (answer.getStatus() == DisplayStatus.OPEN) {
                answer.getWriter().checkSameUser(writer.getId());
            }
        }
    }

    public void changeStatus(DisplayStatus displayStatus) {
        this.status = displayStatus;
    }

    public void checkSameUser(Long newId) {
        if (writer.getId() != newId) {
            throw new IllegalUserAccessException("????????? ????????? ?????? ??????");
        }
    }
}
