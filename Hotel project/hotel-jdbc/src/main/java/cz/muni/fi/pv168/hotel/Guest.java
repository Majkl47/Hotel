package cz.muni.fi.pv168.hotel;

import java.util.Date;

public class Guest {

    private Long id;
    private String name;
    private String address;
    private Long phone;
    private Date birthDate;

    public Guest() {
    }

    public Guest(Long id, String name, String address, Long phone, Date birthDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date date) {
        this.birthDate = date;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", birth date='" + birthDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;

        if (address != null ? !address.equals(guest.address) : guest.address != null) return false;
        if (birthDate != null ? !birthDate.equals(guest.birthDate) : guest.birthDate != null) return false;
        if (name != null ? !name.equals(guest.name) : guest.name != null) return false;
        if (id != null ? !id.equals(guest.id) : guest.id != null) return false;
        return !(phone != null ? !phone.equals(guest.phone) : guest.phone != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
