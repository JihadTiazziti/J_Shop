# J-Shop — Application Android de catalogue et panier

**Kotlin · Interfaces XML · SQLite · Android**

J-Shop est un prototype Android de boutique permettant de parcourir un catalogue local, rechercher et trier des produits, puis gérer un panier en mémoire. Le projet comprend également des écrans de connexion, d’inscription et de réinitialisation du mot de passe, dont certains parcours restent à compléter.

> **État de cette documentation :** vérification statique du code au commit [`21b843c`](https://github.com/JihadTiazziti/J_Shop/commit/21b843c81bdf157734217b3b52ef32f11a3b8dee), intitulé « first commit » (6 juillet 2025). La compilation debug a ensuite réussi le 26 octobre 2026 dans GitHub Actions, au commit `bad5f2d` (sources applicatives inchangées). Le fonctionnement sur appareil n’a pas été testé ; une compilation réussie ne valide pas les parcours utilisateur.

## Fonctionnalités présentes dans le code

| Fonctionnalité | Implémentation observée |
| --- | --- |
| Catalogue local | 12 produits initialisés dans SQLite, avec nom, prix et image embarquée. Affichage en grille de deux colonnes. |
| Recherche | Recherche par nom, insensible à la casse, avec suggestions dans un `AutoCompleteTextView`. |
| Tri | Prix croissant/décroissant et nom croissant/décroissant. Le tri repart du catalogue complet, sans conserver le résultat de recherche. |
| Ajout au panier | Ajout d’un produit ; un nouvel ajout du même identifiant augmente sa quantité. |
| Gestion des quantités | Boutons +/−, total par article et total du panier affichés en euros. |
| Vidage du panier | Suppression de tous les articles depuis l’écran panier. |
| Connexion locale de démonstration | Vérification de l’email et du mot de passe dans une base SQLite locale. |
| Navigation | Accès au catalogue et au panier ; l’onglet « Profil » ouvre l’écran de connexion. |

Sources : [MarketActivity.kt](app/src/main/java/com/example/j_shop/MarketActivity.kt), [ProductDatabaseHelper.kt](app/src/main/java/com/example/j_shop/ProductDatabaseHelper.kt), [CartManager.kt](app/src/main/java/com/example/j_shop/CartManager.kt), [CartActivity.kt](app/src/main/java/com/example/j_shop/CartActivity.kt), [CartAdapter.kt](app/src/main/java/com/example/j_shop/CartAdapter.kt) et [LoginActivity.kt](app/src/main/java/com/example/j_shop/LoginActivity.kt).

## Limites et parcours incomplets

- **Inscription :** le formulaire vérifie les champs non vides et l’acceptation des conditions, puis ouvre le catalogue. Il n’appelle pas `UserDatabaseHelper.addUser()` : aucun compte n’est enregistré par ce parcours.
- **Mot de passe oublié :** une boîte de dialogue accepte un nouveau mot de passe, mais la méthode de réinitialisation affiche uniquement un message de réussite. Elle ne modifie pas la base et n’envoie aucun email.
- **Google / Facebook :** les boutons affichent des messages `Toast`. Aucune authentification sociale n’est intégrée.
- **Panier :** les données sont conservées dans le singleton `CartManager`, sans persistance sur disque. Elles ne survivent pas à l’arrêt du processus.
- **Suppression d’un article :** le dialogue de confirmation existe, mais la suppression touche la copie de liste affichée, pas celle de `CartManager`. D’après le code, l’article peut réapparaître à la réouverture du panier ; ce comportement reste à reproduire sur appareil.
- **Sécurité de la connexion :** les mots de passe sont stockés en clair dans SQLite. Ce mécanisme convient uniquement à une démonstration locale, pas à de vrais comptes utilisateurs.
- **Écran de démarrage :** `SplashActivity` existe, mais n’est pas déclarée dans le manifeste. Le lancement actuel pointe vers `LoginActivity`.
- **Périmètre :** aucun paiement, passage de commande, backend distant, Firebase, Room ou appel d’API REST n’a été identifié dans cette version.

Sources complémentaires : [RegisterActivity.kt](app/src/main/java/com/example/j_shop/RegisterActivity.kt), [activity_forgot_password.kt](app/src/main/java/com/example/j_shop/activity_forgot_password.kt), [UserDatabaseHelper.kt](app/src/main/java/com/example/j_shop/UserDatabaseHelper.kt) et [AndroidManifest.xml](app/src/main/AndroidManifest.xml).

## Technologies et configuration

| Élément | Configuration du dépôt |
| --- | --- |
| Langage | Kotlin 2.0.21 |
| Interface | Layouts XML, Android Views, AppCompat, Material Components, ConstraintLayout |
| Listes | RecyclerView, `GridLayoutManager` et `LinearLayoutManager` |
| Données locales | SQLite via `SQLiteOpenHelper` |
| Build | Gradle Kotlin DSL, Gradle Wrapper 8.11.1, Android Gradle Plugin 8.10.0 |
| Android | `minSdk = 24`, `compileSdk = 35`, `targetSdk = 35` |
| Cible Java / Kotlin | Java 11 et `jvmTarget = "11"` ; cette cible ne désigne pas le JDK utilisé pour exécuter Gradle |
| Identifiant de l’application | `com.example.j_shop` |
| Tests présents | Exemples JUnit et Android instrumenté ; aucune couverture des parcours métier démontrée |

Les versions sont définies dans [app/build.gradle.kts](app/build.gradle.kts), [libs.versions.toml](gradle/libs.versions.toml) et [gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties). Plusieurs versions de Material Components sont déclarées dans le build ; leur harmonisation reste à faire. Une dépendance Filament est déclarée, mais aucun usage n’a été identifié dans les sources Kotlin inspectées : aucune fonctionnalité 3D n’est revendiquée.

## Organisation du code

Le projet contient un module Android `app`. La logique d’interface et une partie de la logique applicative sont placées dans les `Activity`, les listes sont gérées par des adapters, et les accès SQLite par des helpers. Aucune séparation MVVM, couche Repository ou Clean Architecture n’est établie par le code actuel.

| Emplacement | Rôle |
| --- | --- |
| `app/src/main/java/com/example/j_shop/` | Activities, adapters, modèles `Product` et `CartItem`, `CartManager` et helpers SQLite |
| `app/src/main/res/layout/` | Écrans et vues d’articles en XML |
| `app/src/main/res/drawable/` | Images et ressources graphiques |
| `app/src/main/res/menu/` | Menus de navigation et de tri |
| `app/src/main/AndroidManifest.xml` | Déclaration des activités et point d’entrée |
| `app/src/test/` et `app/src/androidTest/` | Tests d’exemple |
| `gradle/` | Catalogue de versions et Gradle Wrapper |

## Installation et lancement

### Préparer l’environnement

- Android Studio compatible avec Android Gradle Plugin 8.10.0.
- JDK 17 pour exécuter Gradle, en conservant la cible de compilation Java/Kotlin 11 définie dans le projet.
- Android SDK Platform 35 et un émulateur ou appareil sous Android 7.0 / API 24 minimum.
- Connexion Internet pour télécharger Gradle et les dépendances lors de la première synchronisation.

### Ouvrir le projet

```bash
git clone https://github.com/JihadTiazziti/J_Shop.git
cd J_Shop
```

1. Ouvrir le dossier `J_Shop` dans Android Studio.
2. Configurer le JDK de Gradle et le chemin du SDK Android si nécessaire.
3. Lancer la synchronisation Gradle et installer les composants SDK demandés.
4. Sélectionner le module `app`, puis un appareil compatible, et lancer **Run**.

Aucune configuration Firebase, clé d’API ou serveur n’est utilisée par les sources inspectées.

### Compiler depuis le terminal

Après configuration du JDK et du SDK Android :

```bash
# macOS / Linux
chmod +x gradlew
./gradlew :app:assembleDebug
```

```powershell
# Windows PowerShell
.\gradlew.bat :app:assembleDebug
```

L’APK debug est généré dans `app/build/outputs/apk/debug/app-debug.apk`.

### Build vérifié

La commande `bash gradlew :app:assembleDebug --no-daemon` a réussi le **26 octobre 2026** sur le runner GitHub Actions Ubuntu, avec Java 17, Gradle 8.11.1 et Android SDK Platform 35.

- [Exécution du build vérifié](https://github.com/JihadTiazziti/J_Shop/actions/runs/40397777104)
- [Workflow de compilation](.github/workflows/android-build.yml)
- L’artefact **J-Shop-debug** contient l’APK debug et est conservé pendant 7 jours. Il est accessible dans la section **Artifacts** de l’exécution, avec une connexion GitHub.
- Le workflow se relance sur les modifications du code ou de la configuration de build dans `main`, et peut aussi être lancé manuellement.

La tentative locale avait été bloquée au téléchargement de Gradle par l’accès réseau de l’environnement ; la validation de compilation repose sur GitHub Actions. Les tests unitaires et instrumentés n’ont pas été exécutés lors de ce build. Le lancement dans Android Studio et les parcours sur appareil restent à vérifier.

### Essayer la démonstration locale

`UserDatabaseHelper` initialise un compte de démonstration lors de la création de la base : email `test@example.com`, mot de passe `123456`. Il s’agit de données de test présentes dans le code. Ne pas utiliser de véritables identifiants personnels.

Parcours de vérification proposé : connexion → catalogue → recherche → tri → ajout au panier → modification des quantités → vidage du panier. Vérifier aussi la suppression individuelle, puis quitter et rouvrir le panier pour observer la limite décrite plus haut.

## Captures d’écran à ajouter

Aucune capture de l’application exécutée n’est incluse dans cette documentation. Ajouter des captures réelles de cette version, prises avec des données de démonstration, dans le dossier proposé `docs/screenshots/`.

| Fichier proposé | Contenu à montrer |
| --- | --- |
| `01-login.png` | Écran de connexion initial |
| `02-catalogue.png` | Grille des produits avec navigation inférieure |
| `03-recherche.png` | Recherche affichant un sous-ensemble de produits |
| `04-tri.png` | Menu de tri ouvert |
| `05-panier.png` | Plusieurs articles, quantités et total |

Une fois les fichiers ajoutés, intégrer leurs liens Markdown au README. Garder les mêmes dimensions pour les captures et éviter d’illustrer des fonctionnalités absentes avec des maquettes.

## Portfolio

**Jihad Tiazziti — Développement mobile Android**

Ce projet illustre la création d’interfaces Android en Kotlin/XML, l’affichage d’un catalogue avec RecyclerView, la recherche et le tri de données locales, l’utilisation de SQLite et la gestion d’un panier en mémoire.

- [Profil GitHub](https://github.com/JihadTiazziti)
- [Code source de J-Shop](https://github.com/JihadTiazziti/J_Shop)
- [Portfolio personnel](https://jihadtiazziti.netlify.app)
- [LinkedIn](https://ma.linkedin.com/in/jihad-tiazziti-273b22378)

Les liens personnels proviennent des informations fournies par l’auteur ; leur disponibilité et leur contenu actuel restent à confirmer avant publication.

## Checklist avant publication

- [ ] Confirmer que le commit documenté correspond bien à la version à présenter ; si une version locale plus récente existe, réexaminer son code.
- [x] Compiler l’APK debug dans GitHub Actions avec Java 17 et Android SDK 35.
- [ ] Tester le parcours de démonstration sur appareil et consigner le modèle et la version Android.
- [ ] Reproduire le problème de suppression individuelle du panier et conserver cette limite documentée jusqu’à correction.
- [ ] Conserver les mentions « prototype » et « parcours incomplets » tant que l’inscription, la réinitialisation et l’authentification sociale ne sont pas implémentées.
- [ ] Ajouter les captures réelles et vérifier les droits d’utilisation des images de produits avant diffusion publique de l’application.
- [ ] Confirmer les liens du portfolio et de LinkedIn.
- [ ] Choisir une licence si une autorisation de réutilisation doit être accordée. Aucun fichier de licence n’a été identifié dans le dépôt inspecté ; aucune licence n’est supposée ici.

Seule la case cochée correspond à une validation réalisée. Les autres éléments restent des vérifications proposées, pas des engagements de développement.
