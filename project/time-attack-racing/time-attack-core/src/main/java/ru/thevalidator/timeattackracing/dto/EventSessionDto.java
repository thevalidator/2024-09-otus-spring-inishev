package ru.thevalidator.timeattackracing.dto;

import ru.thevalidator.timeattackracing.entity.SessionTypeEntity;

public class EventSessionDto {

    private Long sessionId;

    private Integer ordinalNumber;

    private SessionTypeEntity sessionType;

    private String sessionName;

    private Integer lapsLimit;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public SessionTypeEntity getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionTypeEntity sessionType) {
        this.sessionType = sessionType;
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
