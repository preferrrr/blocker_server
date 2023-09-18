package com.blocker.blocker_server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "SIGN")
@Getter
@NoArgsConstructor
@DynamicInsert
public class Sign extends BaseEntity{

    @EmbeddedId
    private SignId id;

    @MapsId("email")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("contractId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_state")
    private SignState signState;

    @Builder
    public Sign(User user, Contract contract) {
        SignId id = SignId.builder()
                .contractId(contract.getContractId())
                .email(user.getEmail())
                .build();

        this.id = id;
        this.contract = contract;
        this.user = user;
        this.signState = SignState.N;
    }

}
