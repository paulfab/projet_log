# projet_log
## Pour lancer le code
* la classe main, est la classe Maine
* mettre le bool `coordinate` a `False` (ligne 16) si on veut faire les calculs de distance entre deux fontaines, a vol d'oiseau, pour utiliser les distances données par Google Maps le mettre a `True`
* choisir ensuite l'algorithme de calcul, (ligne 63-67), 
  * l'algorithme de  la solution exact, n'est  possible que pour un nombre de fontaine égal à 5
  * solution_2_opt et solution_ant  peuvent se mettre a la suite, pour appliquer les 2 algorithmes en cascade
* le resultat est visile dans `stdout`, l'avant derniere ligne est la succession des fontaines, et la derniere ligne, le cout en metre
* On peut voir le resultat sur une carte google maps si `coordinate` est  a `True` dans le fichier `carte.html` et les insctructions sont dans le fichier `instructions.html`
