package com.example.repository;

import com.example.model.entity.ItemsForPeriod;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = ItemsForPeriod.class, idClass = Integer.class)
public interface ItemsForPeriodRepository {


    @Query("select * from get_max_cost_buy_per_day_for_period(:start, :end, :id::integer)")
    Optional<List<ItemsForPeriod>> getItemsForPeriod(@Param("start") LocalDate start,
                                                     @Param("end") LocalDate end,
                                                     @Param("id") long id);
}
