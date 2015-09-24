package com.example.helloworld.services;

import com.example.helloworld.core.Saying;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class SayingDao extends AbstractDAO<Saying> {
    @Inject
    public SayingDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Saying persist(Saying item) {
        return super.persist(item);
    }

    public List<Saying> listAll() {
        return super.list(criteria());
    }
}
