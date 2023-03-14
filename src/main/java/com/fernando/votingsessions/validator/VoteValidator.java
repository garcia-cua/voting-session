package com.fernando.votingsessions.validator;

import com.fernando.votingsessions.exception.VoteException;
import com.fernando.votingsessions.model.Vote;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Objects;

import static com.fernando.votingsessions.model.QVote.vote;

@Component
public class VoteValidator {

    private final EntityManager entityManager;

    public VoteValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void alreadyVoted(Vote entity) throws VoteException {
        var query = new JPAQuery(entityManager);
        query.from(vote)
                .where(vote.associate.id.eq(entity.getAssociate().getId())
                        .and(vote.session.id.eq(entity.getSession().getId()))
                );

        if (Objects.nonNull(query.fetchFirst())) {
            throw new VoteException("ALREADY_VOTED", "You can only vote once");
        };
    }

    public void isSessionOpen(Vote entity) throws VoteException {
        var query = new JPAQuery(entityManager);
                query.from(vote)
                .where(vote.session.id.eq(entity.getSession().getId())
                        .and(vote.session.closingDate.isNotNull())
                );

        if (Objects.nonNull(query.fetchFirst())) {
            throw new VoteException("SESSION_IS_CLOSED", "Session is closed");
        };
    }
}
