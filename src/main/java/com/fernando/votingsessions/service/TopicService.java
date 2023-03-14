package com.fernando.votingsessions.service;

import com.fernando.votingsessions.model.Topic;
import com.fernando.votingsessions.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public Topic findById(Long id) {
        return topicRepository.findById(id).get();
    }

    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

}
