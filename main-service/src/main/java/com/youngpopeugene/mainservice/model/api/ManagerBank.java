package com.youngpopeugene.mainservice.model.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ManagerBank{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    @JsonProperty("manager_email")
    private String managerEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Bank bank;
}