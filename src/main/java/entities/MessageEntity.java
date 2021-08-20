package entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "Message", schema = "hellodemo", catalog = "")
public class MessageEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id", nullable = false)
    private int id;
    @Temporal(TemporalType.DATE)
    private Calendar date;
    @Basic
    @Column( name = "message", nullable = false, length = 50)
    private String message;

    public MessageEntity() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 50)
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            MessageEntity that = (MessageEntity)o;
            return this.id == that.id && Objects.equals(this.message, that.message);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.message});
    }
}

