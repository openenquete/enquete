package jjug.questionnaire.enums;

import java.util.Comparator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Satisfaction implements Comparator<Satisfaction>, Comparable<Satisfaction> {
	EXCELLENT(5), GOOD(4), NOT_BAD(3), BAD(2), TERRIBLE(1);
	private final int value;

	public static Satisfaction valueOfUnsafe(int v) {
		return values()[5 - v];
	}

	@Override
	public int compare(Satisfaction o1, Satisfaction o2) {
		return Integer.compare(o1.getValue(), o2.getValue());
	}
}
