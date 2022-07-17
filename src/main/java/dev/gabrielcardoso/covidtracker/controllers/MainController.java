package dev.gabrielcardoso.covidtracker.controllers;

import dev.gabrielcardoso.covidtracker.models.CovidStat;
import dev.gabrielcardoso.covidtracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<CovidStat> allCovidStats = coronaVirusDataService.getAllCovidStats();
        int totalCases = allCovidStats.stream().mapToInt(covidStat -> covidStat.getLatestTotalCases()).sum();
        int totalNewCases = allCovidStats.stream().mapToInt(covidStat -> covidStat.getDiffFromPreviousDay()).sum();
        model.addAttribute("covidStats", allCovidStats);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }

}
