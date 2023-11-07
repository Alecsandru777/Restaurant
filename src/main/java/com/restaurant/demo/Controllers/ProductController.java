package com.restaurant.demo.Controllers;

import com.restaurant.demo.Service.ProductService;
import com.restaurant.demo.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ProductController {

    private ProductService productService;

    //cartMap<seatid,List<Products> toate produsele, forma finala, ianinte de a fi grupate dupa emse
    private Map<Integer,List<Product>> cartMap = new HashMap<>();
    //Toate produsele, grupate dupa mese
    private Map<Integer,List<Product>> tableProductMap = new HashMap<>();
    //Maparea scaunelor dupa mese
    private Map<Integer,List<Integer>> tableMap = new HashMap<>();
    //Cart temporar, inainte de submit
    private Map<Integer,List<Product>> tempCart = new HashMap<>();


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
        tableMap.put(1,Arrays.asList(1,2));
        tableMap.put(2,Arrays.asList(3,4));
    }

    @GetMapping("/menu/{seatId}")
    public String displayMenu(@PathVariable int seatId, Model theModel){

        List<Product> productList = productService.findAll();
        theModel.addAttribute("products", productList);

        return "menu-products";
    }

    @GetMapping("order/{seatId}")
    public String displayOrdrer(@PathVariable int seatId, Model theModel){

        Integer total = 0;

        theModel.addAttribute("tempCart",tempCart);

        for (Product tempProduct: tempCart.get(seatId)){
            total+=tempProduct.getPretProdus();
        }
        theModel.addAttribute("total",total);

        return "view-order";

    }
    @GetMapping("/submit/{seatId}")
    public String submitOrder(@PathVariable int seatId){

        if (cartMap.get(seatId) == null){
            cartMap.put(seatId,new ArrayList<>());
        }

        if (tempCart.get(seatId) != null){
            cartMap.get(seatId).addAll(tempCart.get(seatId));
        }
        return "redirect:/order/" + seatId;
    }

    @GetMapping("/addToCart/{seatId}")
    public String addToCart(@PathVariable int seatId, @RequestParam("idProdus") int idProdus){

        tempCart.computeIfAbsent(seatId, k -> new ArrayList<>());
        tempCart.get(seatId).add(productService.findById(idProdus));
        return "redirect:/menu/" + seatId;

    }

    @GetMapping("/remove/{seatId}/{productIndex}")
    public String removeItem(@PathVariable int seatId, @PathVariable int productIndex){
        List<Product> list = tempCart.get(seatId);

        if (list != null && productIndex >= 0 && productIndex < list.size()) {
            // Remove the item at the specified index
            list.remove(productIndex);
        }
        tempCart.put(seatId,list);

        return "redirect:/order/" +seatId;

    }
    @GetMapping("/orders")
    public String orders(Model theModel) {

        for (Integer currTable : tableMap.keySet()){
            tableProductMap.put(currTable,new ArrayList<>());
        }

        for (Integer currTable : tableMap.keySet()){
            for (Integer currSeat : tableMap.get(currTable)){
                if (cartMap.get(currSeat) != null){
                    tableProductMap.get(currTable).addAll(cartMap.get(currSeat));
                }
            }
        }

        theModel.addAttribute("tableProductMap",tableProductMap);

        return "orders-overview";
    }

    @GetMapping("/meniubs")
    public String getMeniuBs(){
        return "meniu-bs";
    }

}
