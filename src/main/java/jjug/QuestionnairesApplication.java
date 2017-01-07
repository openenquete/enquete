package jjug;

import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

import jjug.questionnaire.enums.Difficulty;
import jjug.questionnaire.enums.Satisfaction;
import jjug.seminar.ResponseForSeminar;
import jjug.seminar.ResponseForSeminarRepository;
import jjug.seminar.Seminar;
import jjug.seminar.SeminarRepository;
import jjug.session.ResponseForSession;
import jjug.session.ResponseForSessionRepository;
import jjug.session.Session;

@SpringBootApplication
@EntityScan(basePackageClasses = { QuestionnairesApplication.class,
		Jsr310JpaConverters.class })
public class QuestionnairesApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionnairesApplication.class, args);
	}

	@Profile("!cloud")
	@Bean
	CommandLineRunner runner(SeminarRepository seminarRepository,
			ResponseForSeminarRepository responseForSeminarRepository,
			ResponseForSessionRepository responseForSessionRepository,
			TransactionTemplate tx) {
		return a -> tx.execute(st -> {
			List<Session> sessions = asList(
					Session.builder().sessionName("キーノート")
							.speakerDisplayNames(asList("Taro Yamada", "Ichiro Suzuki"))
							.speakers(asList("foo", "making")).build(),
					Session.builder().sessionName("Hello World!")
							.speakerDisplayNames(asList("Taro Yamada", "Ichiro Tanaka"))
							.speakers(asList("bar", "makits")).build());
			Seminar seminar = Seminar.builder().seminarName("JJUG CCC Fall 2016")
					.sessions(sessions).seminarDate(LocalDate.of(2016, 12, 3)).build();
			sessions.forEach(session -> session.setSeminar(seminar));
			Seminar created = seminarRepository.save(seminar);

			responseForSeminarRepository.save(
					ResponseForSeminar.builder().comment("ナイスです。").request("Wifiが欲しい。")
							.satisfaction(Satisfaction.GOOD).seminar(created).build());
			responseForSeminarRepository
					.save(ResponseForSeminar.builder().comment("ダメでした").request("休憩が欲しい。")
							.satisfaction(Satisfaction.BAD).seminar(created).build());
			responseForSeminarRepository.save(ResponseForSeminar.builder().comment("")
					.request("").satisfaction(Satisfaction.EXCELLENT).seminar(created)
					.build());
			responseForSeminarRepository
					.save(ResponseForSeminar.builder().comment("++").request("hoge")
							.satisfaction(Satisfaction.BAD).seminar(created).build());

			responseForSessionRepository.save(ResponseForSession.builder().comment("")
					.difficulty(Difficulty.MODERATE).satisfaction(Satisfaction.EXCELLENT)
					.username("foo").session(sessions.get(0)).build());
			responseForSessionRepository.save(ResponseForSession.builder().comment("")
					.difficulty(Difficulty.EASY).satisfaction(Satisfaction.GOOD)
					.username("bar").session(sessions.get(0)).build());
			responseForSessionRepository.save(ResponseForSession.builder().comment("")
					.difficulty(Difficulty.EASY).satisfaction(Satisfaction.BAD)
					.username("making").session(sessions.get(1)).build());
			responseForSessionRepository.save(ResponseForSession.builder().comment("")
					.difficulty(Difficulty.VERY_EASY).satisfaction(Satisfaction.TERRIBLE)
					.username("bar").session(sessions.get(1)).build());
			return null;
		});
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Configuration
	public static class RestConfig extends RepositoryRestConfigurerAdapter {
		private final Validator validator;

		public RestConfig(@Lazy @Qualifier("mvcValidator") Validator validator) {
			this.validator = validator;
		}

		@Override
		public void configureValidatingRepositoryEventListener(
				ValidatingRepositoryEventListener validatingListener) {
			validatingListener.addValidator("beforeCreate", validator);
			validatingListener.addValidator("beforeSave", validator);
		}

	}
}
