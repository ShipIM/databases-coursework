package com.example.repository;

import com.example.model.entity.Item;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Item.class, idClass = Integer.class)
public interface ItemRepository {

    @Query("select id, item.name from item " +
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
            "where (:name is NULL OR lower(item.name) LIKE '%' || lower(:name) || '%') " +
            "and (:category is NULL OR ic.category = :category)")
    long countFilteredItems(@Param("name") String name,
                            @Param("category") String category);

    @Query("select * from item where id = :id")
    Optional<Item> findItem(@Param("id") long id);

    @Query("select item.id, item.name from item " +
            "join favourite f on item.id = f.item_id " +
            "left join item_category ic on item.id = ic.item_id " +
            "where user_login = :user " +
            "and (:name is NULL OR lower(item.name) LIKE '%' || lower(:name) || '%') " +
            "and (:category is NULL OR ic.category = :category) " +
            "limit :page_size offset :page_number * :page_size")
    List<Item> findFavouriteItems(@Param("user") String user,
                                  @Param("name") String name,
                                  @Param("category") String category,
                                  @Param("page_size") long pageSize,
                                  @Param("page_number") long pageNumber);

    @Query("select count(*) from item " +
            "join favourite f on item.id = f.item_id " +
            "left join item_category ic on item.id = ic.item_id " +
            "where user_login = :user " +
            "and (:name is NULL OR lower(item.name) LIKE '%' || lower(:name) || '%') " +
            "and (:category is NULL OR ic.category = :category)")
    long countFavouriteItems(@Param("user") String user,
                            @Param("name") String name,
                            @Param("category") String category);

    @Query("select exists(select * from favourite where user_login = :user and item_id = :item)")
    boolean isFavourite(@Param("user") String user,
                        @Param("item") long item);
}
