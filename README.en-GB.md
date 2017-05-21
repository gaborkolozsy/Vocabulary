# Vocabulary - `Maven` based `pom` project in Java

## Screenshot

![](https://cloud.githubusercontent.com/assets/23102020/26070555/522ee78e-39a5-11e7-969a-800d23570445.png "Screenshot")

## Table of Contents

* [Screenshot](#screenshot)
* [Description](#description)
    * [Using](#use)
    * [Rules](#rules)
    * [Prize](#prize)
    * [Shortcut](#shortcut)
    * [Information](#information)
* [Construction](#construction)
    * [Dependencies](#dependencies)
    * [Files](#files)
* [Data](#data)
    * [Data handling: `DataService`](#the-dataservice-class-handling-the-data)
    * [The `Container` interface implemented classes](#the-container-interface-implemented-classes)
    * [Data store in `List`](#data-store-in-list)
    * [Data store in `Integer`](#data-store-in-integer)
* [Testability](#testability)
    * [`RegExp` pattern for test](#regexp-pattern-for-test)
    * [`String`s for test](#strings-for-test)
* [Plan](#plan)
* [Warning](#warning)
* [Footnote](#footnote)
* [License](LICENSE.txt)

# Description

## The program is able to help to expand the vocabulary.

>The program ask a question in one language and one have to answer in another.
A round of ask and answer's name is "**Round**"(Futam).

>The program starts with a welcome window once. The default language combination
is "**ENG-HUN**". It can be changed from "**Language**"(Nyelv) combo box or to
click to the "**Change**"(Csere) button.

>From "**Round**" combo box one can choose how many words have to be asked in a round. It's default
values are `5` és `10`. As a result of a continually good achievement(**A%**), they are
supplemented with the `20`, `30`, `40`, `50` and `100` items.
(e.g. if 3 times in `10` round **90 ≤ A ≤ 100** then adding the `20` item to combo box)

>The root of the "**view**" modul there are two hand-edited `.ini` file. 1000 hungarian
words were translated to english and to german. Files contain these translated words.
By the first start the program makes `.bin` files from them. Henceforth it uses those
to load the vocabulary.

>Firstly from 3 `.bin` files only available 2. The 3th ***MIX*** language combination will
available if the user does at least one ***"lap"*** of both language combination.

>The ***MIX*** means, "**ENG-GER**" language combination and the reverse "**GER-ENG**".

>The "**Lap**"(Kör) means, that the actual language combination of every words
are translated good at least twice. Program shows the actual info about it.
e.g. in "**Lap**" field **131/1000**.

>Sum of the three vocabulary files all combination available from the application.

#### Use

>After clicking the "**Start**" button start the Q & A session. Type the answer and
click `Enter`. If the answer in the answerfield is good then its color will be green.
Otherwise the program shows the good answer with red color. For the next word one
must click on the `Enter` again.

>The bad translated words will be asked again in the same round. This session keeps
as long as all of the bad translated words will be good. The next round will launch
after clicking the "**Again**"(Újra) button. At the same time some info in the
program window will be reseted.

>In one lap one has to translate at least 2 times the words at least 2 different rounds.

#### Rules

1. Use only english alphabetical characters.
2. Use **`'ss'`** characters instead of **`'ß'`**.

#### Prize

1. For the 1st 100% achievement the program congratulates in a pop up window in
   every language combination's every single round. From congrat one can collect 42
   (6 language combinations x 7 different rounds). The achieved congrat will be shown
   in this window in **13/42** format. The user can query it anytime with the
   "**show my congrat**" text. Type this text in the answer field when the
   "**Start**" button's title is "Start" and click `Enter`.

2. The rate of processed the program recompense with little stars. 4 different
   star give a part of little poem in the `toolTipText`. After every 250
   good answer the program gives from empty stars to the full ones. This is available
   on all language combination. So 6 stars are in 24 variation. The full poems of
   **József Romhányi** can be read on the full star.

#### Shortcut

>If the cursor is in the answer field then the user with the "**Ctrl** + **J**"
key combination can propose. Every propose will be stored in `.ini` file specified
on the language combination. The process will be launched under the next language
changing or exiting the program. This file can use for feedback. The email address is
in the program. To click on it will open the default email client on the user's computer.

#### Information

The program gives information about the following:

* Result
* Warning
* Advise

[⬆︎ ToC](#table-of-contents)

# Construction

>The build construction is `MVC`(Model-View-Controller). The moduls of
parent `pom`(***Vocabulary***) realizes the three layer of `MVC` software
architectural pattern. The `View` layer join only with the `Controller` which
joins with the `Model` layer.

#### Dependencies

Modul           | Dependencies
:-------------: | :------------:
`model`         | junit junit 4.10 test
`view`          | hu.gaborkolozsy controller 2.3.0
`controller`    | hu.gaborkolozsy model 2.3.0, junit junit 4.10 test

#### Files

* The root of the `view` contains 2 hand-edited `.ini` file. The program stores these
files into binary files in `ConfigService` class. The user won't see it,
so one can't change it.

    * `English-1000.ini`
    * `German-1000.ini`

* To reduce the possibility of error the project will contain the following files.
These files will be read by the `VocabularyService` class.

    * `ENG-HUN.bin` (default)
    * `GER-HUN.bin`
    * `ENG-GER.bin`

* The program saves and stores the user's data too. The `DataService` class read it
when the program starts and write when the program stops.

    * `data.bin`

* The proposal will be saved and stored as well. The `ProposalService` class will save
it when the program exits or the changes of the language combination.

    * `proposal(ABC-XYZ, 1970.01.01 123456).ini`

[⬆︎ ToC](#table-of-contents)

# Data

#### The `DataService` class handling the data.

    All data will be stored in the "data.bin" file.

* The indexes of learned words. If the value of the index is 2 then it will be added to
the list of the indexes of the learned words. This list exists in the current lap.
After the lap it will clear and the procedure starts again.

* The highest index of the **Round** combo box is when one launch the program and
the achieved plus numbers of round(e.g. `20`, `30`, `40`, `50`, `100`) should be available.

* The number between of the 90% and 100% achievements. The application will increase the
highest index of the **Round** combo box.[See above](#description)

* The list of the indexes of the learned words. [See below](#data-store-in-list)

* The number of the laps. At the start the program check the number of laps on all
of the language combination. If the program find at least 2 completed laps then 
it adds to the **Language** combo box the **MIX** text. It means that is available 
anytime the **ENG-GER** language combination or the reverse of it.

* The number of congrat. [See above](#prize)

#### The `Container` interface implemented classes.

* `DataContainerImpl` - stores all kind of the data
* `IndexValueContainerImpl` - the indexes of the words in the list(at every start are the same) their values(**0, 1, 2**)
* `RaceComboBoxContainerImpl` - stores the highest index of **Round** combo box for every single language combination
* `PerformanceContainerImpl` - stores the achievement(**A > 90%**) in every round for every single language combination

#### Data store in `List`.

* `learnedIdxs`<sup title="See footnote">[[1](#footnote)]</sup> - contains the learned words' indexes

#### Data store in `Integer`.

* `round` - the complieted laps on every single language combination
* `congratulation` - sum of the congrat

# Testability

### "Command" for the manual testing in the answer field, if the **`Start`** button's text is "Start".

#### `RegExp` pattern for test.

1. `#answer [1-2]` - in one **lap** N times any word must be translated corretly
2. `#\\d{3}`- it sets the actual language combination the list's size of the learned words(star will be shown)
3. `#race [0-6]`- it sets the `Round` combo box's highest index
4. `#add (20|30|40|50|100)`- adds the specified item to the `Round` combo box
5. `#delete (20|30|40|50|100)` - deletes the specified item from the `Round` combo box

#### `String`s for test.

1. `#reset`- resets all of the data on the actual language combination
2. `#round++`- increases the number of the completed lap the actual language combination
3. `#show flag` - shows the "LED flag"
4. `#delete flag`- deletes the "LED flag"
5. `#add MIX` - adds the "MIX" text to the `Language` combo box, and this will be availabe the "**ENG-GER**" and the "**GER-ENG**" language combo
6. `#delete MIX` - deletes the "MIX" text from the `Language` combo box
7. `show my congrat` - shows the sum of the collected congrat

[⬆︎ ToC](#table-of-contents)

# Plan

* Hangman game
* Levels(easy, hard, etc.)
* Loads the own vocabulary
* Help by shortcut for the answer

# `Warning`

    If one deletes the "data.bin" file from the root of the "View" modul then all user data will be lost!

##### Footnote
---
>[`1⬆`](#data-store-in-list) The program stores indexes of the words from the list
instead of the words. These indexes always belong to the same words. The learned
words' indexes will be deleted from the temporary list. It contains indexes between
0 and 1000. The program will pick up from the rest as much as the user sets in
the `Round` combo box. These proper indexes' words will be shown in the question field
and these words(key) will be compared with the belonged values from the answer field.
Considering the number of the right answers the program will store the indexes
and they won't be asked in the next rounds in the same lap.

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
