package br.com.rest.repositories;

import br.com.rest.model.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
