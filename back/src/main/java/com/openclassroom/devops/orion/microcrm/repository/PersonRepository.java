package com.openclassroom.devops.orion.microcrm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.openclassroom.devops.orion.microcrm.model.Person;

@CrossOrigin
@RepositoryRestResource
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, CrudRepository<Person, Long> {
    Optional<Person> findByEmail(@Param("email") String email);
}
