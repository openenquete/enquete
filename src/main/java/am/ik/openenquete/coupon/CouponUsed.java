package am.ik.openenquete.coupon;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CouponUsed implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "binary(16)")
    private UUID usedId;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    @RestResource(exported = false)
    private Coupon coupon;

    @Column(insertable = false, updatable = false)
    private Instant updatedAt;

    @Column(insertable = false, updatable = false)
    private Instant createdAt;

    public UUID getUsedId() {
        return usedId;
    }

    public void setUsedId(UUID usedId) {
        this.usedId = usedId;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
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
        CouponUsed that = (CouponUsed) o;
        return Objects.equals(usedId, that.usedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedId);
    }
}
