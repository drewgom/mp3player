package models;

public class Artist {
    private Integer pk;
    private String name;

    public Artist(Integer pk, String name) {
        this.pk = pk;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getPk() {
        return pk;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }
}
