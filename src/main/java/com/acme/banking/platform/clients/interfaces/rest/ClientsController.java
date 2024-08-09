package com.acme.banking.platform.clients.interfaces.rest;

import com.acme.banking.platform.clients.application.commandservices.ClientCommandService;
import com.acme.banking.platform.clients.application.queryservices.ClientQueryService;
import com.acme.banking.platform.clients.domain.projections.ClientProjection;
import com.acme.banking.platform.clients.domain.projections.ClientAuditLogProjection;
import com.acme.banking.platform.clients.interfaces.rest.resources.*;
import com.acme.banking.platform.clients.interfaces.rest.transform.ClientResourceFromCommandAssembler;
import com.acme.banking.platform.clients.interfaces.rest.transform.EditClientCommandFromResourceAssembler;
import com.acme.banking.platform.clients.interfaces.rest.transform.RegisterClientCommandFromResourceAssembler;
import com.acme.banking.platform.shared.interfaces.rest.Pagination;
import io.hypersistence.tsid.TSID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Clients", description = "Client Management Endpoints")
public class ClientsController {
    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientsController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @Operation(summary = "Register a new client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Register Client received successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<RegisterClientResponseResource> register(@RequestBody RegisterClientResource resource) {
        try {
            Long id = TSID.Factory.getTsid().toLong();
            resource = resource.withId(id);
            var command = RegisterClientCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = clientCommandService.register(command);
            if (notification.hasErrors()) {
                var response = new RegisterClientResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var clientResource = ClientResourceFromCommandAssembler.toResourceFromRegisterClient(command);
            var responseResource = new RegisterClientResponseResource(clientResource, null);
            return new ResponseEntity<>(responseResource, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new RegisterClientResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Edit a client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Edit Client received successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EditClientResponseResource> edit(@PathVariable("id") Long id, @RequestBody EditClientResource resource) {
        try {
            resource = resource.withId(id);
            var command = EditClientCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = clientCommandService.edit(command);
            if (notification.hasErrors()) {
                var response = new EditClientResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var clientResource = ClientResourceFromCommandAssembler.toResourceFromEditClient(command);
            var responseResource = new EditClientResponseResource(clientResource, null);
            return new ResponseEntity<>(responseResource, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EditClientResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/page/{page}/limit/{limit}")
    @Operation(summary = "Get clients")
    public ResponseEntity<GetClientsResponseResource> getClients(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        try {
            List<ClientProjection> clients = clientQueryService.getClients(page, limit);
            var response = new GetClientsResponseResource(clients, null);
            HttpHeaders headers = Pagination.createPaginationHeaders(clients.size(), page, limit);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GetClientsResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/audit-logs")
    @Operation(summary = "Get client audit logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ClientAuditLogResponseResource> getAuditLogsByClientId(@PathVariable("id") Long clientId) {
        try {
            List<ClientAuditLogProjection> auditLog = clientQueryService.getAuditLogsByClientId(clientId);
            var response = new ClientAuditLogResponseResource(auditLog, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ClientAuditLogResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
