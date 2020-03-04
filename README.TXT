ASIMIONESEI DANIEL 321 CD
In implementarea temei am plecat de la scheletul oferit de voi si am mai adaugat niste clase:
clasa Player care este mostenita de clasele BasicPlayer, BribedPLayer, GreedyPLayer , dar si
o clasa Help.
In clasa Main creez un vector de playeri carora le adaug strategiile, le impart rolurile
(comercian si serif) ,le distribui cartile in mana, bag cartile in sac dupa strategia
fiecarui jucator,declar bunurile din sac si sterg cartile din mana ramase, verific sacul 
primit de catre serific , iar in final adaug bonusurile si calculez scorul.Toate aceste
lucruri le fac prin apelarea unor metode din clasa Player care unele la randul lor sunt
suprascrise de metode din celelate clase.Practic in Main implementez logica jocului.
In clasa Player imi declar niste variabile de care ma folosesc in functionarea jocului,
pe care le fac private (getter & setter).Aici am implementate mai multe metode cum ar fi:
-role:imparte roluri jucatorilor;
-distributes(cu doi parametri):distribui cartile in mana jucatorilor;
-distributes(un parametru):introduc cartule in sac(aceasta metoda este suprascrisa);
-deletecard:sterg cartile din mana ramase;
-declare:declar cartile din sac;
-bagchack:verific sacul primit de serif(aceasta metoda este suprascrisa pentru ca aceasta
verificare depinde de strategie);
-addTaraba:adaug pe taraba cartile care au ramas in sac dupa verificare; 
-score:calculez scorul obtinut de pe cartile de pe taraba;
-kingQuinScore:adaug bonusul de rege si regina;
-output:afises clasamentul;
In clasa BasicPlayer suprascriu metoda distribuire , care adauga cartile in sac,declar cartile si
verific sacul primit de catre un player cu strategia "BASIC", iar in clasele BribedPlayer si 
GreedyPlayer fac aceleasi lucruri doar ca pentru strategiile "GREEDY" si "BRIBED". 
In clasa Help am implementat doua metode ajutatoare:
-frequentElem:care afla cea mai frecventa carte si numarul de aparitii;
-takeillegal:afla cartea ilegala cu cel mai mare profit; 
 