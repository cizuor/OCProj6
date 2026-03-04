<p align="center">
   <img src="./front/src/favicon.png" width="192px" />
</p>

# MicroCRM (P7 - Développeur Full-Stack - Java et Angular - Mettez en œuvre l'intégration et le déploiement continu d'une application Full-Stack)

MicroCRM est une application de démonstration basique ayant pour être objectif de servir de socle pour le module "P7 - Développeur Full-Stack".

L'application MicroCRM est une implémentation simplifiée d'un ["CRM" (Customer Relationship Management)](https://fr.wikipedia.org/wiki/Gestion_de_la_relation_client). Les fonctionnalités sont limitées à la création, édition et la visualisations des individus liés à des organisations.

![Page d'accueil](./misc/screenshots/screenshot_1.png)
![Édition de la fiche d'un individu](./misc/screenshots/screenshot_2.png)

## Code source

### Organisation

Ce [monorepo](https://en.wikipedia.org/wiki/Monorepo) contient les 2 composantes du projet "MicroCRM":

- La partie serveur (ou "backend"), en Java SpringBoot 3;
- La partie cliente (ou "frontend"), en Angular 17.

### Démarrer avec les sources

#### Serveur

##### Dépendances

- [OpenJDK >= 17](https://openjdk.org/)

##### Procédure

1. Se positionner dans le répertoire `back` avec une invite de commande:

   ```shell
   cd back
   ```

2. Construire le JAR:

   ```shell
   # Sur Linux
   ./gradlew build

   # Sur Windows
   gradlew.bat build
   ```

3. Démarrer le service:

   ```shell
   java -jar build/libs/microcrm-0.0.1-SNAPSHOT.jar
   ```

Puis ouvrir l'URL http://localhost:8080 dans votre navigateur.

#### Client

##### Dépendances

- [NPM >= 10.2.4](https://www.npmjs.com/)

##### Procédure

1. Se positionner dans le répertoire `front` avec une invite de commande:

   ```shell
   cd front
   ```

2. (La première fois seulement) Installer les dépendances NodeJS:

   ```shell
   npm install
   ```

3. Démarrer le service de développement:

   ```shell
   npx @angular/cli serve
   ```

Puis ouvrir l'URL http://localhost:4200 dans votre navigateur.

### Exécution des tests

#### Client

**Dépendances**

- Google Chrome ou Chromium

Dans votre terminal:

```shell
cd front
CHROME_BIN=</path/to/google/chrome> npm test
```

#### Serveur

Dans votre terminal:

```shell
cd back
./gradlew test
```

### Images Docker

#### Client

##### Construire l'image

```shell
docker build --target front -t orion-microcrm-front:latest .
```

##### Exécuter l'image

```shell
docker run -it --rm -p 80:80 -p 443:443 orion-microcrm-front:latest
```

L'application sera disponible sur https://localhost.

#### Serveur

##### Construire l'image

```shell
docker build --target back -t orion-microcrm-back:latest .
```

##### Exécuter l'image

```shell
docker run -it --rm -p 8080:8080 orion-microcrm-back:latest
```

L'API sera disponible sur http://localhost:8080.

#### Tout en un

```shell
docker build --target standalone -t orion-microcrm-standalone:latest .
```

##### Exécuter l'image

```shell
docker run -it --rm -p 8080:8080 -p 80:80 -p 443:443 orion-microcrm-standalone:latest
```

L'application sera disponible sur https://localhost et l'API sur http://localhost:8080.




# MicroCRM - Application Full-Stack Industrialisée

## 📝 Présentation
**MicroCRM** est une solution de gestion de la relation client (CRM) permettant de piloter des personnes et des organisations. Ce projet a été conçu dans une démarche d'industrialisation logicielle poussée, incluant une automatisation complète du cycle de vie du produit.

## 🏗️ Architecture Technique
L'application repose sur une architecture **Monorepo** moderne :

- **Backend** : Java 17, Spring Boot 3.2.5, Gradle 8.7.
- **Frontend** : Angular 14, TypeScript.
- **Base de données** : PostgreSQL 13 (future) et HSQLDB (Tests/CI).
- **Proxy Inverse** : Caddy Server (gestion du routage et des fichiers statiques).
- **Registre** : GitHub Container Registry (GHCR).

---

## 🚀 Installation et Lancement Rapide

### Prérequis
- Docker Desktop (activé avec le moteur WSL 2 sur Windows).
- Un accès réseau pour récupérer les images sur GHCR.

### Lancement via Docker Compose
Pour déployer l'application complète sans avoir besoin de compiler le code source localement :

```bash
# 1. Récupérer les dernières images certifiées par le pipeline CI
docker compose pull

# 2. Lancer l'application et sa base de données en arrière-plan
docker compose up -d


```

Interface Web : http://localhost (Port 80)
API Backend : http://localhost/api (via le Reverse Proxy Caddy)



## 🛠️ Pipeline CI/CD (GitHub Actions)
Le workflow `.github/workflows/ci.yml` orchestre l'intégration continue de manière robuste :

*   **Unit Tests** : Exécution des tests JUnit (Back) et Jasmine (Front) avec mise en cache des dépendances.
*   **Quality Gate** : Analyse statique complète via **SonarCloud** (Sécurité, Bugs, Dette technique).
*   **E2E Tests** : Validation fonctionnelle via **Cypress** sur l'application assemblée (Back + Front + DB).
*   **Semantic Release** : Calcul automatique du numéro de version et génération du Changelog GitHub.
*   **Docker Build & Push** : Construction d'images multi-stage optimisées et publication sur **GHCR** avec tags sémantiques.

### ⚙️ CI/CD – Variables et secrets requis

Pour rendre le pipeline opérationnel, les configurations suivantes sont indispensables :

#### 1. Secrets GitHub
Ajoutez le secret suivant pour permettre l'analyse SonarCloud :
**Chemin :** `Settings` ➔ `Secrets and variables` ➔ `Actions` ➔ `New repository secret`

| Nom du Secret | Description |
| **SONAR_TOKEN** | Jeton d'authentification (obligatoire) à générer sur [SonarCloud.io](https://sonarcloud.io). |

#### 2. Configuration SonarCloud (`sonar-project.properties`)
Assurez-vous que le fichier `sonar-project.properties` à la racine du projet contient les bonnes références à votre compte SonarCloud :

#### 3. Permissions du Workflow
Pour permettre la publication des rapports de tests et des images Docker, configurez les permissions :
Chemin : Settings ➔ Actions ➔ General ➔ section Workflow permissions

    Cochez : Read and write permissions.



## 📊 Monitoring (Stack ELK)
Une stack de surveillance (Elasticsearch, Logstash, Kibana) est intégrée pour centraliser les logs applicatifs.

### Lancer le monitoring
```bash
docker compose -f docker-compose.yml -f docker-compose-elk.yml up -d
```

*   **Kibana Dashboard** : [http://localhost:5601](http://localhost:5601)
*   **Métriques suivies** : Répartition des erreurs, volume de logs, fréquence des événements.

## 🔒 Sécurité et Qualité

*   **Analyse SonarSource** : Suivi rigoureux des vulnérabilités et des "Code Smells".
*   **Subresource Integrity (SRI)** : Protection contre les attaques de type *Supply Chain* sur les bibliothèques externes.
*   **Isolation Réseau** : Séparation logicielle entre le réseau public (Reverse Proxy) et le réseau de données (Base de données isolée).
*   **Versioning Immuable** : Chaque déploiement est lié à un tag Docker précis permettant un rollback immédiat.