package com.mycode.lambda.chap11.v1;

import com.mycode.lambda.chap11.ExchangeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * jf
 * 2018/10/21 17:20
 */
public class BestPriceFinder {
    private final List<Shop> shops =
            Arrays.asList(new Shop("BestPrice"),
                    new Shop("LetsSaveBig"),
                    new Shop("MyFavoriteShop"),
                    new Shop("BuyItAll"));

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public List<String> findPriceSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getName() + "price is" + shop.getPriceAsync(product))
                .collect(toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getName() + " price is" + shop.getPrice(product))
                .collect(toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is" + shop.getPrice(product), executor))
                .collect(toList());

        List<String> prices = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
        return prices;
    }

    public List<String> findPricesInUSD(String product) {
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
        for (Shop shop : shops) {
            CompletableFuture<Double> findPricesInUSD = CompletableFuture.supplyAsync(() -> shop.getPrice(product));
            findPricesInUSD
                    .thenCombine(
                            CompletableFuture.supplyAsync(
                                    () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)

                            ), (price, rate) -> price * rate
                    );
            priceFutures.add(findPricesInUSD);
        }

        List<String> prices = priceFutures
                .stream()
                .map(CompletableFuture::join)
                .map(price -> "price is" + price)
                .collect(toList());

        return prices;

    }


    public List<String> findPricesInUSDJava7(String product) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Double>> pricesFutures = new ArrayList<>();
        for (Shop shop : shops) {
            Future<Double> futureRate = executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);

                }
            });
            Future<Double> futurePriceUSD = executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    try {
                        double priceInEUR = shop.getPrice(product);
                        return priceInEUR * futureRate.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            });

            pricesFutures.add(futurePriceUSD);
        }

        List<String> prices = new ArrayList<>();
        for (Future<Double> priceFuture : pricesFutures) {
            try {
                prices.add("price is " + priceFuture.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return prices;
    }

    public List<String> findPricesInUSD2(String product) {
        List<CompletableFuture<String>> priceFutures = new ArrayList<>();
        for (Shop shop : shops) {
            CompletableFuture<String> futurePriceInUSD = CompletableFuture.supplyAsync(() -> shop.getPrice(product))
                    .thenCombine(
                            CompletableFuture.supplyAsync(
                                    () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)
                            ),
                            (price, rate) -> price * rate
                    ).thenApply(price -> shop.getName() + " price is " + price);
            priceFutures.add(futurePriceInUSD);
        }

        List<String> prices = priceFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(toList());

        return prices;
    }

    public List<String> findPricesInUSD3(String product) {
        Stream<CompletableFuture<String>> priceFuturesStream =
                shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product)
                        ).thenCombine(
                        CompletableFuture.supplyAsync(() -> ExchangeService
                                .getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD))
                        , (price, rate) -> price * rate)
                                .thenApply(price -> shop.getName() + " price is " + price)

                );

        List<CompletableFuture<String>> priceFutures = priceFuturesStream.collect(toList());
        List<String> prices = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
        return prices;
    }

}


