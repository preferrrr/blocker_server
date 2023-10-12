package com.blocker.blocker_server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "CANCEL_SIGN")
@Getter
@NoArgsConstructor
@DynamicInsert
public class CancelSign {

    @EmbeddedId
    private SignId id;

    @MapsId("email")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("contractId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_state")
    private SignState signState;

    @Builder
    public CancelSign(User user, Contract contract) {
        SignId id = SignId.builder()
                .contractId(contract.getContractId())
                .email(user.getEmail())
                .build();

        this.id = id;
        this.contract = contract;
        this.user = user;
        this.signState = SignState.N;
    }

    public void sign() {
        this.signState = SignState.Y;
    }

}
