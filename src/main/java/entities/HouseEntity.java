package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "House", schema = "hellodemo", catalog = "")
public class HouseEntity {
    private int houseId;
    private Integer price;
    private AreaEntity areaByAreaId;
    private String description;
    private String type;
    private Integer userId;
    private UserEntity userByUserId;
    private Integer areaId;

    @Id
    @Column(name = "house_id", nullable = false)
    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    @Basic
    @Column(name = "price", nullable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseEntity that = (HouseEntity) o;
        return houseId == that.houseId && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseId, price);
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "area_id", referencedColumnName = "area_id")
    public AreaEntity getAreaByAreaId() {
        return areaByAreaId;
    }

    public void setAreaByAreaId(AreaEntity areaByAreaId) {
        this.areaByAreaId = areaByAreaId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @Basic
    @Column(name = "area_id", nullable = true)
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
