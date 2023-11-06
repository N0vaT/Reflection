# Reflection

## Exercise 00 - Work with Classes
**Структура проекта**:
- ex00
  - src
    - main
      - java
        - edu.school21.reflection
          - exceptions
            - InputClassNameException
          - models
            - Car
            - User
          - App
  - pom.xml

Реализовано приложение:
  - Предоставляющее информацию о классе в пакете классов models (Car, User). 
  - Пользователь может создавать объекты указанного класса с определенными значениями полей. 
  - Отображается информация о созданном объекте класса.
  - Пользователь может вызовать метод класса.

```
Classes:
  - User
  - Car
---------------------
Enter class name:
-> User
---------------------
fields:
	String firstName
	String lastName
	int height
methods:
	int grow(int)
---------------------
Let’s create an object.
firstName:
-> UserName
lastName:
-> UserSurname
height:
-> 185
Object created: User[firstName='UserName', lastName='UserSurname', height=185]
---------------------
Enter name of the field for changing:
-> firstName
Enter String value:
-> Name
Object updated: User[firstName='Name', lastName='UserSurname', height=185]
---------------------
Enter name of the method for call:
-> grow(int)
Enter int value:
-> 10
Method returned:
195
```

## Exercise 01 – Annotations – SOURCE
**Структура проекта**:
- ex01
  - Annotations
    - src
      - main
        - java
          - edu.school21.reflection
            - annotations
              - HtmlForm
              - HtmlInput
            - models
              - UserForm
    - pom.xml
  - HtmlProcessor
    - src
      - main
        - java
          - edu.school21.processor
            - HtmlProcessor
    - pom.xml
  - pom.xml

Реализован класс HtmlProcessor (производный от AbstractProcessor), который обрабатывает классы со специальными аннотациями @HtmlForm и
@Htmlnput и генерирует код формы HTML внутри папки target/classes в файл «user_form.html» после выполнения команды компиляции mvn clean.
Класс UserForm:
```java
@HtmlForm(fileName = “user_form.html”, action = “/users”, method = “post”)
public class UserForm {
	@HtmlInput(type = “text”, name = “first_name”, placeholder = “Enter First Name”)
	private String firstName;

	@HtmlInput(type = “text”, name = “last_name”, placeholder = “Enter Last Name”)
	private String lastName;
	
	@HtmlInput(type = “password”, name = “password”, placeholder = “Enter Password”)
	private String password;
}
```
Файл «user_form.html»:
```HTML
<form action = "/users" method = "post">
	<input type = "text" name = "first_name" placeholder = "Enter First Name">
	<input type = "text" name = "last_name" placeholder = "Enter Last Name">
	<input type = "password" name = "password" placeholder = "Enter Password">
	<input type = "submit" value = "Send">
</form>
```
Аннотации @HtmlForm и @HtmlInput доступны только во время компиляции.

## Exercise 02 – ORM
**Структура проекта**:
- ex00
  - src
    - main
      - java
        - edu.school21.OrmManager
          - annotations
            - OrmColumn
            - OrmColumnId
            - OrmEntity
          - exceptions
            - AnnotationOrmEntityException
          - models
            - User
            - UserWithoutAnnotation
          - ormManager
            - OrmManager
            - OrmManagerImpl
          - App
  - pom.xml

Реализована тривиальная версия структуры ORM.
Каждый класс не содержит зависимостей от других классов, и его поля могут принимать только следующие типы значений: String, Integer, Double, Boolean, Long.
Например, класс User:
```java
@OrmEntity(table = “simple_user”)
public class User {
  @OrmColumnId
  private Long id;
  @OrmColumn(name = “first_name”, length = 10)
  private String firstName;
  @OrmColumn(name = “first_name”, length = 10)
  private String lastName;
  @OrmColumn(name “age”)
  private Integer age;
  
  // setters/getters
}
```

Класс OrmManager генерирует и выполняет соответствующий SQL-код во время инициализации всех классов, отмеченных аннотацией @OrmEntity.
Этот код будет содержать команду CREATE TABLE для создания таблицы с именем, указанным в аннотации. Каждое поле класса, отмеченное аннотацией @OrmColumn,
становится столбцом в этой таблице. Поле, отмеченное аннотацией @OrmColumnId, указывает на то, что необходимо создать идентификатор автоматического увеличения.
OrmManager также поддерживает следующий набор операций (для каждой из них также генерируется соответствующий SQL-код в Runtime):
```java
public void save(Object entity)

public void update(Object entity)

public <T> T findById(Long id, Class<T> aClass)
```

- OrmManager обеспечивает вывод сгенерированного SQL на консоль во время выполнения.
- При инициализации OrmManager удаляет созданные таблицы.
- Метод обновления заменяет значения в столбцах, указанных в сущности, даже если значение поля объекта равно нулю.
