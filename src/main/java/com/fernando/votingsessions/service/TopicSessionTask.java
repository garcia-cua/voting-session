package com.fernando.votingsessions.service;

import com.fernando.votingsessions.model.Session;

public class TopicSessionTask implements Runnable {

    private final Session topicSession;

    private final SessionService topicSessionService;

    public TopicSessionTask(SessionService topicSessionService, Session topicSession) {
        super();
        this.topicSessionService = topicSessionService;
        this.topicSession = topicSession;
    }

    @Override
    public void run() {
        topicSessionService.closeSession(topicSession.getId());
    }

}
