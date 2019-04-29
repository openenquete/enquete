package am.ik.openenquete.coupon;

import am.ik.openenquete.seminar.Seminar;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RepositoryEventHandler
@Component
public class CouponUsedHandler {

    private final MeterRegistry meterRegistry;

    private final CouponRepository couponRepository;

    public CouponUsedHandler(MeterRegistry meterRegistry, CouponRepository couponRepository) {
        this.meterRegistry = meterRegistry;
        this.couponRepository = couponRepository;
    }

    @HandleAfterCreate
    public void createCouponForSeminar(CouponUsed couponUsed) {
        Optional<Coupon> coupon = this.couponRepository.findById(couponUsed.getCoupon().getCouponId());
        coupon.map(Coupon::getSeminar)
            .map(Seminar::getSeminarId)
            .ifPresent(id -> this.meterRegistry.counter("coupon_used", "seminar_id", id.toString()).increment());
    }
}
