package entity;

/**
 * Created by serhii.hokhkalenko on 2016-08-12.
 */
public class Player {
    private String surname;
    private String name;
    private Integer number;

    public Player() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public static Player createPlayer(String name, String surname, Integer number){
        final Player player=new Player();
        player.setName(name);
        player.setSurname(surname);
        player.setNumber(number);
        return player;
    }

}
