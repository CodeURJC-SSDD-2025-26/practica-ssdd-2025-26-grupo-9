package es.codeujrc.distribuidos.DTOs;

import java.util.List;

public record CardChartDTO(
    List<String> names,
    List<Integer> counts
) {}
