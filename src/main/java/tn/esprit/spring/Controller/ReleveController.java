package tn.esprit.spring.Controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Entity.Compte;
import tn.esprit.spring.Entity.Transaction;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Service.CompteService;
import tn.esprit.spring.Service.ReleveService;
import tn.esprit.spring.Service.TransactionService;
import tn.esprit.spring.Service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("Releve")
public class ReleveController {

    private final ReleveService releveService;
    private final CompteService compteService;




    @GetMapping("/generate/{compteId}")
    public ResponseEntity<byte[]> generateReleve(@PathVariable Long compteId) {
        Compte compte = compteService.getCompteById(compteId);
        if (compte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] pdfContent = releveService.generateRelevePDF(compte);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=releve_compte_" + compteId + ".pdf");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
