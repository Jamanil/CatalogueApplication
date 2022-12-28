package ru.jamanil.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.service.ItemService;

import java.util.List;

/**
 * @author Victor Datsenko
 * 24.12.2022
 */
@RestController
@RequiredArgsConstructor
public class ItemRestController {
    private final ItemService service;
    @GetMapping("/findBy")
    public ResponseEntity<List<Item>> findAllItemsContainsString(String findBy) {
        throwIllegalExceptionIfArgumentIsNull(findBy);
        List<Item> items = service.findAllItemContainsString(findBy);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/getCount")
    public ResponseEntity<Integer> findItemsAndGetCount(String findBy) {
        throwIllegalExceptionIfArgumentIsNull(findBy);
        return new ResponseEntity<>(service.findAllItemContainsString(findBy).size(), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        System.out.println("Exception handled");
        return new ResponseEntity<>(new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public ProblemDetail getBody() {
                ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
                detail.setTitle("FindBy argument error");
                detail.setDetail("FindBy cant be null");
                return detail;
            }
        }, HttpStatus.BAD_REQUEST);
    }

    private void throwIllegalExceptionIfArgumentIsNull(String argument) {
        if (argument == null)
            throw new IllegalArgumentException("findBy cant be null");
    }
}
