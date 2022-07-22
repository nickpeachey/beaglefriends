package com.cawooka.beagle.controllers;

import com.cawooka.producers.BeagleProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/beagle")
public class BeagleController {
    @GetMapping("/event")
    public ResponseEntity<String> event() {
        BeagleProducer producer = new BeagleProducer();
        boolean result = producer.publish("hello from the controller");
        return new ResponseEntity<>("hello world" + result, HttpStatus.OK);
    }
}
