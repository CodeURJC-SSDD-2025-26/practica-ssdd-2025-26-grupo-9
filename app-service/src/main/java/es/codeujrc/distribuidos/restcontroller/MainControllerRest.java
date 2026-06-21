package es.codeujrc.distribuidos.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeujrc.distribuidos.DTOs.CardChartDTO;
import es.codeujrc.distribuidos.service.CardService;



@RestController
@RequestMapping("/api/v1/charts")
public class MainControllerRest {

    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public ResponseEntity<CardChartDTO> getCardChartData() {
        CardChartDTO chartData = cardService.getMetaCardsChartData();
        return ResponseEntity.ok(chartData);
    }

}