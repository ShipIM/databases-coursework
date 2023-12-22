package com.example.repository;

import com.example.model.entity.Lot;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Lot.class, idClass = Long.class)
public interface LotRepository {

    @Query("select lot.id, lot.user_login as login, cost_current as current, cost_buy as buy, time_end as \"end\" from lot " +
            "join lot_cost_information lci on lot.id = lci.lot_id " +
            "join lot_status_information lsi on lot.id = lsi.lot_id " +
            "join lot_time_information lti on lot.id = lti.lot_id " +
            "where item_id = :item " +
            "and status = 'ACTIVE' " +
            "limit :page_size offset :page_number * :page_size")
    List<Lot> findActiveLots(@Param("item") long item,
                             @Param("page_size") long pageSize,
                             @Param("page_number") long pageNumber);

    @Query("select count(*) from lot " +
            "join lot_status_information lsi on lot.id = lsi.lot_id " +
            "where item_id = :item " +
            "and status = 'ACTIVE'")
    long countActiveLots(@Param("item") long item);

}
