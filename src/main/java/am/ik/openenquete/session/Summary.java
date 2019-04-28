package am.ik.openenquete.session;

import am.ik.openenquete.questionnaire.enums.Satisfaction;

import java.util.List;
import java.util.UUID;

public interface Summary<T extends Comparable<T>> {

    UUID getSessionId();

    String getSessionName();

    T getValue();

    Long getCount();

    Long getTotal();

    UUID getSeminarId();

    String getSeminarName();

    default Session asSession() {
        return new Session(getSessionId(), getSessionName(), getSeminarId(),
            getSeminarName());
    }

    default Detail<T> asDetail() {
        return new Detail<>(getValue(), getTotal(), getCount());
    }

    final class Session {

        private final UUID sessionId;

        private final String sessionName;

        private final UUID seminarId;

        private final String seminarName;

        public Session(UUID sessionId, String sessionName, UUID seminarId, String seminarName) {
            this.sessionId = sessionId;
            this.sessionName = sessionName;
            this.seminarId = seminarId;
            this.seminarName = seminarName;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Summary.Session)) {
                return false;
            }
            final Session other = (Session) o;
            final Object this$sessionId = this.getSessionId();
            final Object other$sessionId = other.getSessionId();
            if (this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId)) {
                return false;
            }
            final Object this$sessionName = this.getSessionName();
            final Object other$sessionName = other.getSessionName();
            if (this$sessionName == null ? other$sessionName != null : !this$sessionName.equals(other$sessionName)) {
                return false;
            }
            final Object this$seminarId = this.getSeminarId();
            final Object other$seminarId = other.getSeminarId();
            if (this$seminarId == null ? other$seminarId != null : !this$seminarId.equals(other$seminarId)) {
                return false;
            }
            final Object this$seminarName = this.getSeminarName();
            final Object other$seminarName = other.getSeminarName();
            if (this$seminarName == null ? other$seminarName != null : !this$seminarName.equals(other$seminarName)) {
                return false;
            }
            return true;
        }

        public UUID getSeminarId() {
            return this.seminarId;
        }

        public String getSeminarName() {
            return this.seminarName;
        }

        public UUID getSessionId() {
            return this.sessionId;
        }

        public String getSessionName() {
            return this.sessionName;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $sessionId = this.getSessionId();
            result = result * PRIME + ($sessionId == null ? 43 : $sessionId.hashCode());
            final Object $sessionName = this.getSessionName();
            result = result * PRIME + ($sessionName == null ? 43 : $sessionName.hashCode());
            final Object $seminarId = this.getSeminarId();
            result = result * PRIME + ($seminarId == null ? 43 : $seminarId.hashCode());
            final Object $seminarName = this.getSeminarName();
            result = result * PRIME + ($seminarName == null ? 43 : $seminarName.hashCode());
            return result;
        }

        public String toString() {
            return "Summary.Session(sessionId=" + this.getSessionId() + ", sessionName=" + this.getSessionName() + ", seminarId=" + this.getSeminarId() + ", seminarName=" + this.getSeminarName() + ")";
        }
    }

    class Report<T extends Comparable<T>> {

        private final double average;

        private final long total;

        private final long count;

        private final List<Detail<T>> details;

        public Report(double average, long total, long count, List<Detail<T>> details) {
            this.average = average;
            this.total = total;
            this.count = count;
            this.details = details;
        }

        public double getAverage() {
            return average;
        }

        public long getTotal() {
            return total;
        }

        public long getCount() {
            return count;
        }

        public List<Detail<T>> getDetails() {
            return details;
        }

        @Override
        public String toString() {
            return "Report{" + "average=" + average + ", total=" + total + ", count="
                + count + ", details=" + details + '}';
        }
    }

    class SatisfactionReport extends Report<Satisfaction> {

        private final long nsat;

        public SatisfactionReport(Report<Satisfaction> report) {
            super(report.average, report.total, report.count, report.details);
            this.nsat = nsat(report);
        }

        /**
         * Returns "Net User Satisfaction". <br>
         * http://download.microsoft.com/download/3/2/2/32269687-F181-449A-8C72-317090403C70/Determining_Net_User_Satisfaction.docx
         */
        private static long nsat(Summary.Report<Satisfaction> report) {
            double vsat = extract(report, Satisfaction.EXCELLENT) * 100.0d
                / report.getCount();
            double dsat = (extract(report, Satisfaction.BAD)
                + extract(report, Satisfaction.TERRIBLE)) * 100.0d
                / report.getCount();
            return Math.round(vsat - dsat) + 100;
        }

        private static long extract(Summary.Report<Satisfaction> report,
                                    Satisfaction satisfaction) {
            return report.getDetails().stream().filter(d -> d.getValue() == satisfaction)
                .mapToLong(Summary.Detail::getCount).sum();
        }

        public long getNsat() {
            return nsat;
        }
    }

    class Detail<T extends Comparable<T>> {

        private final T value;

        private final long total;

        private final long count;

        public Detail(T value, long total, long count) {
            this.value = value;
            this.total = total;
            this.count = count;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Summary.Detail)) {
                return false;
            }
            final Detail<?> other = (Detail<?>) o;
            final Object this$value = this.getValue();
            final Object other$value = other.getValue();
            if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
                return false;
            }
            if (this.getTotal() != other.getTotal()) {
                return false;
            }
            if (this.getCount() != other.getCount()) {
                return false;
            }
            return true;
        }

        public long getCount() {
            return this.count;
        }

        public long getTotal() {
            return this.total;
        }

        public T getValue() {
            return this.value;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $value = this.getValue();
            result = result * PRIME + ($value == null ? 43 : $value.hashCode());
            final long $total = this.getTotal();
            result = result * PRIME + (int) ($total >>> 32 ^ $total);
            final long $count = this.getCount();
            result = result * PRIME + (int) ($count >>> 32 ^ $count);
            return result;
        }

        public String toString() {
            return "Summary.Detail(value=" + this.getValue() + ", total=" + this.getTotal() + ", count=" + this.getCount() + ")";
        }
    }
}
