# Application d'aide au personnes vulnérables

## Présentation

Ce projet est un logiciel d'aide aux personnes vulnérables. Il permet de gérer les personnes vulnérables et les bénévoles qui les aident.
Ce projet est réalisé dans le cadre de la 4ème année de l'INSA Toulouse.

## Fonctionnalités

- [x] Inscription d'une personne vulnérable
- [x] Inscription d'un bénévole
- [x] Connexion d'une personne vulnérable
- [x] Connexion d'un bénévole
- [ ] Connexion d'un valideur
- [ ] Page d'accueil d'une personne vulnérable
- [ ] Page d'accueil d'un bénévole
- [x] Ajout de demande d'aide par une personne vulnérable
- [ ] Validation d'une demande d'aide par un valideur
- [ ] Acceptation d'une demande d'aide par un bénévole

## Installation

### Prérequis

- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/mysql/) (pour la base de données)

### Installation

#### Depuis une release GitHub (recommandé)

1. Télécharger la dernière release sur [GitHub](https://github.com/annzzza/ConduiteProjet)
2. Lancer le fichier `Release-1.0-SNAPSHOT.jar` avec la commande `java -jar ConduiteProjet-1.0-SNAPSHOT.jar`

#### Depuis les sources

1. Cloner le projet avec la commande `git clone git@github.com:annzzza/ConduiteProjet.git`
2. Se placer dans le dossier `ConduiteProjet`
3. Lancer la commande `mvn clean install`
4. Lancer le fichier `target/ConduiteProjet-1.0.0-SNAPSHOT-shaded.jar` avec la commande `java -jar ConduiteProjet-1.0.0-SNAPSHOT-shaded.jar`


## Auteurs

- [**Ronan Bonnet**](https://github.com/BloodFutur)
- [**Anna Cazeneuve**](https://github.com/annzzza)