package am.ik.openenquete.seminar;

import am.ik.openenquete.coupon.CouponRepository;
import am.ik.openenquete.qrcode.QrCode;
import am.ik.openenquete.questionnaire.ContextUsername;
import am.ik.openenquete.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class SeminarController {

    private final SeminarRepository seminarRepository;

    private final ResponseForSeminarRepository responseForSeminarRepository;

    private final ContextUsername contextUsername;

    private final CouponRepository couponRepository;

    private final QrCode qrCode;

    public SeminarController(SeminarRepository seminarRepository, ResponseForSeminarRepository responseForSeminarRepository, ContextUsername contextUsername,
                             CouponRepository couponRepository, QrCode qrCode) {
        this.seminarRepository = seminarRepository;
        this.responseForSeminarRepository = responseForSeminarRepository;
        this.contextUsername = contextUsername;
        this.couponRepository = couponRepository;
        this.qrCode = qrCode;
    }

    @GetMapping("seminars/{seminarId}")
    String list(@PathVariable UUID seminarId, Model model,
                UriComponentsBuilder uriComponentsBuilder) {
        Seminar seminar = seminarRepository.findBySeminarId(seminarId).get();
        List<Session> sessions = seminar.getSessions();
        String username = contextUsername.getUsername();
        Optional<ResponseForSeminar> response = responseForSeminarRepository
            .findBySeminar_SeminarIdAndUsername(seminarId,
                username);
        couponRepository.findByUsername(username).ifPresent(coupon -> {
            model.addAttribute("coupon", coupon);
            model.addAttribute("qrCode", qrCode.dataUrl(uriComponentsBuilder.pathSegment("coupons", coupon.getCouponId().toString()).build().toString()));
        });
        model.addAttribute("seminar", seminar);
        model.addAttribute("sessions", sessions);
        model.addAttribute("submitted", response.isPresent());
        return "seminar";
    }
}
