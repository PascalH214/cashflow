package de.pascalh214.cashflow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api")
    public String index() {
        return "Hello World!";
    }

}
