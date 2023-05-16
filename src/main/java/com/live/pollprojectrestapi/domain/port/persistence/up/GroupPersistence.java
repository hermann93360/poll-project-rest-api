package com.live.pollprojectrestapi.domain.port.persistence.up;

import com.live.pollprojectrestapi.domain.model.up.Group;

public interface GroupPersistence {
    void updateGroup(Group group);
}
