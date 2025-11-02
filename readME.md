# StackActivityTest

Учебное Android-приложение для работы со **стеками задач (tasks)** и флагами запуска `Activity` верстка на XML. В проекте три экрана — `ActivityA`, `ActivityB`, `ActivityC` — и `BroadcastReceiver` (`WakeReceiver`) для запуска `ActivityA` по событию.

---

## Реализация

- **Два независимых стека задач**
  - **Task #1** — только `ActivityA` (`launchMode=singleTask`, `taskAffinity=com.example.stackactivitytest.taskA`).
  - **Task #2** — `ActivityB` и `ActivityC` (общий `taskAffinity=com.example.stackactivitytest.taskB`).

- **Навигация**
  - `A → B` — переход в Task #2, гарантируем, что `B` всегда сверху: `NEW_TASK | CLEAR_TOP`.
  - `B → C` — обычный `startActivity` в том же task.
  - `C → A` — возврат к существующей `A` без дублей: `NEW_TASK | CLEAR_TOP | SINGLE_TOP` (вызывает `onNewIntent()` у `A`).

- **Цвет и состояние**
  - В `A` генерация/ввод HEX-цвета (`#RRGGBB`), валидация, сохранение поля и фона при повороте.
  - `B` принимает цвет из `A` через `Intent` (`EXTRA_HEX`), красит фон и сохраняет его в `onSaveInstanceState`.
  - Все экраны имеют разные цвета фона для наглядности.

- **Поведение экрана и безопасность**
  - На `ActivityA` экран **не гаснет**: `FLAG_KEEP_SCREEN_ON`.
  - `ActivityA` может отображаться **поверх lock-screen** и **будить экран**:
    - API 27+: `setShowWhenLocked(true)`, `setTurnScreenOn(true)`.
    - Старые API: эквивалентные window-флаги.
  - **Запрет скриншотов**: `FLAG_SECURE` включается на активных экранах (реализация в базовой активити).

- **WakeReceiver**
  - Принимает broadcast `com.example.stackactivitytest.SHOW_A` и поднимает `ActivityA` без дублей (удобно для демонстрации и интеграций).

---

## Требования

- Android Studio
- JDK 21 (Gradle Toolchain)
- `compileSdk = 36`, `minSdk = 26`, `targetSdk = 36`
- Kotlin 2.x, AGP 8.x
- XML-верстка

---

## Структура

```
app/
  src/main/java/com/example/stackactivitytest/
    ui/
      BaseActivity.kt
      ActivityA.kt
      ActivityB.kt
      ActivityC.kt
    util/ColorUtils.kt
    WakeReceiver.kt
  src/main/res/layout/
    activity_base.xml
    activity_a.xml
    activity_b.xml
    activity_c.xml
  src/main/AndroidManifest.xml
build.gradle.kts (project)
app/build.gradle.kts
gradle/wrapper/*
gradlew, gradlew.bat
```

---