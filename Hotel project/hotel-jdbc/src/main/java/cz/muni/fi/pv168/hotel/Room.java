package cz.muni.fi.pv168.hotel;

public class Room {

    private Long id;
    private Integer floor;
    private Integer number;
    private Integer capacity;

    public Room() {
    }

    public Room(Long id, Integer floor, Integer number, Integer capacity) {
        this.id = id;
        this.floor = floor;
        this.number = number;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", floor='" + floor + '\'' +
                ", number='" + number + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (floor != null ? !floor.equals(room.floor) : room.floor != null) return false;
        if (id != null ? !id.equals(room.id) : room.id != null) return false;
        if (capacity != null ? !capacity.equals(room.capacity) : room.capacity != null) return false;
        return !(number != null ? !number.equals(room.number) : room.number != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (floor != null ? floor.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }
}
