package am.ik.openenquete.seminar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class SeminarRestController {

    private final SeminarReportService seminarReportService;

    public SeminarRestController(SeminarReportService seminarReportService) {
        this.seminarReportService = seminarReportService;
    }

    @GetMapping(path = "v1/seminars/{seminarId}/votes")
    public Object votes(@PathVariable("seminarId") UUID seminarId) {
        return this.seminarReportService.satisfactionReport(seminarId)
            .entrySet()
            .stream()
            .map(e -> {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("session", e.getKey());
                item.put("summary", e.getValue());
                return item;
            })
            .collect(Collectors.toList());
    }
}
