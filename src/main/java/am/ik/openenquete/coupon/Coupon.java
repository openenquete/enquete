package am.ik.openenquete.coupon;

import am.ik.openenquete.seminar.Seminar;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Coupon implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "binary(16)")
    private UUID couponId;

    @ManyToOne
    @JoinColumn(name = "seminar_id")
    private Seminar seminar;

    @OneToOne(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private CouponUsed couponUsed;

    @Column(updatable = false)
    private String username;

    @Column(insertable = false, updatable = false)
    private Instant updatedAt;

    @Column(insertable = false, updatable = false)
    private Instant createdAt;

    public Coupon() {
    }

    public Coupon(Seminar seminar, String username) {
        this.seminar = seminar;
        this.username = username;
    }

    public UUID getCouponId() {
        return couponId;
    }

    public void setCouponId(UUID couponId) {
        this.couponId = couponId;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public CouponUsed getCouponUsed() {
        return couponUsed;
    }

    public void setCouponUsed(CouponUsed couponUsed) {
        this.couponUsed = couponUsed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(couponId, coupon.couponId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId);
    }

    @Override
    public String toString() {
        return "Coupon{" +
            "couponId=" + couponId +
            '}';
    }
}
