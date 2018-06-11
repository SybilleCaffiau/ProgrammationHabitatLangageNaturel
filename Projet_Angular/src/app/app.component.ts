import { Component, Input } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import {EtatSystem} from "./shared/models/etatSystem.model";
import {Regle} from "./shared/models/regle.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = "Programmation de l'habitat en langage naturel";

  constructor(private httpService: HttpClient) {

  }

  etatSystem: EtatSystem;

  ngOnInit() {
    // Read JSON file
    this.httpService.get('assets/json/etatSystemRecu.json').subscribe(
      data => {
        this.etatSystem = data as EtatSystem;
        console.log(this.etatSystem);
      },
      (err: HttpErrorResponse) => {
        console.log(err.message);
      }
    );
  }
}
