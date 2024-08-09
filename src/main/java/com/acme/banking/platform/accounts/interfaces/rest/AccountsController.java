package com.acme.banking.platform.accounts.interfaces.rest;

import com.acme.banking.platform.accounts.application.services.AccountCommandService;
import com.acme.banking.platform.accounts.application.services.AccountQueryService;
import com.acme.banking.platform.accounts.domain.projections.AccountProjection;
import com.acme.banking.platform.accounts.domain.projections.AccountAuditLogProjection;
import com.acme.banking.platform.accounts.interfaces.rest.resources.*;
import com.acme.banking.platform.accounts.interfaces.rest.transform.AccountResourceFromCommandAssembler;
import com.acme.banking.platform.accounts.interfaces.rest.transform.EditAccountCommandFromResourceAssembler;
import com.acme.banking.platform.accounts.interfaces.rest.transform.OpenAccountCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts", description = "Account Management Endpoints")
public class AccountsController {
    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;

    public AccountsController(AccountCommandService accountCommandService, AccountQueryService accountQueryService) {
        this.accountCommandService = accountCommandService;
        this.accountQueryService = accountQueryService;
    }

    @Operation(summary = "Open a new account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Open Account received successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<OpenAccountResponseResource> open(@RequestBody OpenAccountResource resource) {
        try {
            Long id = TSID.Factory.getTsid().toLong();
            resource = resource.withId(id);
            var command = OpenAccountCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = accountCommandService.open(command);
            if (notification.hasErrors()) {
                var response = new OpenAccountResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var accountResource = AccountResourceFromCommandAssembler.toResourceFromOpenAccount(command);
            var response = new OpenAccountResponseResource(accountResource, null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new OpenAccountResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Edit an account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Edit Account received successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EditAccountResponseResource> edit(@PathVariable("id") Long id, @RequestBody EditAccountResource resource) {
        try {
            resource = resource.withId(id);
            var command = EditAccountCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = accountCommandService.edit(command);
            if (notification.hasErrors()) {
                var response = new EditAccountResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var accountEditedResource = AccountResourceFromCommandAssembler.toResourceFromEditClient(command);
            var response = new EditAccountResponseResource(accountEditedResource, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EditAccountResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/page/{page}/limit/{limit}")
    @Operation(summary = "Get accounts")
    public ResponseEntity<GetAccountsResponseResource> getAccounts(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        try {
            List<AccountProjection> accounts = accountQueryService.getAccounts(page, limit);
            var response = new GetAccountsResponseResource(accounts, null);
            HttpHeaders headers = Pagination.createPaginationHeaders(accounts.size(), page, limit);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GetAccountsResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/audit-logs")
    @Operation(summary = "Get account audit logs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = ""),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountAuditLogResponseResource> getAuditLogsByAccountId(@PathVariable("id") Long accountId) {
        try {
            List<AccountAuditLogProjection> auditLog = accountQueryService.getAuditLogsByAccountId(accountId);
            var response = new AccountAuditLogResponseResource(auditLog, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new AccountAuditLogResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
