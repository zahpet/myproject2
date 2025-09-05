package com.javacourse.myproject2.controller;

import com.javacourse.myproject2.exception.CryptoNotFoundException;
import com.javacourse.myproject2.model.Crypto;
import com.javacourse.myproject2.service.CryptoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // Přidání nové kryptoměny
    @PostMapping
    public ResponseEntity<String> addCrypto(@RequestBody Crypto crypto) {
        cryptoService.addCrypto(crypto);
        return ResponseEntity.ok("Crypto added successfully");
    }

    // Výpis všech kryptoměn s možností řazení
    @GetMapping
    public ResponseEntity<List<Crypto>> getAllCryptos(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(cryptoService.getAllCryptos(sort));
    }

    // Detail kryptoměny podle ID
    @GetMapping("/{id}")
    public ResponseEntity<Crypto> getCryptoById(@PathVariable Integer id) throws CryptoNotFoundException {
        Crypto crypto = cryptoService.getCryptoById(id);
        if (crypto != null) {
            return ResponseEntity.ok(crypto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crypto> updateCrypto(@PathVariable Integer id, @RequestBody Crypto updatedCrypto)
            throws CryptoNotFoundException {
        Crypto crypto = cryptoService.updateCrypto(id, updatedCrypto);
        if (crypto != null) {
            return ResponseEntity.ok(crypto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Výpočet celkové hodnoty portfolia
    @GetMapping("/portfolio-value")
    public ResponseEntity<BigDecimal> getPortfolioValue() {
        return ResponseEntity.ok(cryptoService.getPortfolioValue());
    }
}