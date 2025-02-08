### Проектная работа третьего спринта

### Краткое описание
Приложение-блог с использованием Spring Framework.

### Стэк технологий

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white "Java 21")
![Maven](https://img.shields.io/badge/Maven-green.svg?style=for-the-badge&logo=mockito&logoColor=white "Maven")
![Spring](https://img.shields.io/badge/Spring-blueviolet.svg?style=for-the-badge&logo=spring&logoColor=white "Spring")
![GitHub](https://img.shields.io/badge/git-%23121011.svg?style=for-the-badge&logo=github&logoColor=white "Git")

* Язык: *Java 21*
* Автоматизация сборки: *Maven*
* Фреймворк: *Spring*
* Контроль версий: *Git*
* База данных: *H2*
* Шаблоны: *Thymeleaf*
* Контейнер: *Tomcat*

## Функционал

### Лента постов:
- Превью постов (название, картинка, первый абзац);
- Количество комментариев к посту;
- Количество лайков к посту;
- Теги поста, фильтрация по тегу;
- Пагинация (10, 20, 50 постов);
- Добавление поста (название, картинка, текст, теги).

### Страница поста:
- Название поста;
- картинка;
- текст поста, теги;
- Лайки, комментарии;
- Кнопки редактирования и удаления;


## Развертывание и запуск
```
 mvn clean package
```
SimpleBlog.war необходимо скопировать в папку webapps в директории Tomcat

Болог будет доступен по адресу http://localhost:8080/SimpleBlog/

