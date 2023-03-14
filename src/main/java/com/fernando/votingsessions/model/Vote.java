package com.fernando.votingsessions.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "vote")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1463362213995461163L;

    @Id
    @GeneratedValue(generator = "seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="seq_generator", sequenceName = "seq_vote", allocationSize=1)
    private Long id;

    @NotNull(message = "Associate cannot be null")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "i_associate", referencedColumnName = "id")
    private Associate associate;

    @NotNull(message = "Topic session cannot be null")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "i_session", referencedColumnName = "id")
    private Session session;

    @NotNull(message = "Answer cannot be null")
    @Column(name = "answer")
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Vote() {
    }

}
