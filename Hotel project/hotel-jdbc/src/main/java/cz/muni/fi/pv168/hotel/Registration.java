package cz.muni.fi.pv168.hotel;

import java.util.Date;

public class Registration {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Double price;
    private Guest guest;
    private Room room;

    public Registration() {
    }

    public Registration(Long id, Room room, Guest guest, Date startDate, Date endDate, Double price) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", room=" + room +
                ", guest=" + guest +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }
}
