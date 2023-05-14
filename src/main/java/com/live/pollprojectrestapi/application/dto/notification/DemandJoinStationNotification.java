package com.live.pollprojectrestapi.application.dto.notification;

import com.live.pollprojectrestapi.domain.model.Demand;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import lombok.Data;

import java.util.UUID;

@Data
public class DemandJoinStationNotification {
    private final UUID demandId;
    private final String nameRequestUser;
    private final String namePollingStation;

    public DemandJoinStationNotification(Demand demand) {
        this.demandId = demand.getDemandId();
        this.nameRequestUser = demand.getRequesting().getUsername();
        this.namePollingStation = demand.getSubjectRequesting().getPollingStationDescription().getName();
    }

    public static DemandJoinStationNotification of(Demand demand) {
        return new DemandJoinStationNotification(demand);
    }
}
