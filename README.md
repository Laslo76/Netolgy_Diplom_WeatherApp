# WeatherApp — Android Weather Forecast App

Приложение прогноза погоды на основе NGS Weather API.

## Технологии

- **Kotlin** + **Jetpack Compose** (Single Activity, @Composable)
- **Dagger Hilt** — внедрение зависимостей
- **Retrofit** — работа с удалённым API
- **Room** — локальная база данных (offline режим)
- **DataStore Preferences** — хранение настроек
- **Material Design 3** — UI компоненты
- **Navigation Compose** — навигация между экранами

## Возможности

### Экран прогноза погоды
- Краткий прогноз по дням (сегодня + N дней)
- Каждый день показывает: дату, макс/мин температуру, вероятность осадков
- При нажатии на день — раскрывается карточка с подробным прогнозом:
  температура, влажность, давление, направление и сила ветра
- Баннер «офлайн» при отображении кэшированных данных

### Экран настроек
- Выбор города (Москва, Новосибирск, Санкт-Петербург, Екатеринбург, Казань, Красноярск)
- Количество дней прогноза (1–14)
- Тема оформления: светлая / тёмная / системная
- Язык: английский / русский / системный

### Offline режим
- При недоступности сети приложение загружает данные из локальной БД (Room)
- При успешном сетевом запросе данные кэшируются автоматически

### Локализация
- Английский (по умолчанию) и русский языки
- Файлы строковых ресурсов: `res/values/strings.xml` и `res/values-ru/strings.xml`

## API

Базовый URL: `http://pogoda.ngs.ru/`

Эндпоинты:
- `GET api/v1/forecasts/forecast?city={city}` — прогноз погоды по дням
- `GET api/v1/cities` — список доступных городов

## Структура проекта

```
app/src/main/java/com/example/weatherapp/
├── WeatherApp.kt              # Application класс (@HiltAndroidApp)
├── MainActivity.kt            # Single Activity
├── data/
│   ├── api/WeatherApiService.kt       # Retrofit API интерфейс
│   ├── model/WeatherResponse.kt       # Модели данных API
│   ├── local/                        # Room (offline кэш)
│   │   ├── WeatherEntity.kt
│   │   ├── WeatherDao.kt
│   │   └── WeatherDatabase.kt
│   ├── prefs/SettingsManager.kt      # DataStore (настройки)
│   └── repository/WeatherRepository.kt  # Repository pattern
├── di/
│   ├── NetworkModule.kt              # Hilt: Retrofit, OkHttp
│   └── DatabaseModule.kt             # Hilt: Room
├── ui/
│   ├── theme/                        # Material 3 тема
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   └── Theme.kt
│   ├── forecast/
│   │   ├── ForecastScreen.kt         # Экран прогноза
│   │   └── ForecastViewModel.kt
│   ├── settings/
│   │   ├── SettingsScreen.kt         # Экран настроек
│   │   └── SettingsViewModel.kt
│   └── navigation/AppNavigation.kt   # Navigation Compose
└── util/WeatherUtils.kt              # Утилиты
```

## Тесты

- `WeatherRepositoryTest.kt` — unit-тесты репозитория (mockk)
  - Успешный сетевой запрос → данные из API
  - Сетевая ошибка с кэшем → данные из кэша
  - Сетевая ошибка без кэша → ошибка
  - Обработка null-полей в ответе API
- `WeatherUtilsTest.kt` — unit-тесты утилит форматирования

## Сборка

1. Откройте проект в Android Studio (Hedgehog или новее)
2. Укажите путь к Android SDK в `local.properties`
3. Sync Gradle и запустите приложение

```bash
./gradlew assembleDebug
./gradlew test
```

## Минимальные требования

- Android SDK 26+ (Android 8.0)
- Android Studio Hedgehog | 2023.1.1+
- JDK 17
