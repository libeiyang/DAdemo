package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Image", schema = "hellodemo", catalog = "")
public class ImageEntity {
    private int urlId;
    private String imageUrl;

    @Id
    @Column(name = "url_id", nullable = false)
    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    @Basic
    @Column(name = "image_url", nullable = true, length = -1)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return urlId == that.urlId && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlId, imageUrl);
    }
}
