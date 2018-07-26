import { Component, OnInit, Input } from '@angular/core';

import {Regle} from "../shared/models/regle.model"
import {TexteStructuree} from "../shared/models/texteStructuree.model"
import {Message} from "../shared/models/message.model"
import {Probleme} from "../shared/models/probleme.model"
import {ProblemeMultiplesAppareils} from "../shared/models/ProblemeMultiplesAppareils.model"
import {EtatSystem} from "../shared/models/etatSystem.model";

@Component({
  selector: 'app-regle',
  templateUrl: './regle.component.html',
  styleUrls: ['./regle.component.css']
})
export class RegleComponent implements OnInit {

  // Variables provenant de App
  @Input() etatSystem: EtatSystem;

  selectedRegle : Number;
  setSelectedRegle : Function;
  deleteRegle : Function;

  // Gestion des onglets
  ongletActif: string = "Règles créées";
  count: Number = 0;

  // Liste de Règles
  regle01: Regle = new Regle(null, "Allumer le ventilateur à 13h", null, null, null, "22/03/2018");
  regle02: Regle = new Regle(null, "Eteindre le chauffage à minuit", null, null, null, "23/03/2018");
  regle03: Regle = new Regle(null, "Ouvrir les stores à midi", null, null, null, "23/03/2018");
  regle04: Regle = new Regle(null, "Allumer la télévision à 19h", null, null, null, "23/03/2018");
  regle05: Regle = new Regle(null, "Allumer la radio à 7h", null, null, null, "23/03/2018");
  regle06: Regle = new Regle(null, "Activer le chauffage à 18h", null, null, null, "23/03/2018");
  regle07: Regle = new Regle(null, "Fermer la porte d'entrée à 23h", null, null, null, "23/03/2018");
  regle08: Regle = new Regle(null, "Ouvrir la porte d'entrée à 6h", null, null, null, "23/03/2018");
  regle09: Regle = new Regle(null, "Fermer les stores quand il y a du soleil", null, null, null, "23/03/2018");
  regle10: Regle = new Regle(null, "Allumer la lampe musicale à 21h", null, null, null, "23/03/2018");
  regle11: Regle = new Regle(null, "Eteindre la lampe musicale à 23h", null, null, null, "23/03/2018");
  regle12: Regle = new Regle(null, "Eteindre toutes les lampes à minuit", null, null, null, "23/03/2018");
  regle13: Regle = new Regle(null, "Allumer le réveil à 6h30", null, null, null, "23/03/2018");
  regle14: Regle = new Regle(null, "Allumer l'appareil dauphin", null, null, null, "23/03/2018");
  reglesCreees: Regle[] = [this.regle01, this.regle02, this.regle03, this.regle04, this.regle05, this.regle06, this.regle07, this.regle08, this.regle09,
    this.regle10, this.regle11, this.regle12, this.regle13, this.regle14];

  constructor() {
    // Gestion de la suppresion de règles
    this.setSelectedRegle = function(index){
      this.selectedRegle = index;
      this.etatSystem.regleCourante = this.etatSystem.regles[index];
    }
    this.deleteRegle = function(index){
      this.etatSystem.regles.splice(index, 1);
      console.log(this.etatSystem);
    }
  }

  ngOnInit() {
  }

}
