package com.fernando.votingsessions.resource;

import com.fernando.votingsessions.model.Topic;
import com.fernando.votingsessions.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/topics")
public class TopicResource {

    final private TopicService topicService;

    public TopicResource(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<Topic>> findAll() {
        return ResponseEntity.ok(topicService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Topic> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Topic> create(@RequestBody Topic topic) {
        return ResponseEntity.ok(topicService.create(topic));
    }

}
