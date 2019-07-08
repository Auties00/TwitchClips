package values;

public enum Game {
    FORTNITE(33214), LOL(21779), APEX(511224);

    private long id;
    Game(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
