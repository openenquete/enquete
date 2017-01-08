package jjug.questionnaire.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DifficultyConverter implements AttributeConverter<Difficulty, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Difficulty attribute) {
		return attribute == null ? null : attribute.getValue();
	}

	@Override
	public Difficulty convertToEntityAttribute(Integer data) {
		return data == null ? null : Difficulty.valueOfUnsafe(data);
	}
}
