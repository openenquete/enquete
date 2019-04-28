package am.ik.openenquete.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class ConponController {

    private final CouponRepository couponRepository;

    public ConponController(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @GetMapping(path = "/coupons/{couponId}")
    public String showCoupon(@PathVariable("couponId") UUID couponId, Model model) {
        Coupon coupon = this.couponRepository.findById(couponId).get();
        model.addAttribute("coupon", coupon);
        return "coupon";
    }
}
