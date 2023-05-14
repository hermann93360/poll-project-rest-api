package com.live.pollprojectrestapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.groups.ConvertGroup;
import java.util.UUID;

@Getter
public class Demand {
    private final UUID demandId;
    private final User requesting;
    private final User assignedTo;
    private final PollingStation subjectRequesting;

    public Demand(User requesting, User assignedTo, PollingStation subjectRequesting) {
        this.demandId = UUID.randomUUID();
        this.requesting = requesting;
        this.assignedTo = assignedTo;
        this.subjectRequesting = subjectRequesting;
    }
}
