package com.acme.banking.platform.transactions.interfaces.rest;

import com.acme.banking.platform.transactions.application.command.services.TransactionCommandService;
import com.acme.banking.platform.transactions.application.query.services.TransactionQueryService;
import com.acme.banking.platform.transactions.interfaces.rest.resources.*;
import com.acme.banking.platform.transactions.interfaces.rest.transform.*;
import io.hypersistence.tsid.TSID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Transactions", description = "Transaction Management Endpoints")
public class TransactionsController {
    private final TransactionCommandService transactionCommandService;
    private final TransactionQueryService transactionQueryService;

    public TransactionsController(TransactionCommandService transactionCommandService, TransactionQueryService transactionQueryService) {
        this.transactionCommandService = transactionCommandService;
        this.transactionQueryService = transactionQueryService;
    }

    @Operation(summary = "Deposit money")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Deposit money received successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("deposit")
    public ResponseEntity<DepositMoneyResponseResource> deposit(@RequestBody DepositMoneyResource resource) {
        try {
            Long transactionId = TSID.Factory.getTsid().toLong();
            resource = resource.withTransactionId(transactionId);
            var command = DepositMoneyCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = transactionCommandService.deposit(command);
            if (notification.hasErrors()) {
                var response = new DepositMoneyResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var depositMoneyResource = DepositMoneyResourceFromCommandAssembler.toResourceFromDepositMoney(command);
            var response = new DepositMoneyResponseResource(depositMoneyResource, null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new DepositMoneyResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Withdraw money")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Withdraw money received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("withdraw")
    public ResponseEntity<WithdrawMoneyResponseResource> withdraw(@RequestBody WithdrawMoneyResource resource) {
        try {
            Long transactionId = TSID.Factory.getTsid().toLong();
            resource = resource.withTransactionId(transactionId);
            var command = WithdrawMoneyCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = transactionCommandService.withdraw(command);
            if (notification.hasErrors()) {
                var response = new WithdrawMoneyResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var withdrawMoneyResource = WithdrawMoneyResourceFromCommandAssembler.toResourceFromDepositMoney(command);
            var response = new WithdrawMoneyResponseResource(withdrawMoneyResource, null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new WithdrawMoneyResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Transfer money")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transfer money received successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation errors"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("transfer")
    public ResponseEntity<TransferMoneyResponseResource> transfer(@RequestBody TransferMoneyResource resource) {
        try {
            Long transactionId = TSID.Factory.getTsid().toLong();
            resource = resource.withTransactionId(transactionId);
            var command = TransferMoneyCommandFromResourceAssembler.toCommandFromResource(resource);
            var notification = transactionCommandService.transfer(command);
            if (notification.hasErrors()) {
                var response = new TransferMoneyResponseResource(null, notification.getErrors());
                return ResponseEntity.badRequest().body(response);
            }
            var transferMoneyResource = TransferMoneyResourceFromCommandAssembler.toResourceFromTransferMoney(command);
            var response = new TransferMoneyResponseResource(transferMoneyResource, null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new TransferMoneyResponseResource(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
