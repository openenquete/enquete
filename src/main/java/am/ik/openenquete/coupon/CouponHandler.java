package am.ik.openenquete.coupon;

import am.ik.openenquete.EnqueteProps;
import am.ik.openenquete.seminar.ResponseForSeminar;
import am.ik.openenquete.seminar.Seminar;
import am.ik.openenquete.session.ResponseForSession;
import am.ik.openenquete.session.Session;
import am.ik.openenquete.session.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RepositoryEventHandler
@Component
public class CouponHandler {

    private final Logger log = LoggerFactory.getLogger(CouponHandler.class);

    private final CouponRepository couponRepository;

    private final SessionRepository sessionRepository;

    private final EnqueteProps props;

    public CouponHandler(CouponRepository couponRepository, SessionRepository sessionRepository, EnqueteProps props) {
        this.couponRepository = couponRepository;
        this.sessionRepository = sessionRepository;
        this.props = props;
    }

    @HandleAfterCreate
    public void createCouponForSeminar(ResponseForSeminar response) {
        if (!this.props.isCoupon()) {
            return;
        }
        String username = response.getUsername();
        Optional<Coupon> opt = this.couponRepository.findByUsername(username);
        if (!opt.isPresent()) {
            Seminar seminar = response.getSeminar();
            this.createCoupon(seminar, username);
        }
    }

    @HandleAfterCreate
    public void createCouponForSession(ResponseForSession response) {
        if (!this.props.isCoupon()) {
            return;
        }
        String username = response.getUsername();
        Optional<Coupon> opt = this.couponRepository.findByUsername(username);
        if (!opt.isPresent()) {
            this.sessionRepository.findBySessionId(response.getSession().getSessionId())
                .map(Session::getSeminar) //
                .ifPresent(seminar -> this.createCoupon(seminar, username));
        }
    }

    void createCoupon(Seminar seminar, String username) {
        Coupon coupon = new Coupon(seminar, username);
        log.info("Create a coupon for {}", username);
        this.couponRepository.save(coupon);
    }

}
