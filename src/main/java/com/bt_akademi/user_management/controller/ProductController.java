package com.bt_akademi.user_management.controller;

import com.bt_akademi.user_management.model.service.AbstractProductService;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// **** 13-> AbstractProductService
@RestController
@RequestMapping("gateway/product")
public class ProductController {

    @Autowired
    private AbstractProductService productService;

    @GetMapping()
    public ResponseEntity<?> getAll(){

        return ResponseEntity.ok(productService.getAll());
    }

    @DeleteMapping("delete/{productID}")
    public ResponseEntity<?> deleteByID(@PathVariable("productID") Integer productID){
        productService.deleteById(productID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> save(@RequestBody JsonElement product){
        return ResponseEntity.ok(productService.save(product));
    }
}
