---
title: Laboratorio de Programación Orientada a Objetos
author: acá van sus nombres
---

El enunciado del laboratorio se encuentra en [este link](https://docs.google.com/document/d/1wLhuEOjhdLwgZ4rlW0AftgKD4QIPPx37Dzs--P1gIU4/edit#heading=h.xe9t6iq9fo58).

## 1. Tareas

Pueden usar esta checklist para indicar el avance.

### Verificación de que pueden hacer las cosas

- [X] Java 17 instalado. Deben poder compilar con `make` y correr con `make run` para obtener el mensaje de ayuda del programa.

### 1.1. Interfaz de usuario

- [X] Estructurar opciones
- [x] Construir el objeto de clase `Config`

### 1.2. FeedParser

- [X] `class Article`
  - [X] Atributos
  - [X] Constructor
  - [X] Método `print`
  - [X] _Accessors_
- [X] `parseXML`

### 1.3. Entidades nombradas

- [X] Pensar estructura y validarla con el docente
- [X] Implementarla
- [X] Extracción
  - [X] Implementación de heurísticas
- [X] Clasificación
  - [X] Por tópicos
  - [X] Por categorías
- Estadísticas
  - [X] Por tópicos
  - [X] Por categorías
  - [X] Impresión de estadísticas

### 1.4 Limpieza de código

- [X] Pasar un formateador de código
- [X] Revisar TODOs

## Experiencia
Este laboratorio nos resulto muy entretenido a todos los integrantes del grupo, ya que estamos haciendo una especie de "Web Scraping". 
Nos parece muy util aprender la programacion orientada a objetos ya que esta presente en muchos codigos ajenos. La idea de extraer informacion de paginas de noticias es muy interesante y sentimos que esto se podria aplicar a la extraccion de informacion de distintos proyectos que tengamos a lo largo de nuestra carrera. Al ser un lenguaje de programacion nuevo nos costo adaptarnos a dicho lenguaje y a sus librerias. Otro problema que tuvimos fue  la transparecia de multiples clases en un solo archivo.
Pero en lineas generales pudimos comprender el laboratorio y afrontar todos los problemas por nuestra cuenta.

## Preguntas
1-Explicar brevemente la estructura de datos elegida para las entidades nombradas.

Cada categoría, al ser un conjunto finito, es su propia clase. Todas estas son subclases de la superclase `NamedEntities que` contiene los tres valores por defecto de name, category y topic. Esto nos permite tratar a todas las entidades en conjunto, para así hacer cosas como extraer el nombre de cada una sin tener que hacer casos separados de acuerdo a la categoría.


2-Explicar brevemente cómo se implementaron las heurísticas de extracción.

Nuestras heurísticas se basaron en dos formas distintas de verificar código: usando una expresión regular y un matcher (cambiando la expresión regular que nos dio la cátedra y limitándola a solo dos palabras que empiecen con mayúscula), y verificando índice a índice en un arreglo de nombres (que extraemos del diccionario, la etiqueta label.)

Estas heurísticas tienen varios problemas: `NameSurname` sigue teniendo falsos positivos, y no acepta entidades que tenían una sola palabra de nombre. Por el otro lado, DictHeuristic solo agarra entidades con una sola palabra de nombre. Además que no acepta entidades que no se encuentren en el diccionario, por diseño.