package ar.edu.itba.ingesoft.Classes;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Universidad implements DatabaseObject {

    private String name;

    @SuppressWarnings("unchecked")
    public Universidad(Map<String, Object> data){
        this.name = (String) data.get("name");
    }

    public Universidad(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Universidad that = (Universidad) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public Map<String, Object> generateDataToUpdate() {
        return null;
    }
}
