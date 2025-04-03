package ru.thevalidator.timeattackracing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedEntityGraph(name = "sessions-types", attributeNodes = {@NamedAttributeNode("sessionType")})
@Table(name = "session")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private SessionTypeEntity sessionType;

    @Column(name = "ordinal_number", nullable = false)
    private Integer ordinalNumber;

    @Column(name = "name", nullable = false)
    private String sessionName;

    @Column(name = "laps_limit", nullable = false)
    private Integer lapsLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public SessionTypeEntity getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionTypeEntity sessionType) {
        this.sessionType = sessionType;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Integer getLapsLimit() {
        return lapsLimit;
    }

    public void setLapsLimit(Integer lapsLimit) {
        this.lapsLimit = lapsLimit;
    }

}