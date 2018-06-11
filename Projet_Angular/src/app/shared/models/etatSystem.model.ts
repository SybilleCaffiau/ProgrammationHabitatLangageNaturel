import {Regle} from "./regle.model";

export class EtatSystem {
  regleCourante: Regle;
  regles: Regle[];

  constructor(regleCourante: Regle, regles: Regle[]) {
    this.regleCourante = regleCourante;
    this.regles = regles;
  }

  assigner() {
    //Object.assign(this, data);
    console.log("YES");
  }

}
