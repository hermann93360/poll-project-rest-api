package com.live.pollprojectrestapi.domain;

import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PollingStationDescription {
    private String name;
    private String category;
    private String description;
    private String keywords;

    public PollingStationDescription check(){
        checkNameIsNotNull();
        checkCategoryIsNotNull();
        checkDescriptionIsNotNull();
        return this;
    }

    private void checkNameIsNotNull() {
        if(isNull(name)){
            throw new BadRequestException("Name cannot be null");
        }
    }
    private void checkCategoryIsNotNull() {
        if(isNull(name)){
            throw new BadRequestException("Category cannot be null");
        }
    }
    private void checkDescriptionIsNotNull() {
        if(isNull(name)){
            throw new BadRequestException("Description cannot be null");
        }
    }

    private boolean isNull(String value) {
        return value == null || value.isEmpty();
    }

    @Override
    public String toString() {
        return "PollingStationDescription{" +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
