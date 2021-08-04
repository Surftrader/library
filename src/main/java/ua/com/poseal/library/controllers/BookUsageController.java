package ua.com.poseal.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.library.dto.ExpiredInUseDTO;
import ua.com.poseal.library.mappers.BookInUseMapper;
import ua.com.poseal.library.models.BookHistory;
import ua.com.poseal.library.models.BookInUse;
import ua.com.poseal.library.services.BookUsageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookUsageController {

    private final BookUsageService bookUsageService;
    private final BookInUseMapper bookInUseMapper;

    @GetMapping("/usage/expired")
    public List<ExpiredInUseDTO> expired(
            @RequestParam(defaultValue = "${time.expired.after}") Integer minExpiredDays) {
        List<BookInUse> expiredBooks = bookUsageService.getExpiredBooks(minExpiredDays);

        return expiredBooks.stream()
                .map(bookInUseMapper::toDTO)
                .collect(Collectors.toList());
    }

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
