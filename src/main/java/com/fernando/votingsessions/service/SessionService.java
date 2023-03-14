package com.fernando.votingsessions.service;

import com.fernando.votingsessions.model.Session;
import com.fernando.votingsessions.model.Vote;
import com.fernando.votingsessions.repository.SessionRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fernando.votingsessions.model.QVote.vote;

@Service
public class SessionService {

    private static final Logger LOG = LogManager.getLogger(SessionService.class);

    private static final Long DEFAULT_PERIOD = 60L;

    private static final int THOUSAND = 1000;

    private static final String QUEUE = "session-result";

    private static final String SIM = "Sim";

    private static final String NAO = "Não";

    private static final String MSG_RESULTADO = "Resultado: (Sim=%s, Não=%s)";

    private final SessionRepository sessionRepository;

    private final ThreadPoolTaskScheduler scheduler;

    private final JmsTemplate jmsTemplate;

    private final EntityManager entityManager;

    public SessionService(SessionRepository sessionRepository, ThreadPoolTaskScheduler scheduler,
                          JmsTemplate jmsTemplate, EntityManager entityManager) {
        this.sessionRepository = sessionRepository;
        this.scheduler = scheduler;
        this.jmsTemplate = jmsTemplate;
        this.entityManager = entityManager;
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session findById(Long id) {
        return sessionRepository.findById(id).get();
    }

    public Session create(Session session) {
        session.setOpeningDate(LocalDateTime.now());

        if (Objects.isNull(session.getPeriod())) {
            session.setPeriod(DEFAULT_PERIOD);
        }

        Session topicSessionSaved = sessionRepository.save(session);

        scheduler.schedule(
                new TopicSessionTask(this, topicSessionSaved),
                new Date(System.currentTimeMillis() + session.getPeriod() * THOUSAND)
        );

        return topicSessionSaved;
    }

    public void closeSession(Long sessionId) {
        Session session = findById(sessionId);
        session.setClosingDate(LocalDateTime.now());
        Session sessionSaved = sessionRepository.save(session);
        sendResultMessage(sessionSaved);
    }

    private void sendResultMessage (Session sessionClosed) throws JmsException {
        jmsTemplate.convertAndSend(QUEUE, sessionClosed.getId());
    }

    @JmsListener(destination = QUEUE)
    private void resultClosedSession(Long sessionId) {
        var query = new JPAQuery(entityManager);
        query.from(vote).where(vote.session.id.eq(sessionId));

        List<Vote> listVotes = query.fetch();

        long yesSummary = listVotes.stream()
                .filter(vote -> vote.getAnswer().equals("S"))
                .collect(Collectors.toList()).stream().count();

        Map<String, Long> result = new HashMap<>();
        result.put(SIM, yesSummary);
        result.put(NAO, (long) listVotes.size() - yesSummary);
        LOG.info(String.format(MSG_RESULTADO, result.get(SIM), result.get(NAO)));
    }
}
