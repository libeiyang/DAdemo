package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Area", schema = "hellodemo", catalog = "")
public class AreaEntity {
    private int areaId;
    private String streetName;

    @Id
    @Column(name = "area_id", nullable = false)
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "street_name", nullable = true, length = 100)
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaEntity that = (AreaEntity) o;
        return areaId == that.areaId && Objects.equals(streetName, that.streetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaId, streetName);
    }
}
