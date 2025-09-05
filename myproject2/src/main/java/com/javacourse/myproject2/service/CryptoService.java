package com.javacourse.myproject2.service;

import com.javacourse.myproject2.exception.CryptoNotFoundException;
import com.javacourse.myproject2.model.Crypto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CryptoService {
    private final List<Crypto> portfolio = new ArrayList<>();

    public void addCrypto(Crypto crypto) {
        portfolio.add(crypto);
    }

    public List<Crypto> getAllCryptos(String sortBy) {
        List<Crypto> sortedList = new ArrayList<>(portfolio);

        if (sortBy != null) {
            switch (sortBy) {
                case "price" -> sortedList.sort(Comparator.comparing(Crypto::getPrice));
                case "name" -> sortedList.sort(Comparator.comparing(Crypto::getName));
                case "quantity" -> sortedList.sort(Comparator.comparing(Crypto::getQuantity));
            }
        }
        return sortedList;
    }

    public Crypto getCryptoById(Integer id) throws CryptoNotFoundException {
        return portfolio.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CryptoNotFoundException(id));
    }

    public Crypto updateCrypto(Integer id, Crypto updatedCrypto) throws CryptoNotFoundException {
        portfolio.forEach(existing -> {
            if (existing.getId().equals(id)) {
                existing.setName(updatedCrypto.getName());
                existing.setSymbol(updatedCrypto.getSymbol());
                existing.setPrice(updatedCrypto.getPrice());
                existing.setQuantity(updatedCrypto.getQuantity());
            }
        } );
        return getCryptoById(id);
    }

    public BigDecimal getPortfolioValue() {
        return portfolio.stream()
                .map(c -> c.getPrice().multiply(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
