package ar.edu.itba.ingesoft.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Universidad {

    private String name;
    private List<Materia> materias;

    @SuppressWarnings("unchecked")
    public Universidad(Map<String, Object> data){
        this.name = (String) data.get("name");
        this.materias = (List<Materia>) data.get("materias");
    }

    public Universidad(String name){
        this.name = name;
        this.materias = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void addMaterias(List<Materia> materias) {
        this.materias.addAll(materias);
    }

    public void addMateria(Materia materia) {
        this.materias.add(materia);
    }

    public void deleteMateria(Materia materia) {
        this.materias.remove(materia);
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
}
