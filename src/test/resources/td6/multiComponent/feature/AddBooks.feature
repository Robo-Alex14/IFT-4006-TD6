#language: fr

  Fonctionnalité: Ajouter un livre
  Description optionnelle

  Scénario: Le livre ajouté est bien dans la librairie.
  Étant donnée Un livre avec le titre "Clean Code" et l'auteur "Bob" est ajouté à la librairie
  Quand le contenu de la librairie est obtenu
  Alors le livre "Clean Code" est présent
  Alors le code de statut est 200
