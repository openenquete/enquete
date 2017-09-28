package am.ik.openenquete.session;

import java.util.List;
import java.util.UUID;

import am.ik.openenquete.questionnaire.enums.Satisfaction;
import lombok.Value;

public interface Summary<T extends Comparable<T>> {

	T getValue();

	Long getTotal();

	UUID getSessionId();

	String getSessionName();

	UUID getSeminarId();

	String getSeminarName();

	Long getCount();

	default Session asSession() {
		return new Session(getSessionId(), getSessionName(), getSeminarId(),
				getSeminarName());
	}

	default Detail<T> asDetail() {
		return new Detail<>(getValue(), getTotal(), getCount());
	}

	@Value
	final class Session {
		private final UUID sessionId;
		private final String sessionName;
		private final UUID seminarId;
		private final String seminarName;
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

	@Value
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
	}

	@Value
	class Detail<T extends Comparable<T>> {
		private final T value;
		private final long total;
		private final long count;
	}
}
