package ua.com.poseal.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.poseal.library.models.BookHistory;
import ua.com.poseal.library.models.BookInUse;
import ua.com.poseal.library.services.BookUsageService;

@RestController
@RequiredArgsConstructor
public class BookUsageController {

    private final BookUsageService bookUsageService;

    @PutMapping("/usage/client/{clientId}/book/{bookId}")
    public BookInUse takeBook(@PathVariable Long clientId,
                              @PathVariable Long bookId) {
        return bookUsageService.takeBook(clientId, bookId);
    }

    @DeleteMapping("/usage/client/{clientId}/book/{bookId}")
    public BookHistory returnBook(@PathVariable Long clientId,
                                  @PathVariable Long bookId) {
        return bookUsageService.returnBook(clientId, bookId);
    }
}
