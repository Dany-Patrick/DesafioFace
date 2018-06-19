package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.Developer;

import java.util.List;

public class DevelopersQueries {
    public List<Developer> all()
    {

        return Developer.listAll(Developer.class);
    }
    public List<Developer> findByName(String name)
    {
        String query = "name LIKE ?";
        String arguments = "%"+name+"%";
        return Developer.find(Developer.class,query,arguments);
    }
}
