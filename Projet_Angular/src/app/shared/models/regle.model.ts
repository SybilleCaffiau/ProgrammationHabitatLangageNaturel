import {TexteStructuree} from "./texteStructuree.model";
import {Message} from "./message.model";
import {Probleme} from "./probleme.model";


export class Regle {
  texteStructuree: TexteStructuree;
  texteNaturel: string;
  texteOpenHab: string;
  dialogue: Message[];
  probleme: Probleme;
  estampilleTemporelle: string;

  constructor(texteStructuree: TexteStructuree, texteNaturel: string, texteOpenHab: string, dialogue: Message[], probleme: Probleme, estampilleTemporelle: string) {
        this.texteStructuree = texteStructuree;
        this.texteNaturel = texteNaturel;
        this.texteOpenHab = texteOpenHab;
        this.dialogue = dialogue;
        this.probleme = probleme;
        this.estampilleTemporelle = estampilleTemporelle;
  }

}
