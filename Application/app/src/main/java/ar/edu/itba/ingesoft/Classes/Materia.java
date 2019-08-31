package ar.edu.itba.ingesoft.Classes;

import java.util.Map;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Materia implements DatabaseObject {

    private String name;

    public Materia(Map<String, Object> data){
        this.name = (String) data.get("name");
    }

    public Materia(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> getDataToUpdate() {
        return null;
    }
}
