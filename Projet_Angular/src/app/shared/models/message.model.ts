export class Message {
  texte: string;
  date: string;
  auteur: string;

  constructor(texte: string, date: string, auteur: string) {
        this.texte = texte;
        this.date = date;
        this.auteur = auteur;
  }
}
