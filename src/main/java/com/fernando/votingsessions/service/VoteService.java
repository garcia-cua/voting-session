package com.fernando.votingsessions.service;

import com.fernando.votingsessions.exception.VoteException;
import com.fernando.votingsessions.model.Vote;
import com.fernando.votingsessions.repository.VoteRepository;
import com.fernando.votingsessions.validator.VoteValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteValidator voteValidator;

    public VoteService(VoteValidator voteValidator, VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
        this.voteValidator = voteValidator;
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public Vote findById(Long id) {
        return voteRepository.findById(id).get();
    }

    public Vote create(Vote vote) throws VoteException {
        voteValidator.isSessionOpen(vote);
        voteValidator.alreadyVoted(vote);
        return voteRepository.save(vote);
    }

}
