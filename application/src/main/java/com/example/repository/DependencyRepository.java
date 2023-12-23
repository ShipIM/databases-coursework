package com.example.repository;


import com.example.model.entity.Dependency;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Dependency.class, idClass = Integer.class)
public interface DependencyRepository {

    @Query("select * from dependency_parser(:id::integer)")
    List<Dependency> getDependencies(long id);

}
