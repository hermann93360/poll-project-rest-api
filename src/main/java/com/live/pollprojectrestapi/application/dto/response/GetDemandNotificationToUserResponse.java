package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.application.dto.notification.DemandJoinStationNotification;
import com.live.pollprojectrestapi.domain.model.Demand;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetDemandNotificationToUserResponse {
    List<DemandJoinStationNotification> demands;


    public GetDemandNotificationToUserResponse(List<Demand> demands) {
        this.demands = getCollect(demands);
    }

    private static List<DemandJoinStationNotification> getCollect(List<Demand> demands) {
        return demands.stream()
                .map(DemandJoinStationNotification::of)
                .collect(Collectors.toList());
    }

    public static GetDemandNotificationToUserResponse of(List<Demand> demand) {
        return new GetDemandNotificationToUserResponse(demand);
    }
}
