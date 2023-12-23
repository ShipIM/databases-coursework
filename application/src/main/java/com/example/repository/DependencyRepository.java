package com.example.repository;


import com.example.model.entity.Dependency;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Dependency.class, idClass = Integer.class)
public interface DependencyRepository {

    @Query("select * from dependency_parser(:id::integer)" +
            "limit :page_size offset :page_number * :page_size")
    List<Dependency> getDependencies(@Param("id") long id,
                                     @Param("page_size") long pageSize,
                                     @Param("page_number") long pageNumber);

    @Query("select count(*) from dependency_parser(:id::integer)")
    Long getDependenciesCount(@Param("id") long id);
}
