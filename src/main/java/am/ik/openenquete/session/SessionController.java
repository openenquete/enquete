package am.ik.openenquete.session;

import am.ik.openenquete.coupon.CouponRepository;
import am.ik.openenquete.qrcode.QrCode;
import am.ik.openenquete.questionnaire.ContextUsername;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@Controller
public class SessionController {

    private final SessionRepository sessionRepository;

    private final ResponseForSessionRepository responseForSessionRepository;

    private final ContextUsername contextUsername;

    private final CouponRepository couponRepository;

    private final QrCode qrCode;

    public SessionController(SessionRepository sessionRepository, ResponseForSessionRepository responseForSessionRepository, ContextUsername contextUsername,
                             CouponRepository couponRepository, QrCode qrCode) {
        this.sessionRepository = sessionRepository;
        this.responseForSessionRepository = responseForSessionRepository;
        this.contextUsername = contextUsername;
        this.couponRepository = couponRepository;
        this.qrCode = qrCode;
    }

    @GetMapping("sessions/{sessionId}")
    String session(@PathVariable UUID sessionId, Model model,
                   UriComponentsBuilder uriComponentsBuilder) {
        Session session = sessionRepository.findBySessionId(sessionId).get();
        String username = contextUsername.getUsername();
        Optional<ResponseForSession> response = responseForSessionRepository
            .findBySession_SessionIdAndUsername(sessionId,
                username);
        couponRepository.findByUsername(username).ifPresent(coupon -> {
            model.addAttribute("coupon", coupon);
            model.addAttribute("qrCode", qrCode.dataUrl(uriComponentsBuilder.pathSegment("coupons", coupon.getCouponId().toString()).build().toString()));
        });
        model.addAttribute("s", session);
        model.addAttribute("submitted", response.isPresent());
        return "session";
    }
}
