package com.openclassroom.devops.orion.microcrm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.openclassroom.devops.orion.microcrm.model.Organization;

@CrossOrigin
@RepositoryRestResource
public interface OrganizationRepository
        extends PagingAndSortingRepository<Organization, Long>, CrudRepository<Organization, Long> {
}
