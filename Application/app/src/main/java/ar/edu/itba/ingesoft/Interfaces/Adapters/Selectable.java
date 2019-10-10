package ar.edu.itba.ingesoft.Interfaces.Adapters;

public abstract class Selectable {

    private boolean selected;

    public boolean getSelected(){
        return selected;
    };
    public void setSelected(){
        this.selected = true;
    };
    public void unsetSelected(){
        this.selected = false;
    };
    public void toggleSelected(){
        this.selected = !this.selected;
    }
}
