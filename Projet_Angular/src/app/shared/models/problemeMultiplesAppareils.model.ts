import {Probleme} from "./probleme.model"

export class ProblemeMultiplesAppareils extends Probleme {
  appareil : string;

  constructor(type: string, appareil: string) {
    super(type);

    this.appareil = appareil;
  }
}
