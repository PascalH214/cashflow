package de.pascalh214.cashflow.features.country.api;

import de.pascalh214.cashflow.features.country.application.CountryImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryImportController {

    private final CountryImportService countryImportService;

    @PostMapping("/import")
    public ResponseEntity<ImportCountriesResponse> importCountries(
            @RequestParam(name = "replace", defaultValue = "false") boolean replaceExisting
    ) {
        int imported = countryImportService.importCountries(replaceExisting);
        return ResponseEntity.ok(new ImportCountriesResponse(imported));
    }

    public record ImportCountriesResponse(int imported) {}
}
