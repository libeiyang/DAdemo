package entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "hellodemo", catalog = "")
public class UserEntity {
    private int userId;
    private String username;
    private String password;
    private String phoneNumber;
    private Collection<HouseEntity> housesByUserId;
    private List<MessageEntity> messages;
    private MessageEntity messageByMessageId;
    private Integer messageId;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 30)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone_number", nullable = false, length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, phoneNumber);
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<HouseEntity> getHousesByUserId() {
        return housesByUserId;
    }

    public void setHousesByUserId(Collection<HouseEntity> housesByUserId) {
        this.housesByUserId = housesByUserId;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "message_id", referencedColumnName = "id")
    public MessageEntity getMessageByMessageId() {
        return messageByMessageId;
    }

    public void setMessageByMessageId(MessageEntity messageByMessageId) {
        this.messageByMessageId = messageByMessageId;
    }

    @Basic
    @Column(name = "message_id", nullable = true)
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Transient
    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
}
