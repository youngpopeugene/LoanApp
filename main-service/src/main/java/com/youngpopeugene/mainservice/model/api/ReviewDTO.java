package com.youngpopeugene.mainservice.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @NotNull
    @JsonProperty("application_id")
    private int applicationId;
    @NotNull
    @JsonProperty("new_status")
    private Status newStatus;
}