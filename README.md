# Vocabulary - `Maven` alapú `pom` projekt Java nyelven

## Felépítés

A programszerkezet felépítése a `MVC`(Model-View-Controller) architektúra alapján 
történt.  

A szülő `pom`(***Vocabulary***) moduljai valósítják meg a `MVC` tervezési modell
elkülönülő három rétegét.  
A `View`réteg csak a `Controller`, a `Controller`réteg csak a `Model`réteggel van 
kapcsolatban.

## Leírás

### Angol és német szókincs bővítésére alkalmas program. 1000 szót tartalmaz.


Kézzel szerkesztett `.ini` kiterjesztésű fájlokból készít `.bin` kiterjesztésű 
fájlokat az első indítás alkalmával. A továbbiakban onnan tőlti be a használni 
kívánt szószedetet.


Az alapból három `.bin` kiterjesztésű szószedetből elöször csak kettő érhető el. 
A kevert ***MIX*** nyelvek, "**ENG-GER**" és "**GER-ENG**" szószedetek csak akkor, 
ha a felhasználó teljesített legalább egy-egy kört két különböző nyelvkombináción.
A három szószedet fájl hat különböző és elérhető nyelvkombinációt takar. A program 
kérésre(klikk a "**Csere**" gombon) megfordítja az aktuális szószedetet.


A program, egy egyszer megjelenő üdvözlő ablakkal és alapértelmezetten az "**ENG-HUN**"
nyelvkombinációval indul. Ezt megváltoztatni a "**Nyelv**" combo box-ból ill. a 
"**Csere**" gombra való kattintással lehet.


A "**Futam**" combo box-ból választható ki, hogy egy futam alkalmával mennyi legyen
a fordítandó szavak száma. Kezdetben ez csak `5` és `10` lehet. A "folyamatos" jó 
teljesítmény(**T%**) eredményeként ezek kiegészülnek a `20`, `30`, `40`, `50` és `100`-as 
tételekkel. pl.: `10`-es futam **90 ≤ T ≤ 100 3x** = `20`-as hozzáadva stb.

## Használat

A "**Start**" gombra való kattintás után elindul a kérdezz-felelek. Ha a válasz 
mezőbe gépelt fordítás helyes, akkor az `Enter` lenyomása után zöld színű lesz az. 
Máskülönben megjelenik a helyes fordítás pirossal. Az `Enter` ismételt lenyomásával 
kérhető a következő fordítandó szó.


A rontott szavak a futam végén újra sorra kerülnek. Ez megy mindaddig amig minden 
a futamban fordításra váró szó helyesen le nem lesz fordítva. A következő futam az 
"**Újra**" feliratú gombra való kattintással kezdeményezhető. Ezzel egyidőben 
resetelésre kerülnek a futam egyes információi.


A szavakat körönként(*minden tartalmazott szó vagy szóösszetétel helyes fordítása*
*az előre meghatározott alkalommal*), itt **legalább 2x** kell helyesen lefordítani, 
**legalább két különböző** futamban. 

## Szabályok

1. Csak ékezet nélküli betűk használhatók.
2. A **`'ß'`** karaktert **`'ss'`** karakterrel kell helyetesíteni.

## Jutalmazás

1. Nyelvkombinációkként és futamszámonként az első 100%-os teljesítményhez egy 
   felugró kis ablakban gratulál a program. Ebből 42-t lehet összegyüjteni
   (6 nyelvkombináció x 7 választható futamszám). Az addig elért gratulációk 
   számát meg is jeleníti. Ezt a felhasználó le is kérdezheti a "**show my congrat**"
   szöveg válasz mezőbe történő bevitele és az `Enter` lenyomásával ha a "**Start**" 
   gombon a "Start" felirat olvasható.

2. A feldolgozottság mértékét a program kis csillagokkal is "jutalmazza". Ezekből 
   négy féle van egy-egy `toolTipText`-el. 250 helyes fordításonként kap egyet a 
   felhasználó az ürestől a teljesig minden nyelvkombináción. Így hat megszerezhető 
   kis csillag van 24 féle variációban. A `toolTipText`-ek feliratai érdekességeket 
   ill. **Romhányi József** verseket tartalmaznak részletekben. A teljes változat 
   csak a kör teljesítésekor megkapott csillag után olvasható.

## Gyorsbillentyű

* Ha a kurzor a válasz mezőben van, akkor "**Ctrl** + **J**" billentyűkombinációval 
  a felhasználó javaslatot(fordítási, stb.) tehet. A program a javaslatokat `.ini` 
  kiterjesztésű fájlba menti nyelvkombinációnként, nyelv váltás és csere alkalmával 
  ill. kilépéskor. A programban megtalálható email címre kattintva a fájl akár 
  mellékletként el is küldhető (ha van beállított email kliens). 

## Informálás
 
* Eredményről.
* Figyelmeztetés.
* Tanács.

## Terv
 
* Akasztófa játék.
* Nehézségi szintek.
* Saját szószedet feltöltése.
* Gyorsbillentyűre segítség.

## Figyelmeztetés

* A **`data.bin`** fájl törlése az adatok elvesztésével jár!