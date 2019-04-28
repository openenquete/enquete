package am.ik.openenquete.coupon;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "coupons")
public interface CouponRepository extends Repository<Coupon, UUID> {

    Optional<Coupon> findById(UUID couponId);

    Optional<Coupon> findByUsername(String username);

    @RestResource(exported = false)
    Coupon save(Coupon coupon);
}
