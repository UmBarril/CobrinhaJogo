package cobrinha.jogo;

public class Player {
    private String name;
    private int personalRecord = 0;
    public Player(String name, int personalRecord) {
        this.setName(name);
        this.setPersonalRecord(personalRecord);
    }
    public String toString() {
        return  getName() + ": " + getPersonalRecord();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonalRecord() {
        return personalRecord;
    }

    public void setPersonalRecord(int personalRecord) {
        this.personalRecord = personalRecord;
    }

    public int hashCode() {
        return this.name.hashCode() * Integer.hashCode(this.getPersonalRecord());
    }

    public boolean equals(Player player) {
        if(this == player) {
            return true;
        }
        return this.name == player.name && this.personalRecord == player.personalRecord;
    }
}
