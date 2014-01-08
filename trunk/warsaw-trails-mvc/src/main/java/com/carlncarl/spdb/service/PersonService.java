package com.carlncarl.spdb.service;

import com.carlncarl.spdb.model.Person;

public interface PersonService {
    public Person getRandom();
    public Person getById(Long id);
    public void save(Person person);
}