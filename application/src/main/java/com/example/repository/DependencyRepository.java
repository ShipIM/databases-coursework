package com.example.repository;


import com.example.model.entity.Dependency;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Dependency.class, idClass = Integer.class)
public interface DependencyRepository {

    //TODO: dependency parser

}
