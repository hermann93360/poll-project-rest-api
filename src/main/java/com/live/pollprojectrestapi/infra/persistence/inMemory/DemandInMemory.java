package com.live.pollprojectrestapi.infra.persistence.inMemory;

import com.live.pollprojectrestapi.domain.model.Demand;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.port.persistence.DemandPersistence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DemandInMemory implements DemandPersistence {

    private static final List<Demand> demands = new ArrayList<>();

    @Override
    public Demand addDemandToJoinPollingStation(PollingStation pollingStation, User requesting, User assignedTo) {
        Demand demand = new Demand(requesting, assignedTo, pollingStation);
        demands.add(demand);

        return demand;
    }

    @Override
    public List<Demand> getDemandNotificationByUserEmail(Email email) {
        return demands.stream()
                .filter(d -> d.getAssignedTo().getEmail().getValue().equals(email.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Demand> getDemandById(UUID demandID) {
        return demands.stream()
                .filter(demand -> demand.getDemandId().equals(demandID))
                .findFirst();
    }

    @Override
    public void removeDemand(UUID demandId) {
        demands.remove(getDemandById(demandId).get());
    }
}
