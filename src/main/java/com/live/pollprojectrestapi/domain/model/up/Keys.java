package com.live.pollprojectrestapi.domain.model.up;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Keys {
    private UUID keysId;
    private Group associatedGroup;
}
