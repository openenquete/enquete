package am.ik.openenquete.coupon;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "coupon_used")
public interface CouponUsedRepository extends Repository<CouponUsed, UUID> {

    CouponUsed save(CouponUsed couponUsed);
}
