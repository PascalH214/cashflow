package de.pascalh214.cashflow.features.country.application;

import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private static final Set<String> ALLOWED_SORT_PROPERTIES = Set.of("alpha2Code", "name");
    private static final int MAX_PAGE_SIZE = 50;

    @Transactional(readOnly = true)
    public CountryResult getCountry(String alpha2Code) {
        if (alpha2Code == null || alpha2Code.isBlank()) {
            return new CountryResult.Failure("Country code is required");
        }
        CountryId id = new CountryId(alpha2Code.toUpperCase());
        Optional<Country> country = countryRepository.findById(id);
        return country.<CountryResult>map(CountryResult.Success::new)
                .orElseGet(() -> new CountryResult.Failure("Country not found"));
    }

    @Transactional(readOnly = true)
    public Page<Country> listCountries(Pageable pageable) {
        return countryRepository.findAll(sanitizePageable(pageable));
    }

    private Pageable sanitizePageable(Pageable pageable) {
        if (pageable == null) {
            return null;
        }
        int safeSize = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
        if (pageable.getSort().isUnsorted()) {
            return PageRequest.of(pageable.getPageNumber(), safeSize);
        }
        List<Sort.Order> safeOrders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            if (ALLOWED_SORT_PROPERTIES.contains(order.getProperty())) {
                safeOrders.add(order);
            }
        }
        if (safeOrders.isEmpty()) {
            return PageRequest.of(pageable.getPageNumber(), safeSize);
        }
        return PageRequest.of(pageable.getPageNumber(), safeSize, Sort.by(safeOrders));
    }
}
