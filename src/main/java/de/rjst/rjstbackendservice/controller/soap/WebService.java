package de.rjst.rjstbackendservice.controller.soap;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.logic.PlayerService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


@jakarta.jws.WebService(targetNamespace = "http://test.rjst.de", name = "WebService")
public interface WebService {

    @PreAuthorize("hasAuthority('USER')")
    @WebMethod
    @WebResult(name = "result")
    List<PlayerEntity> getPlayers();

    @PreAuthorize("hasAuthority('USER')")
    @WebMethod
    @WebResult(name = "result")
    PlayerEntity getPlayerById(@WebParam(name = "input") Long id);

    @PreAuthorize("hasAuthority('USER')")
    @WebMethod
    @WebResult(name = "result")
    PlayerEntity postPlayer(@WebParam(name = "input") PlayerEntity player);

    @PreAuthorize("hasAuthority('USER')")
    @WebMethod
    @WebResult(name = "result")
    PlayerEntity updatePlayer(@WebParam(name = "input") PlayerEntity player);

    @PreAuthorize("hasAuthority('ADMIN')")
    @WebMethod
    @WebResult(name = "result")
    boolean deletePlayer(@WebParam(name = "input") Long id);

}
