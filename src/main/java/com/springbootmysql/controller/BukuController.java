package com.springbootmysql.controller;

import com.springbootmysql.ErrorHandler.NotFound;
import com.springbootmysql.model.Buku;
import com.springbootmysql.repository.BukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BukuController {

    @Autowired
    BukuRepository bukuRepository;

    @GetMapping("/buku")
    public List<Buku> getAll(){
        return bukuRepository.findAll();
    }

    @PostMapping("/buku")
    public Buku insertBuku(@Validated @RequestBody Buku buku){
        return bukuRepository.save(buku);
    }

    @GetMapping("/buku/{buku_id}")
    public Buku getBuku(@PathVariable(value = "buku_id")int buku_id) {
        return bukuRepository.findById(buku_id).orElseThrow(() -> new NotFound("Buku", "buku_id", buku_id));
    }

    @PutMapping("/buku/{buku_id}")
    public Buku updateBuku(@PathVariable(value = "buku_id")int buku_id, @Validated @RequestBody Buku bukuDetail) {
        Buku buku = bukuRepository.findById(buku_id).orElseThrow(() -> new NotFound("Buku", "buku_id", buku_id));
        buku.setJudul(bukuDetail.getJudul());
        buku.setDeskripsi(bukuDetail.getDeskripsi());
        buku.setJumlah(bukuDetail.getJumlah());

        Buku updateBuku = bukuRepository.save(buku);
        return updateBuku;
    }

    @DeleteMapping("/buku/{buku_id}")
    public ResponseEntity<?> deleteBuku(@PathVariable(value = "buku_id") int buku_id) {
        Buku buku = bukuRepository.findById(buku_id).orElseThrow(() -> new NotFound("Buku", "buku_id", buku_id));
        bukuRepository.delete(buku);
        return ResponseEntity.ok().build();
    }
}
