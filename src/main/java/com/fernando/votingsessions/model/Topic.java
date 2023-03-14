package com.fernando.votingsessions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "topic")
public class Topic implements Serializable {
    private static final long serialVersionUID = -353635619496274756L;

    @GeneratedValue
    @Id
    private Long id;

    @NotNull(message = "Description cannot be null")
    @Column(name = "description")
    private String description;

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic(TopicBuilder topicBuilder) {
        this.id = topicBuilder.id;
        this.description = topicBuilder.description;
    }

    public static class TopicBuilder {
        private Long id;
        private String description;

        public TopicBuilder() {
            from(new Topic());
        }

        public TopicBuilder from(Topic topic) {
            return this.id(topic.getId())
                    .description(topic.getDescription());
        }

        private TopicBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TopicBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Topic build() {
            return new Topic(this);
        }
    }

}

