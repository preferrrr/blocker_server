package com.blocker.blocker_server.repository;

import com.blocker.blocker_server.entity.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryCustomImpl implements ContractRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    QContract contract = QContract.contract;

    QAgreementSign agreementSign = QAgreementSign.agreementSign;

    QUser user = QUser.user;

    @Override
    public List<Contract> findNotProceedContracts(User user, ContractState state) {

        JPAQuery<Contract> getContractListQuery = jpaQueryFactory
                .selectFrom(contract)
                .where(contract.user.email.eq(user.getEmail()),
                        contract.contractState.eq(state))
                .orderBy(contract.modifiedAt.desc());

        //TODO : 미체결 계약서 조회 페이징 ?

        List<Contract> result = getContractListQuery.fetch();

        return result;
    }

    @Override
    public Optional<Contract> findProceedContractWithSignById(Long contractId) {

        JPAQuery<Contract> getContractWithSignQuery = jpaQueryFactory
                .selectFrom(contract)
                .join(contract.agreementSigns, agreementSign).fetchJoin()
                .join(agreementSign.user, user).fetchJoin()
                .where(contract.contractId.eq(contractId));

        Contract contract = getContractWithSignQuery.fetchOne();

        return Optional.ofNullable(contract);
    }
}
