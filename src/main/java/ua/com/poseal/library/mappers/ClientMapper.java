package ua.com.poseal.library.mappers;

import org.mapstruct.Mapper;
import ua.com.poseal.library.dto.ClientDetailsDTO;
import ua.com.poseal.library.dto.ClientSmallDTO;
import ua.com.poseal.library.models.Client;

@Mapper
public interface ClientMapper {
    ClientSmallDTO toSmall(Client client);
    ClientDetailsDTO toDetails(Client client);
}
