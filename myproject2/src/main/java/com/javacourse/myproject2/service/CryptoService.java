package com.javacourse.myproject2.service;

import com.javacourse.myproject2.model.Crypto;
import org.springframework.stereotype.Service;

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

    public Crypto getCryptoById(Integer id) {
        return portfolio.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

//    public Optional<Crypto> getCryptoById(Integer id) {
//        return portfolio.stream()
//                .filter(c -> c.getId().equals(id))
//                .findFirst();
//    }

    public Crypto updateCrypto(Integer id, Crypto updatedCrypto) {
        for (int i = 0; i < portfolio.size(); i++) {
            Crypto existing = portfolio.get(i);
            if (existing.getId().equals(id)) {
                updatedCrypto.setId(id);          // stejne Id
                portfolio.set(i, updatedCrypto);
                return updatedCrypto;
            }
        }
        return null;
    }

    public double getPortfolioValue() {
        return portfolio.stream()
                .mapToDouble(c -> c.getPrice() * c.getQuantity())
                .sum();
    }
}
