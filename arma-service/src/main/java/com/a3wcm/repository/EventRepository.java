package com.a3wcm.repository;

import com.a3wcm.domain.Config;
import com.a3wcm.domain.ConfigType;
import com.a3wcm.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  DAO layer for event model. Serves to exchange data between micro-service and related to it, db
 **/
@Repository
public interface EventRepository extends JpaRepository<Event, Long>  {

    /**
     *  Looks for event by provided name.
     *  @param name - string value, used during unit config search by name
     **/
    List<Event> findByNameContains(String name);

    /**
     *  Looks for event by provided type.
     *  @param description - string value of type to used in search
     **/
    List<Event> findByDescriptionContains(String description);

    /**
     *  Looks for event by provided created date.
     *  @param createDate - instance of util.Date that represents date and time when event was created
     **/
    Event findByCreatedDate(Date createDate);

    /**
     *  Looks for event by provided note.
     *  @param modifiedDate - instance of util.Date that represents date and time when event was modified last time
     **/
    List<Event> findByModifiedDate(Date modifiedDate);



}
