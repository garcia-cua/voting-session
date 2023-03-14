package com.fernando.votingsessions.resource;

import com.fernando.votingsessions.model.Session;
import com.fernando.votingsessions.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/sessions")
public class SessionResource {

    final private SessionService topicSessionService;

    public SessionResource(SessionService topicSessionService) {
        this.topicSessionService = topicSessionService;
    }

    @GetMapping
    public ResponseEntity<List<Session>> findAll() {
        return ResponseEntity.ok(topicSessionService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Session> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(topicSessionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Session> create(@RequestBody Session topicSession) {
        return ResponseEntity.ok(topicSessionService.create(topicSession));
    }

}
