# 📘 MDD - Réseau Social

Application web de type réseau social permettant aux utilisateurs de partager des posts, commenter et interagir autour de sujets.

---

## 🚀 Stack technique

### Backend
- Java
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit / Mockito

### Frontend
- Angular
- TypeScript
- RxJS / HttpClient
- SCSS

---

## 📂 Structure du projet

### Frontend (Angular)

```text
front/
├── src/
│   ├── app/
│   │   ├── core/
│   │   │   ├── guards/        # Protection des routes
│   │   │   ├── interceptors/  # Ajout automatique du JWT
│   │   │   ├── models/        # Interfaces TypeScript
│   │   │   └── services/      # Appels API
│   │   ├── pages/
│   │   │   ├── create-post/   # Création de post
│   │   │   ├── feed/          # Fil des posts
│   │   │   ├── home/          # Accueil
│   │   │   ├── login/         # Connexion
│   │   │   ├── post-detail/   # Détail d’un post
│   │   │   ├── profile/       # Profil utilisateur
│   │   │   ├── register/      # Inscription
│   │   │   └── topics/        # Gestion des topics
│   │   ├── shared/            # Composants réutilisables
│   │   ├── app-routing.module.ts
│   │   ├── app.component.*
│   │   └── app.module.ts
│   ├── assets/
│   ├── environments/
│   ├── styles/
│   └── index.html
```

### Backend (Spring Boot)

```text
back/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/openclassrooms/mddapi/
│   │   │   ├── config/        # Configuration Spring
│   │   │   ├── controller/    # Endpoints REST
│   │   │   ├── dto/           # Objets de transfert
│   │   │   ├── entity/        # Entités JPA
│   │   │   ├── repository/    # Accès aux données
│   │   │   ├── security/      # JWT, filtres, sécurité
│   │   │   ├── service/       # Logique métier
│   │   │   └── MddApiApplication.java
│   │   └── resources/
│   │       └── application.properties
```

---

## ⚙️ Configuration du backend

Créer ou compléter le fichier :

```text
back/src/main/resources/application.properties
```

Avec :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mdd
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

app.jwt.secret=YOUR_SECRET
app.jwt.expiration=86400000
```

## 🗄️ Base de données

Créer la base PostgreSQL :

```sql
CREATE DATABASE mdd;
```

---

## 🚀 Lancement du projet

### 1. Lancer le backend

Depuis le dossier `back` :

```bash
./mvnw spring-boot:run
```

Sous Windows :

```bash
mvnw.cmd spring-boot:run
```

Backend disponible sur :

```text
http://localhost:8080/api
```

### 2. Lancer le frontend

Depuis le dossier `front` :

```bash
npm install
ng serve
```

Frontend disponible sur :

```text
http://localhost:4200
```

---

## 🔐 Authentification

L’application utilise une authentification JWT.

### Endpoints principaux
- `POST /api/auth/register`
- `POST /api/auth/login`

Côté frontend, le token est géré via un interceptor Angular.

---

## 📝 Fonctionnalités

- Inscription et connexion utilisateur
- Création de posts
- Affichage du feed
- Consultation du détail d’un post
- Ajout de commentaires
- Gestion des topics
- Consultation du profil utilisateur
- Protection des routes côté frontend
- Sécurisation des endpoints côté backend

---

## 🧪 Tests

### Backend

Depuis le dossier `back` :

```bash
./mvnw test
```

Sous Windows :

```bash
mvnw.cmd test
```

---

## 🛠️ Architecture

### Frontend
Le frontend est organisé par responsabilités :
- `core/` contient les briques transverses
- `pages/` contient les écrans principaux
- `shared/` contient les composants réutilisables

### Backend
Le backend suit une architecture Spring classique :
- `controller/` pour exposer l’API REST
- `service/` pour la logique métier
- `repository/` pour l’accès à la base
- `entity/` pour le modèle persistant
- `dto/` pour les échanges API
- `security/` pour JWT et Spring Security

---

## 📌 Pistes d’amélioration

- Likes
- Notifications
- Recherche avancée
- Pagination du feed
- Upload d’images
- Version mobile

---

## 📄 Licence

Projet réalisé dans un cadre pédagogique.
