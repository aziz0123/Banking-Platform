import { Component } from '@angular/core';
import { Compte } from '../../models/Compte';
import { ActivatedRoute } from '@angular/router';
import { CompteService } from '../service/compte.service';

@Component({
  selector: 'app-detail-compte',
  templateUrl: './detail-compte.component.html',
  styleUrls: ['./detail-compte.component.css']
})
export class DetailCompteComponent {
  com!:Compte;

  Compte!: Compte;
  constructor(private route: ActivatedRoute, private documentService: CompteService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam !== null) {
        const id = +idParam;
        this.documentService.getCompteById(id).subscribe(Compte => {
          this.Compte = Compte;
        });
      } else {
        console.log('Erreur : ID du compte manquant');
      }
    });
  }

}
