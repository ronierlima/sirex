/*
 *  Copyright 2009-2022 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package br.gov.pe.ses.starter.controladores.ultima.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.gov.pe.ses.starter.controladores.ultima.domain.InventoryStatus;
import br.gov.pe.ses.starter.controladores.ultima.domain.Product;
import jakarta.annotation.PostConstruct;



import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
public class ProductService {

    List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
        products.add(new Product(1000, "f230fh0g3", "Bamboo Watch", "Product Description", "bamboo-watch.jpg", 65, "Accessories", 24, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1001, "nvklal433", "Black Watch", "Product Description", "black-watch.jpg", 72, "Accessories", 61, InventoryStatus.OUTOFSTOCK, 4));
        products.add(new Product(1002, "zz21cz3c1", "Blue Band", "Product Description", "blue-band.jpg", 79, "Fitness", 2, InventoryStatus.LOWSTOCK, 3));
        products.add(new Product(1003, "244wgerg2", "Blue T-Shirt", "Product Description", "blue-t-shirt.jpg", 29, "Clothing", 25, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1004, "h456wer53", "Bracelet", "Product Description", "bracelet.jpg", 15, "Accessories", 73, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1005, "av2231fwg", "Brown Purse", "Product Description", "brown-purse.jpg", 120, "Accessories", 0, InventoryStatus.OUTOFSTOCK, 4));
        products.add(new Product(1006, "bib36pfvm", "Chakra Bracelet", "Product Description", "chakra-bracelet.jpg", 32, "Accessories", 5, InventoryStatus.LOWSTOCK, 3));
        products.add(new Product(1007, "mbvjkgip5", "Galaxy Earrings", "Product Description", "galaxy-earrings.jpg", 34, "Accessories", 23, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1008, "vbb124btr", "Game Controller", "Product Description", "game-controller.jpg", 99, "Electronics", 2, InventoryStatus.LOWSTOCK, 4));
        products.add(new Product(1009, "cm230f032", "Gaming Set", "Product Description", "gaming-set.jpg", 299, "Electronics", 63, InventoryStatus.INSTOCK, 3));
        products.add(new Product(1010, "plb34234v", "Gold Phone Case", "Product Description", "gold-phone-case.jpg", 24, "Accessories", 0, InventoryStatus.OUTOFSTOCK, 4));
        products.add(new Product(1011, "4920nnc2d", "Green Earbuds", "Product Description", "green-earbuds.jpg", 89, "Electronics", 23, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1012, "250vm23cc", "Green T-Shirt", "Product Description", "green-t-shirt.jpg", 49, "Clothing", 74, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1013, "fldsmn31b", "Grey T-Shirt", "Product Description", "grey-t-shirt.jpg", 48, "Clothing", 0, InventoryStatus.OUTOFSTOCK, 3));
        products.add(new Product(1014, "waas1x2as", "Headphones", "Product Description", "headphones.jpg", 175, "Electronics", 8, InventoryStatus.LOWSTOCK, 5));
        products.add(new Product(1015, "vb34btbg5", "Light Green T-Shirt", "Product Description", "light-green-t-shirt.jpg", 49, "Clothing", 34, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1016, "k8l6j58jl", "Lime Band", "Product Description", "lime-band.jpg", 79, "Fitness", 12, InventoryStatus.INSTOCK, 3));
        products.add(new Product(1017, "v435nn85n", "Mini Speakers", "Product Description", "mini-speakers.jpg", 85, "Clothing", 42, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1018, "09zx9c0zc", "Painted Phone Case", "Product Description", "painted-phone-case.jpg", 56, "Accessories", 41, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1019, "mnb5mb2m5", "Pink Band", "Product Description", "pink-band.jpg", 79, "Fitness", 63, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1020, "r23fwf2w3", "Pink Purse", "Product Description", "pink-purse.jpg", 110, "Accessories", 0, InventoryStatus.OUTOFSTOCK, 4));
        products.add(new Product(1021, "pxpzczo23", "Purple Band", "Product Description", "purple-band.jpg", 79, "Fitness", 6, InventoryStatus.LOWSTOCK, 3));
        products.add(new Product(1022, "2c42cb5cb", "Purple Gemstone Necklace", "Product Description", "purple-gemstone-necklace.jpg", 45, "Accessories", 62, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1023, "5k43kkk23", "Purple T-Shirt", "Product Description", "purple-t-shirt.jpg", 49, "Clothing", 2, InventoryStatus.LOWSTOCK, 5));
        products.add(new Product(1024, "lm2tny2k4", "Shoes", "Product Description", "shoes.jpg", 64, "Clothing", 0, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1025, "nbm5mv45n", "Sneakers", "Product Description", "sneakers.jpg", 78, "Clothing", 52, InventoryStatus.INSTOCK, 4));
        products.add(new Product(1026, "zx23zc42c", "Teal T-Shirt", "Product Description", "teal-t-shirt.jpg", 49, "Clothing", 3, InventoryStatus.LOWSTOCK, 3));
        products.add(new Product(1027, "acvx872gc", "Yellow Earbuds", "Product Description", "yellow-earbuds.jpg", 89, "Electronics", 35, InventoryStatus.INSTOCK, 3));
        products.add(new Product(1028, "tx125ck42", "Yoga Mat", "Product Description", "yoga-mat.jpg", 20, "Fitness", 15, InventoryStatus.INSTOCK, 5));
        products.add(new Product(1029, "gwuby345v", "Yoga Set", "Product Description", "yoga-set.jpg", 20, "Fitness", 25, InventoryStatus.INSTOCK, 8));

    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> getProducts(int size) {

        if (size > products.size()) {
            Random rand = new Random();

            List<Product> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(products.size());
                randomList.add(products.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(products.subList(0, size));
        }

    }

    public List<Product> getClonedProducts(int size) {
        List<Product> results = new ArrayList<>();
        List<Product> originals = getProducts(size);
        for (Product original : originals) {
            results.add(original.clone());
        }
        return results;
    }
}
