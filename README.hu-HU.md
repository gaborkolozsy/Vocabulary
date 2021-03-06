# Vocabulary - `Maven` alapú `pom` projekt Java nyelven

## Képernyőkép

![](https://cloud.githubusercontent.com/assets/23102020/26070555/522ee78e-39a5-11e7-969a-800d23570445.png "Screenshot")

## Tartalom

* [Képernyőkép](#képernyőkép)
* [Leírás](#leírás)
    * [Használat](#használat)
    * [Szabályok](#szabályok)
    * [Jutalmazás](#jutalmazás)
    * [Gyorsbillentyű](#gyorsbillentyű)
    * [Informálás](#informálás)
* [Felépítés](#felépítés)
    * [Függőségek](#függőségek) 
    * [Fájlok](#fájlok)
* [Adatok](#adatok)
    * [Az adatok kezelése: `DataService`](#az-adatokat-a-dataservice-osztály-kezeli)
    * [A `Container` interface-t megvalósító osztályok](#a-container-interface-t-megvalósító-osztályok)
    * [`List`-ben tárolt adat](#list-ben-tárolt-adat)
    * [`Integer`-ként tárolt adatok](#integer-ként-tárolt-adatok)
* [Tesztelhetőség](#tesztelhetőség)
    * [`RegExp` minták a teszteléshez](#regexp-minták-a-teszteléshez)
    * [`String`-ek a teszteléshez](#string-ek-a-teszteléshez)
* [Terv](#terv)
* [Figyelmeztetés](#figyelmeztetés)
* [Lábjegyzet](#lábjegyzet)
* [License](LICENSE.txt)

# Leírás

## Angol és német szókincs bővítésére alkalmas program.

>A program feltesz egy kérdést az egyik nyelven, és meg kell válaszolni a másikon. 
Egy sorozatnyi ilyen kérdés-válasz neve a programban "**Futam**".

>A program, egy egyszer megjelenő üdvözlő ablakkal és alapértelmezetten az "**ENG-HUN**"
nyelvkombinációval indul. Ezt megváltoztatni a "**Nyelv**" combo box-ból ill. a 
"**Csere**" gombra való kattintással lehet.

>A "**Futam**" combo box-ból választható ki, hogy egy futam alkalmával mennyi legyen
a fordítandó szavak száma. Kezdetben ez csak `5` és `10` lehet. A "folyamatos" jó 
teljesítmény(**T%**) eredményeként ezek kiegészülnek a `20`, `30`, `40`, `50` és `100`-as 
tételekkel. pl.: `10`-es futam **90 ≤ T ≤ 100 3x** = `20`-as tétel hozzáadva stb.

>A "**view**" modul gyökerében található két, kézzel szerkesztett `.ini` fájlban 
ugyanaz az 1000 magyar szó került lefordításra mind német mind angol nyelvre.
A program ezekből készít `.bin` kiterjesztésű fájlokat az első indítás alkalmával. 
A továbbiakban onnan tölti be a használni kívánt szószedetet.

>Az alapból három `.bin` kiterjesztésű szószedetből elöször csak kettő érhető el
(de az első indításkor létrehozza mindhármat). 
A kevert ***MIX*** nyelv, "**ENG-GER**" és annak fordítottja, "**GER-ENG**" szószedetek csak akkor, 
ha a felhasználó teljesített legalább egy-egy ***"kört"*** két különböző nyelvkombináción.

>A "**Kör**" itt azt jelenti, hogy a nyelvpáron elérhető minden szót legalább
2x jól lefordított a felhasználó.<br>
Az aktuális információ elérhető a programban(pl.: **131/1000**).

>A három szószedet fájl tehát hat különböző és elérhető nyelvkombinációt takar.

#### Használat

>A "**Start**" gombra való kattintás után elindul a kérdezz-felelek. Ha a válaszmezőbe 
gépelt fordítás helyes, akkor az `Enter` lenyomása után zöld színű lesz az. 
Máskülönben megjelenik a helyes fordítás pirossal. Az `Enter` ismételt lenyomásával 
kérhető a következő fordítandó szó.

>A rontott szavak a futam végén újra sorra kerülnek. Ez megy mindaddig amíg minden 
a futamban fordításra váró szó helyesen le nem lesz fordítva. A következő futam az 
"**Újra**" feliratú gombra való kattintással kezdeményezhető. Ezzel egyidőben 
reszetelésre kerülnek a futam egyes információi.

>A szavakat körönként **legalább 2x** kell helyesen lefordítani, **legalább két különböző** 
futamban. 

#### Szabályok

1. Csak ékezet nélküli betűk használhatóak.
2. A **`'ß'`** karaktert **`'ss'`** karakterrel kell helyetesíteni.

#### Jutalmazás

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

#### Gyorsbillentyű

>Ha a kurzor a válasz mezőben van, akkor "**Ctrl** + **J**" billentyűkombinációval 
a felhasználó javaslatot(fordítási, stb.) tehet. A program a javaslatokat `.ini` 
kiterjesztésű fájlba menti nyelvkombinációnként, nyelv váltás és csere alkalmával 
ill. kilépéskor. A programban megtalálható email címre kattintva a fájl akár 
mellékletként el is küldhető (ha van beállított email kliens). 

#### Informálás

A program felugró ablakokban informálja a felhasználót a következőkről:

* Eredmény
* Figyelmeztetés
* Tanács

[⬆︎](#tartalom)

# Felépítés

>A programszerkezet felépítése a `MVC`(Model-View-Controller) architektúra alapján 
történt. A szülő `pom`(***Vocabulary***) moduljai valósítják meg a `MVC` tervezési 
modell elkülönülő három rétegét. A `View`réteg csak a `Controller`, a `Controller`
réteg csak a `Model`réteggel van kapcsolatban.

#### Függőségek
Modul           | Függőség(ek)
:-------------: | :------------:
`model`         | junit junit 4.10 test
`view`          | hu.gaborkolozsy controller 2.3.0
`controller`    | hu.gaborkolozsy model 2.3.0, junit junit 4.10 test

#### Fájlok

* A `view` modul gyökerében található a kézzel megszerkesztett két `.ini` fájl. 
Ezeket a program a `ConfigService` osztályban alakítja át és menti bináris fájlba. 
A "felhasználó" már nem találkozik majd velük, így azt megváltoztatni nem tudja.

   * `English-1000.ini`
   * `German-1000.ini`

* A hibalehetőségek csökkentése érdekében csak a következő fájlok kerülnek a ***"végleges"*** 
verzióba. Ezen fájlokat a `VocabularyService` osztály olvassa be.

   * `ENG-HUN.bin` (alapértelmezett)
   * `GER-HUN.bin`
   * `ENG-GER.bin`

* A program ide menti a felhasználó elért eredményeit, adatait is. A `DataService` 
osztály olvassa be indításkor(**Vocabulary konstruktor**) és írja ki 
leállításkor(**Vocabulary main**).

   * `data.bin`

* A javaslatok ide kerülnek. A `ProposalService` osztály menti kilépéskor 
ill. a nyelvkombináció megváltozásakor.

   * `proposal(ABC-XYZ, 1970.01.01 123456).ini` 

[⬆︎](#tartalom)

# Adatok

#### Az adatokat a `DataService` osztály kezeli.

    Az adatok a program használatakor keletkeznek és a "data.bin" fájlban tárolódnak.

* A tanult szavak azonosító indexei, hogy ne kérdezze ki a program többször körönként 
mint a beállított érték(itt 2). Ha az érték 2, akkor törlödik az átmeneti tárolóból és hozzáadódik a 
tanult szavak indexeit tároló listához.

* A **Futam** combo box legmagasabb indexe, hogy indításkor a már egyszer megkapott plusz
(`20`, `30`, `40`, `50`, `100`) futamszámok elérhetőek legyenek.

* A futamon elért legalább 90%-os teljesítmények száma. 3-nál növeli a program a **Futam** 
combo box indexét.[Lásd feljebb](#angol-és-német-szókincs-bővítésére-alkalmas-program)

* A tanult szavak indexeinek listája. [Lásd lejebb](#list-ben-tárolt-adat) 

* Körök száma. A program indításkor ellenőrzi, hogy van-e olyan nyelvpár ami már 
legalább 1x teljesítve lett. Ha talál legalább két különböző ilyen nyelvpárt, akkor hozzáadja
a **Nyelv** combo box-hoz a **MIX** feliratott. Így onnantól bármikor elérhető lesz
a **ENG-GER** nyelvkombináció ill. annak fordítottja.

* Gratulációk száma. A nyelvpárok futamszámain elért első 100%-os eredményért jár.
`6` nyelvpár és `7` futamszám esetén az összegyűjthető gratulációk száma `42`. [Lásd feljebb](#jutalmazás)

#### A `Container` interface-t megvalósító osztályok.

* `DataContainerImpl` - tárol minden adatot
* `IndexValueContainerImpl` - a szavak listában elfoglalt helye szerinti index(minden indításkor ua.) értéke(**0, 1, 2**)
* `RaceComboBoxContainerImpl` - a `Futam` combo box legmagasabb indexét tárolja nyelvpáronként
* `PerformanceContainerImpl` - az egyes futamszámokon elért teljesítmény(**T > 90%**) számát tárolja nyelvpáronként

#### `List`-ben tárolt adat.

* `learnedIdxs`<sup title="Lásd lábjegyzet">[[1](#lábjegyzet)]</sup> - a **körben** már ***"megtanult"*** szavak, listában elfoglalt helye szerinti indexeiket tárolja

#### `Integer`-ként tárolt adatok.

* `round` - a teljesített körök száma nyelvpáronként
* `congratulation` - az összes megszerzett gratulációk száma<br>

# Tesztelhetőség

### A tesztelés megkönnyítésére kiadható "parancsok" a válasz mezőben ha a **`Start`** gombon a "Start" felirat olvasható.

#### `RegExp` minták a teszteléshez.

1. `#answer [1-2]` - 1 **körben** N alakalommal kell lefordítani helyesen egy szót
2. `#\\d{3}`- beállítja az aktuális nyelvkombináción a tanult indexek lista méretét és ennek megfelelően a "jutalom csillag" is megjelenik
3. `#race [0-6]`- beállítja a `Futam` combo box maximális indexét
4. `#add (20|30|40|50|100)`- hozzáadja a kiválasztott tételt a `Futam` combo boxhoz
5. `#delete (20|30|40|50|100)` - törli a kiválasztott tételt a `Futam` combo boxból

#### `String`-ek a teszteléshez 

1. `#reset`- reszeteli az aktuális nyelvkombinációt
2. `#round++`- növeli az aktális nyelvkombináción a teljesített körök számát
3. `#show flag` - lefut a kör végén megjelenő "LED zászló"
4. `#delete flag`- törli a "LED zászló"-t
5. `#add MIX` - hozzáadja a "MIX" feliratott a `Nyelv` combo boxhoz és így elérhető a "**ENG-GER**" és a "**GER-ENG**" nyelvkombináció
6. `#delete MIX` - törli a "MIX" feliratot a `Nyelv` combo boxból
7. `show my congrat` - informál az összegyűjtött gratulációk számáról

[⬆︎](#tartalom)

# Terv
 
* Akasztófa játék
* Nehézségi szintek
* Saját szószedet feltöltése
* Gyorsbillentyűre segítség

# `Figyelmeztetés`

    A "View" modul gyökerében található "data.bin" fájl törlése az adatok elvesztésével jár!

##### Lábjegyzet

>[`1⬆`](#list-ben-tárolt-adat) Nem szavakat tárol a program hanem a listában elfoglalt indexeiket. Ezen
indexek mindig ugyanahhoz a szóhoz tartoznak. A már megtanult szavak indexeit a 
program törli egy ideiglenesen létrehozott tárolóból ami 0-1000 között tartalmazz 
"indexeket". A maradékból(a még meg nem tanult szavak indexei) pedig véletlenszerüen 
választja ki a futamszámnak megfelelő számú indexet. Ezen indexek alatt található 
szavak kerülnek a **keyList**-ből a **kérdés** mezőbe és ezen szavakhoz(kulcs) 
tartozó értékekkel(**VocabularyService** **vocabulary** adattagja) hasonlítja 
össze a program a **válasz** mezőben található szavakat.<br>
A helyes válaszok számát figyelembe véve az indexeket eltárolja a program és a 
lehetséges következő futamokban már nem kérdezi ki ugyanazon körben.

# &nbsp;
<br>
<p align="center">
    <sup>
        <strong>Information about me on </strong>
    </sup>
    <a href="https://www.linkedin.com/in/g%C3%A1bor-kolozsy-950484115/">
        <img src="https://img.shields.io/badge/Linked-In-red.svg?colorA=000000&colorB=0077b5">
    </a>
</p>
<p align="center">
    <a href="https://github.com/gaborkolozsy">
        <img src="https://cloud.githubusercontent.com/assets/23102020/26025948/88e8d276-37f2-11e7-8e53-a25c0624a8da.png" width="50">
    </a>
</p>
