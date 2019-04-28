package am.ik.openenquete.questionnaire.enums;

import java.util.Comparator;

public enum Difficulty implements Comparator<Difficulty>, Comparable<Difficulty> {
	VERY_HARD(5), HARD(4), MODERATE(3), EASY(2), VERY_EASY(1);
	private final int value;

	Difficulty(int value) {
		this.value = value;
	}

	public static Difficulty valueOfUnsafe(int v) {
		return values()[5 - v];
	}

	@Override
	public int compare(Difficulty o1, Difficulty o2) {
		return Integer.compare(o1.getValue(), o2.getValue());
	}

	public int getValue() {
		return this.value;
	}
}
