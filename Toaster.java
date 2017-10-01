public class Toaster {

private int slots;

private String songName;


public Toaster() {
    slots = 1;
    songName = "Let It Be";
}

public Toaster(int slots, String songName) {
    this.slots = slots;
    this.songName = songName;
}

public String toString(String songName) {
    return "Singing " + songName + " while I toast your bread!";
}

public void setSlots(int slots) {
    this.slots = slots;
}

public void setSongName(String name) {
    songName = name;
}

public int getSlots() {
    return slots;
}

public String getSongName() {
    return songName;
}

}