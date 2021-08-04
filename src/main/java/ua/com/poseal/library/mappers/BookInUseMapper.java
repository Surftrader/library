package ua.com.poseal.library.mappers;

import lombok.Getter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.com.poseal.library.dto.ExpiredInUseDTO;
import ua.com.poseal.library.models.BookInUse;
import ua.com.poseal.library.services.TimeService;

import java.time.temporal.ChronoUnit;

@Mapper
@Getter
public abstract class BookInUseMapper {

    @Autowired
    private TimeService timeService;

    @Value("${time.expired.after}")
    private Integer expiredAfter;

    @Mapping(target = "expiredDays", ignore = true)
    public abstract ExpiredInUseDTO toDTO(BookInUse bookInUse);

    @AfterMapping
    void map(@MappingTarget ExpiredInUseDTO dto, BookInUse bookInUse) {
        var now = timeService.currentDate();
        var expiredDays = now.isAfter(dto.getInUseFrom().plusDays(expiredAfter)) ?
                ChronoUnit.DAYS.between(bookInUse.getInUseFrom().plusDays(expiredAfter), now) :
                0;
        dto.setExpiredDays(expiredDays);
    }
}
