# Fiche présentation java
## 1. Java
### Typage
Contrairement à php, toutes les variables sont fortement typées. Sauf cas particulier, le type est défini à la déclaration et ne peuvent pas être changés (autrement qu'avec un cast).

### Mots-clés 
- final : équivalent du const en PHP
- static : idem Php
- var: Inférence de type, on ne précise pas le type à proprement parler, le compilateur s'en chargera.
- final sur une méthode : Rend la méthode non surchageable
- final sur une classe : Rend la classe non extensible
- abstract (méthode ou classe) : Implémentation partielle, idem PHP
- default (méthode) : Equivalent méthode abstraite dans une interface

### Collections (List, Map, Set)

Les collections ont 3 grandes familles de collections, contrairement au php où array est plus un dictionnaire qu'une vraie liste

- List, équivalent direct de l'array, a des indices ordonnés. 

```java
List<String> names = new ArrayList<>();

names.add("oui");           // array_push
names.get(0);               // $names[0]
names.remove("oui");        // array_remove
names.size();               // count($names)
```

- Map, liste de tuples clé/valeur. L'accès a une donnée de la map est en O(1) (instantané/sans surcoût)

```java
Map<String, String> config = new HashMap<>();

config.put("key", "value");     // $config['key'] = value
config.get("key");              // $config['key']
config.containsKey("key");     // array_key_exists
config.remove("key");          // unset($config['key'])
```

- Set, une collection sans doublons

```java
Set<String> tags = new HashSet<>();

tags.add("value");        // unique
tags.contains("value");   // in_array
tags.size();             // count
```

### Programmation fonctionnelle 

Le code Java récent tend de plus en plus à d'utiliser la programmation fonctionnelle qui est lisible facilement et donc plus maintenable. Les concepts suivants favorisent ce mode de programmation.

#### Streams

Les streams permettent de manipuler facilement des flux de données, pour des listes ou des fichiers par exemple. Les Java Streams sont comparables aux fonctions PHP comme `array_map`, `array_filter`, `array_reduce`, mais on peut faire beaucoup plus de choses avec.

```java
List<Integer> numbers = List.of(1,2,3,4);
List<Integer> squared = numbers.stream()
    .map(n -> n * n)
    .filter(n -> n > 5)
    .toList();
```

Les streams permettent notamment de mapper, filtrer, ordonancer ou grouper des données.

#### Optionnal
La classe Optional<T> représente une valeur qui peut être présente ou absente, au lieu d’utiliser null et des if (x != null). Elle sert surtout comme type de retour de méthode (Voir exemple repository et service), pour rendre le code plus clair et plus sûr. L'utilisation de cette classe renforce l'utilisation de la programmation fonctionnelle.

```java
public String getUserWithUppercaseName(Long id) {
    return userRepostiory
            .findUserById(id)                // Optional<User>
            .map(User::getName)              // Optional<String>
            .map(String::toUpperCase)        // Optional<String>
            .orElseThrow(() ->               // Si pas de valeur à mapper (null) on lève une exception
                new IllegalArgumentException("Utilisateur introuvable")
            );
}
```
Il existe plusieurs possibilité de orXXX. Les plus utilisées seront orElseThrow() (lève une exception), orElse() (retourne une valeur par défaut) ou encore orElseGet() (Appele une fonction de callback qui retourne la valeur par défaut).

```java
String username =
    findUserById(id)
        .map(User::getName)
        .orElse("anonymous user");

String username =
    findUserById(id)
        .map(User::getName)
        .orElseThrow(() ->
            new IllegalArgumentException("Utilisateur introuvable")
        );
String username =
    findUserById(id)
        .map(User::getName)
        .orElseGet(() -> creerUsername());
```


### Lombok 
- Lombok est une librairie tier très populaire qui permet de se passer du code boilerplate. (get, set, constructeurs, builder)

**Exemple sans Lombok :**
```java
public class User {
    private String username;
    private String email;
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

**Avec Lombok :**
```java
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String email;
}
```

## 2. Symfony vers Spring boot
### Principe du pattern MVC
Le pattern MVC signifie Model – View – Controller et sert à bien organiser une application web en trois couches séparées :
• Model : contient les données et la logique métier (entités JPA, services, accès base de données, etc.).
• View : gère ce que voit l’utilisateur, dans le cas d'un projet avec un backend renvoyant du JSON, on peut considérer le front comme la vue.
• Controller (Contrôleur) : Intermédiaire entre le modèle et la vue. Il reçoit les requêtes HTTP, appelle le modèle (services, repositories) et la réponse JSON appropriée.

Les bénéfices principaux sont :
- Séparation des responsabilités, chaque partie à une seule mission
- Modularité,

##### DTO et contrat d'interface 
Le Data Transfert Object est un objet dont comme son nom l'indique, est utilisé pour le transfert de données. Dans un contexte d'un projet utilisant des contrôleurs retournant du json, ils correspondent aux champs du json que l'on veut retourner.

Utiliser cette technique permet de séparer la responsabilité des parties services/métier de la partie repository. (Ex: si demain, pour X raison, toute la partie base de données doit être réfondue d'une manière différente, mais que nos applications front doivent consommer les mêmes données, alors on aura besoin de toucher uniquement la partie repository/service)

Cette encapsulation permet aussi de définir un contrat d'interface avec le front, c'est à dire que le back et front se mettent d'accord sur les routes et leurs formats de retour (Donc les DTO). Ce qui permet de paralléliser et simplifier le développement des parties. Le back sait ce qu'il doit retourner, indépendamment de la base de données et le front sait ce qu'il obtiendra du back, même si le développement de la route n'a pas encore été fait.

### Créer un projet spring boot
Il existe plusieurs façons de créer un projet spring boot. En voici deux :
- Utiliser le starter officiel de spring boot via le lien https://start.spring.io/
- Utiliser le générateur jhipster via le lien https://start.jhipster.tech/generate-application ou via la commande jhipster (voir https://www.jhipster.tech/creating-an-app/)

##### Version officielle 
Elle permet de générer un projet de base vide avec uniquement ce dont on a besoin. 
Les dépendances seront à ajouter manuellement, par exemple les drivers postgres, les bibliothèques Spring data JPA permettant de générer les appels vers la base de données.

##### Version jhipster
Jhipster est un générateur de projet web utilisant java spring boot. Il permet aussi d'inclure un front (react, angular, vue) dans le projet. Il se charge de créer un projet taillé pour le web, avec des configurations prêtes à l'emploi.

### Gestion des dépendances

Maven (ou Gradle) est l'équivalent de composer. Il permet la gestion des dépendances, le packaging et build. Le fichier maven est pom.xml. 

### Architecture et injection de dépendances

L'injection des dépendances en Java se fait via des annotations (@). Ils sont déclarés au dessus de la classe et injecté via le constructeur. (On a par exemple: @Component, @Service, @Repository, @RestController)

```java
@Repository
public class UserRepository {
}

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

```

Symfony et Spring boot appliquent le modèle **Controller -> Service -> Repository**.


### ORM et base de données

Hibernate/Spring Data JPA est l'équivalent de Doctrine ORM. Liquibase est l'équivalent des migrations doctrines.

**PHP :**
```php
#[ORM\Entity]
class User {
    #[ORM\Column(length: 255)]
    private string $username;
}
```

**Java :**
```java
@Entity
public class User {
    @Column(length = 255)
    private String username;
}
```

Liquibase peut être utilisé avec un fichier yml, sql ou XML. Le plus souvent utilisé est le XML.
Le but du fichier est de pouvoir définir les différentes migrations de la base de données au fur et à mesure du développement de l'application. Les migrations sont jouées et vérifiées systématiquement au lancement de l'application. Une fois qu'une migration a été effectuée, on ne peut (théoriquement) plus modifier la migration.

### Repository
La définition des repository se ressemblent beaucoup :
**PHP :**
```php
class UserRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, User::class);
    }
}
```

**Java :**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
```

JpaRepository<Entity, Id> fournit déjà les méthodes CRUD de base (findById, findAll, save, deleteById, etc.), comme find, findAll, findBy, findOneBy côté Doctrine. On peut créer des méthodes qui symbolisent la requête SQL à générer, dans ce cas, JPA implémentera la fonction tout seul. (ex: findByUsername, findByEmailAndActiveTrue). Sinon, on peut utiliser des requêtes complètes.

```php
class UserRepository extends ServiceEntityRepository
{
    public function findOneByUsername(string $username): ?User
    {
        return $this->findOneBy(['username' => $username]);
    }

    public function findOneActiveByEmail(string $email): ?User
    {
        return $this->findOneBy(['email' => $email, 'active' => true]);
    }

    public function findOneByEmail(string $email): ?User
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.email = :email')
            ->setParameter('email', $email)
            ->getQuery()
            ->getOneOrNullResult();
    }
}
```

**Java :**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // Pas besoin d'implémenter quoi que ce soit
    Optional<User> findByEmailAndActiveTrue(String email);

    // On utilise les entitées JPA comme en java dans des requêtes SQL
    @Query("SELECT u FROM User u WHERE u.email = :email")
    List<User> findByEmail(@Param("email") String email);

    // Sinon, on peut utiliser du SQL natif
    @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
    List<User> findByEmailNative(@Param("email") String email);
    
}

```

### Mapper
On utilise principalement la bibliothèque tier Mapstruct pour les mappers. Elle pourrait se comparer à Object Mapper de symfony, mais elle est beaucoup plus puissante.
**PHP :**
```php
class UserMapper
{
    /* Mapping à la main */
    public function toDto(User $user): UserDto
    {
        $dto = new UserDto();
        $dto->id   = $user->getId();
        $dto->name = $user->getName();

        return $dto;
    }
}
```

**Java :**
```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id") // source <=> User.getId() et target <=> UserDto.setId()
    @Mapping(source = "name", target = "name")
    UserDto toDto(User user);
}
```

MapStruct permet de réaliser un mapping plus poussé, notamment en utilisant des fonctions de transformation des données.
```java
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "name", target = "name", qualifiedByName = "toUpper")
    UserDto toDto(User user);

    @Named("toUpper")
    default String toUpper(String value) {
        return value == null ? null : value.toUpperCase();
    }
}
```

### Service

La définition d'un service est très similaire à Symfony:

**PHP :**
```php
class UserService
{
    public function __construct(
        private UserRepository $userRepository,
        private UserMapper $userMapper
    ) {}

    public function getUser(int $id): UserDto
    {
        $user = $this->userRepository->find($id);

        return $this->userMapper->toDto($user);
    }
}
```

**Java :**
```java
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserDto getUser(Long id) {
        return userRepository
            .findById(id)
            // Appel statique de la méthode toDto de userMapper
            // Ligne équivalente :
            // .map(user -> userMapper.toDto(user))
            .map(userMapper::toDto)
            .orElseThrow();
    }
}
```

### Contrôleurs et réponses JSON

Idem pour les contrôleurs, l'idée reste la même.

**PHP :**
```php
final class UserController extends AbstractController
{
    public function __construct(
        private UserService $userService,
    ) {}

    #[Route('/user/{id}', name: 'user', methods: ['GET'])]
    public function user(int $id): JsonResponse
    {
        $userDto = $this->userService->getUser($id);
        return $this->json($userDto);
    }
}
```

**Java :**
```java
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
```

### Sécurité et authentification
Les deux frameworks suivent le même modèle: filtre d'authentification, contexte utilisateur, gestion des rôles.

### Organisation de base des fichiers 

L'organisation est standard, on sépare principalement le modèle, vue et controlleur. 

**Structure typique :**
```
com.example.project
- controller/
- service/
- repository/
- model/
```

### Fin 
Il y a evidemment beaucoup plus de choses à voir en détails sur Spring boot et java, mais ça permet d'introduire le tout :D  
Si jamais tu as besoin de resources (en français) sur java: https://www.jmdoudoux.fr/java/dej/index.htm
