# Scramble Game - Android

Интерактивная игра на разгадывание слов, разработанная с использованием **Jetpack Compose** для Android. Игроки должны угадать перемешанное слово, вводя буквы в текстовом поле. Игра включает анимации, звуковые эффекты и динамичный фон, который усиливает впечатления от игрового процесса.

# Скриншоты
<div style="display: flex; flex-direction: row;">
  <img src="https://github.com/OlegQt/TestGl/blob/master/ScrambleScreenA.jpeg" alt="playlistScreen" width="300">
</div>


## Особенности:
- Отображение перемешанных слов с анимацией букв.
- Звуковые эффекты, активируемые при взаимодействии пользователя.
- Валидация ввода с динамическим изменением видимости букв.
- Красивая анимация фона с плавающими белыми частицами и градиентными эффектами.
- Современный интерфейс с использованием **Jetpack Compose** и **Material3**.

## Фон:
Фон игры состоит из плавающих белых частиц, которые движутся в случайных направлениях, создавая умиротворенную и захватывающую атмосферу. Этот эффект достигается с использованием **Canvas** и **LaunchedEffect** в **Jetpack Compose**. Ключевые особенности фона:
- Градиентный эффект, который плавно меняет цвета между основными и вторичными цветами темы.
- Анимированные частицы, движущиеся по экрану с легким  размытием.
- Частицы динамически сбрасываются, когда они выходят за пределы центральной части экрана.

## Используемые технологии:
- **Jetpack Compose**
- **ViewModel** (архитектура **MVVM**)
- **SoundPool** для звуковых эффектов
- **Kotlin Coroutines** и **Flow**
- **Canvas** для кастомных анимаций частиц

[![Kotlin](https://img.shields.io/badge/-Kotlin-blue)](https://kotlinlang.org/)  [![Jetpack Compose](https://img.shields.io/badge/-Jetpack%20Compose-brightgreen)](https://developer.android.com/jetpack/compose)  [![MVVM](https://img.shields.io/badge/-MVVM-orange)](https://developer.android.com/jetpack/guide?gclid=CjwKCAjwqIiFBhAHEiwANg9szkbgZdKjebt3kzGqrdK1r2fb1Q4oC1Y-0I7KtI9M6mGZyB4e_2YxHhoC0kkQAvD_BwE&gclsrc=aw.ds)  [![Material3](https://img.shields.io/badge/-Material3-red)](https://developer.android.com/jetpack/androidx/releases/compose-material3)  [![SoundPool](https://img.shields.io/badge/-SoundPool-blue)](https://developer.android.com/reference/android/media/SoundPool)  
[![ViewModel](https://img.shields.io/badge/-ViewModel-green)](https://developer.android.com/topic/libraries/architecture/viewmodel)  [![Coroutines](https://img.shields.io/badge/-Kotlin%20Coroutines-blue)](https://kotlinlang.org/docs/coroutines-guide.html)  [![Flow](https://img.shields.io/badge/-Flow-orange)](https://kotlinlang.org/docs/flow.html)
---

## Инструкция по установке

### 1. Клонирование репозитория
Сначала клонируйте репозиторий на свою локальную машину.

```bash
https://github.com/OlegQt/TestGl/tree/master
```

### 2. Открытие проекта
Откройте проект в **Android Studio**.

### 4. Сборка и запуск проекта
- Если будет предложено, синхронизируйте проект с Gradle-файлами.
- Подключите Android-устройство или используйте эмулятор Android.
- Нажмите кнопку "Run" в Android Studio, чтобы собрать и запустить приложение.

### 5. Наслаждайтесь игрой!
После установки приложения на ваше устройство или эмулятор, запустите его и наслаждайтесь игрой Scramble с интерактивными анимациями, звуками и красивым динамичным фоном!
