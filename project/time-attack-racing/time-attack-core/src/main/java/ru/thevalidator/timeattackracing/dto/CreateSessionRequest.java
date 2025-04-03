package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateSessionRequest {

    @NotNull(message = "Missing parameter 'type_id'")
    private Integer typeId;

    @NotEmpty(message = "Missing parameter 'session_name'")
    private String sessionName;

    @NotNull(message = "Missing parameter 'laps_limit'")
    private Integer lapsLimit;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
