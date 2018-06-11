import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpErrorResponse,  } from '@angular/common/http';
import {Http, Response, Headers, RequestOptions} from "@angular/http";


import {Message} from "../shared/models/message.model";
import {EtatSystem} from "../shared/models/etatSystem.model";
import {Regle} from "../shared/models/regle.model";
import {TexteStructuree} from "../shared/models/texteStructuree.model"

@Component({
  selector: 'app-dialogue',
  templateUrl: './dialogue.component.html',
  styleUrls: ['./dialogue.component.css']
})
export class DialogueComponent implements OnInit {

  // Variables provenant de App
  @Input() etatSystem: EtatSystem;

  nouvelleRegle: Function;
  envoyerRegle: Function;

  constructor(private httpService: HttpClient) {
  }

  ngOnInit() {
    // Gestion des actions du chat textuel
    this.nouvelleRegle = function(){
      var messageAccueil = new Message("Bonjour, que puis-je faire pour vous ?", new Date().toLocaleString(), "System");
      var nouvelleRegle = new Regle(null, null, null, new Array(messageAccueil), null, null);
      this.etatSystem.regleCourante = nouvelleRegle;
    }
    this.envoyerRegle = function(phraseEnvoye){
      this.etatSystem.regleCourante.texteNaturel = phraseEnvoye;
      this.etatSystem.regleCourante.estampilleTemporelle = new Date().toLocaleString();
      var messageRegle = new Message(phraseEnvoye, new Date().toLocaleString(), "User");
      this.etatSystem.regleCourante.dialogue.push(messageRegle);
      (<HTMLInputElement>document.getElementById('inputPhrase')).value = '';
      this.etatSystem.regleCourante.texteStructuree = new TexteStructuree(null, false);
      this.etatSystem.regles.push(this.etatSystem.regleCourante);

      // Ecriture du fichier JSON à envoyer au noyau fonctionnel
      var etatSystemJSON = JSON.stringify(this.etatSystem);
      console.log(this.etatSystem);
    }
  }

}
