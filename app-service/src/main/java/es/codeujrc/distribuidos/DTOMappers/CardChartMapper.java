package es.codeujrc.distribuidos.DTOMappers;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;

import es.codeujrc.distribuidos.DTOs.CardChartDTO;

@Mapper(componentModel = "spring")
public interface CardChartMapper {

    default CardChartDTO toDTO(Map<String, Object> chartData) {
        if (chartData == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<String> names = (List<String>) chartData.get("names");
        @SuppressWarnings("unchecked")
        List<Integer> counts = (List<Integer>) chartData.get("counts");

        return new CardChartDTO(names, counts);
    }
}
