package com.example.repository;

import com.example.model.entity.Item;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Item.class, idClass = Integer.class)
public interface ItemRepository {

    @Query("select * from item " +
            "left join item_category ic on item.id = ic.item_id " +
            "where (:name is NULL OR lower(item.name) LIKE '%' || lower(:name) || '%') " +
            "and (:category is NULL OR ic.category = :category) " +
            "limit :page_size offset :page_number * :page_size")
    List<Item> findFilteredItems(@Param("name") String name,
                                 @Param("category") String category,
                                 @Param("page_size") long pageSize,
                                 @Param("page_number") long pageNumber);

    @Query("select count(*) from item " +
            "left join item_category ic on item.id = ic.item_id " +
            "where (:category is NULL OR ic.category = :category)")
    long countFilteredItems(@Param("category") String category);
}
