package jjug.questionnaire.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SatisfactionConverter implements AttributeConverter<Satisfaction, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Satisfaction attribute) {
		return attribute == null ? null : attribute.getValue();
	}

	@Override
	public Satisfaction convertToEntityAttribute(Integer data) {
		return data == null ? null : Satisfaction.valueOfUnsafe(data);
	}
}
