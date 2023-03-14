package com.fernando.votingsessions.resource;

import com.fernando.votingsessions.exception.VoteException;
import com.fernando.votingsessions.model.Vote;
import com.fernando.votingsessions.service.VoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/votes")
public class VoteResource {

    private static final Logger LOG = LogManager.getLogger(VoteResource.class);

    private static final String FORMAT_ERROR_MSG = "%s: %s";

    final private VoteService voteService;

    public VoteResource(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<List<Vote>> findAll() {
        return ResponseEntity.ok(voteService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Vote> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(voteService.findById(id));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Vote vote) {
        Vote voteSaved;

        try {
            voteSaved = voteService.create(vote);
        } catch (VoteException e) {
            String message = String.format(FORMAT_ERROR_MSG, e.getErrorCode(), e.getMessage());
            LOG.error(e);
            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.ok(voteSaved);
    }

}
